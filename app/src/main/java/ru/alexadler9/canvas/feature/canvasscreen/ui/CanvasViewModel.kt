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
        toolsPanelVisible = false,
        toolsVisibility = MutableList(Tool.values().size) { false },
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

        fun toolSelect(toolIndex: Int) =
            previousState.toolsList.mapIndexed { index, model ->
                model.copy(isSelected = (index == toolIndex))
            }

        return when (action) {
            is UiAction.OnMenuToolsClicked -> {
                previousState.copy(
                    toolsList = toolsHideSelection(),
                    toolsPanelVisible = !previousState.toolsPanelVisible,
                    toolsVisibility = MutableList(Tool.values().size) { false },
                )
            }

            is UiAction.OnMenuClearClicked -> {
                sendViewEvent(ViewEvent.OnClearCanvas)
                previousState.copy(
                    toolsPanelVisible = false,
                    toolsVisibility = MutableList(Tool.values().size) { false },
                )
            }

            is UiAction.OnCanvasClicked -> {
                previousState.copy(
                    toolsPanelVisible = false,
                    toolsVisibility = MutableList(Tool.values().size) { false },
                )
            }

            is UiAction.OnToolClicked -> {
                val toolVisibility = previousState.toolsVisibility[action.index]
                return previousState.copy(
                    toolsList = if (toolVisibility) toolsHideSelection() else toolSelect(action.index),
                    toolsVisibility = MutableList(Tool.values().size) { false }.apply {
                        set(
                            action.index,
                            !toolVisibility
                        )
                    }
                )
            }

            is UiAction.OnStyleClicked -> {
                val selectedStyle = Style.values()[action.index]
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