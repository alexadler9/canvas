package ru.alexadler9.canvas.feature.canvasscreen.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.alexadler9.canvas.base.Action
import ru.alexadler9.canvas.base.BaseViewModel
import ru.alexadler9.canvas.data.PreferencesRepository
import ru.alexadler9.canvas.domain.Color
import ru.alexadler9.canvas.domain.Size
import ru.alexadler9.canvas.domain.Style
import ru.alexadler9.canvas.domain.Tool
import ru.alexadler9.canvas.feature.view.canvas.CanvasViewState
import ru.alexadler9.canvas.feature.view.tools.ToolItem
import javax.inject.Inject

private const val TAG = "CANVAS_VM"

@HiltViewModel
class CanvasViewModel @Inject constructor(
    private val repository: PreferencesRepository
) : BaseViewModel<ViewState, ViewEvent>() {

    override val initialViewState = ViewState(
        toolsList = enumValues<Tool>().map {
            ToolItem.ToolModel(
                type = it,
                selectedStyle = repository.getStyle(),
                selectedColor = repository.getColor(),
                selectedSize = repository.getSize()
            )
        },
        stylesList = enumValues<Style>().map { ToolItem.StyleModel(it.value) },
        colorsList = enumValues<Color>().map { ToolItem.PaletteModel(it.value) },
        sizesList = enumValues<Size>().map { ToolItem.SizeModel(it.value) },
        isToolsVisible = false,
        isStyleToolVisible = false,
        isPaletteToolVisible = false,
        isSizeToolVisible = false,
        canvasViewState = CanvasViewState(
            style = repository.getStyle(),
            color = repository.getColor(),
            size = repository.getSize(),
        )
    )

    override fun reduce(action: Action, previousState: ViewState): ViewState? {

        fun toolsHideSelection() =
            previousState.toolsList.map { model ->
                model.copy(isSelected = false)
            }

        fun toolSelect(actionIndex: Int) =
            previousState.toolsList.mapIndexed { index, model ->
                model.copy(isSelected = (index == actionIndex))
            }

        return when (action) {
            is UiAction.OnMenuToolsClicked -> {
                previousState.copy(
                    toolsList = toolsHideSelection(),
                    isToolsVisible = !previousState.isToolsVisible,
                    isStyleToolVisible = false,
                    isPaletteToolVisible = false,
                    isSizeToolVisible = false
                )
            }

            is UiAction.OnMenuClearClicked -> {
                sendViewEvent(ViewEvent.OnClearCanvas)
                return previousState.copy(
                    isToolsVisible = false,
                    isStyleToolVisible = false,
                    isPaletteToolVisible = false,
                    isSizeToolVisible = false
                )
            }

            is UiAction.OnCanvasClicked -> {
                return previousState.copy(
                    isToolsVisible = false,
                    isStyleToolVisible = false,
                    isPaletteToolVisible = false,
                    isSizeToolVisible = false
                )
            }

            is UiAction.OnToolClicked -> {
                when (action.index) {
                    Tool.STYLE.ordinal -> {
                        return previousState.copy(
                            toolsList = if (previousState.isStyleToolVisible) toolsHideSelection() else toolSelect(
                                Tool.STYLE.ordinal
                            ),
                            isStyleToolVisible = !previousState.isStyleToolVisible,
                            isPaletteToolVisible = false,
                            isSizeToolVisible = false
                        )
                    }

                    Tool.PALETTE.ordinal -> {
                        return previousState.copy(
                            toolsList = if (previousState.isPaletteToolVisible) toolsHideSelection() else toolSelect(
                                Tool.PALETTE.ordinal
                            ),
                            isStyleToolVisible = false,
                            isPaletteToolVisible = !previousState.isPaletteToolVisible,
                            isSizeToolVisible = false
                        )
                    }

                    Tool.SIZE.ordinal -> {
                        return previousState.copy(
                            toolsList = if (previousState.isSizeToolVisible) toolsHideSelection() else toolSelect(
                                Tool.SIZE.ordinal
                            ),
                            isStyleToolVisible = false,
                            isPaletteToolVisible = false,
                            isSizeToolVisible = !previousState.isSizeToolVisible
                        )
                    }
                }
                null
            }

            is UiAction.OnStyleClicked -> {
                val selectedStyle = Style.values()[action.index]
//                Log.d(TAG, "Style changed ${selectedStyle.name}")
                val toolsList = previousState.toolsList.map {
                    if (it.type == Tool.STYLE) it.copy(selectedStyle = selectedStyle) else it
                }
                repository.setStyle(selectedStyle)
                previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(style = selectedStyle)
                )
            }

            is UiAction.OnColorClicked -> {
                val selectedColor = Color.values()[action.index]
//                Log.d(TAG, "Color changed ${selectedColor.name}")
                val toolsList = previousState.toolsList.map {
                    if (it.type == Tool.PALETTE) it.copy(selectedColor = selectedColor) else it
                }
                repository.setColor(selectedColor)
                previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(color = selectedColor)
                )
            }

            is UiAction.OnSizeClicked -> {
                val selectedSize = Size.values()[action.index]
//                Log.d(TAG, "Size changed ${selectedSize.name}")
                val toolsList = previousState.toolsList.map {
                    if (it.type == Tool.SIZE) it.copy(selectedSize = selectedSize) else it
                }
                repository.setSize(selectedSize)
                previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(size = selectedSize)
                )
            }

            else -> null
        }
    }
}