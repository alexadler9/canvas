package ru.alexadler9.canvas.feature.canvasscreen.ui

import android.util.Log
import ru.alexadler9.canvas.base.Action
import ru.alexadler9.canvas.base.BaseViewModel
import ru.alexadler9.canvas.feature.view.Color
import ru.alexadler9.canvas.feature.view.Size
import ru.alexadler9.canvas.feature.view.Style
import ru.alexadler9.canvas.feature.view.Tool
import ru.alexadler9.canvas.feature.view.canvas.CanvasViewState
import ru.alexadler9.canvas.feature.view.tools.ToolItem

private const val TAG = "CANVAS_VM"

class CanvasViewModel : BaseViewModel<ViewState, ViewEvent>() {

    override val initialViewState = ViewState(
        toolsList = enumValues<Tool>().map { ToolItem.ToolModel(it) },
        stylesList = enumValues<Style>().map { ToolItem.StyleModel(it.value) },
        colorsList = enumValues<Color>().map { ToolItem.PaletteModel(it.value) },
        sizesList = enumValues<Size>().map { ToolItem.SizeModel(it.value) },
        isToolsVisible = false,
        isStyleToolVisible = false,
        isPaletteToolVisible = false,
        isSizeToolVisible = false,
        canvasViewState = CanvasViewState(
            style = Style.NORMAL,
            color = Color.BLACK,
            size = Size.MEDIUM,
        )
    )

    override fun reduce(action: Action, previousState: ViewState): ViewState? {
        return when (action) {
            is UiAction.OnMenuToolsClicked -> {
                previousState.copy(
                    isToolsVisible = !previousState.isToolsVisible,
                    isStyleToolVisible = false,
                    isPaletteToolVisible = false,
                    isSizeToolVisible = false
                )
            }

            is UiAction.OnMenuClearClicked -> {
                sendViewEvent(ViewEvent.OnClearCanvas)
                null
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
                            isStyleToolVisible = !previousState.isStyleToolVisible,
                            isPaletteToolVisible = false,
                            isSizeToolVisible = false
                        )
                    }

                    Tool.PALETTE.ordinal -> {
                        return previousState.copy(
                            isStyleToolVisible = false,
                            isPaletteToolVisible = !previousState.isPaletteToolVisible,
                            isSizeToolVisible = false
                        )
                    }

                    Tool.SIZE.ordinal -> {
                        return previousState.copy(
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
                Log.d(TAG, "Style changed ${selectedStyle.name}")
                val toolsList = previousState.toolsList.map {
                    if (it.type == Tool.STYLE) it.copy(selectedStyle = selectedStyle) else it
                }
                previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(style = selectedStyle)
                )
            }

            is UiAction.OnColorClicked -> {
                val selectedColor = Color.values()[action.index]
                Log.d(TAG, "Color changed ${selectedColor.name}")
                val toolsList = previousState.toolsList.map {
                    if (it.type == Tool.PALETTE) it.copy(selectedColor = selectedColor) else it
                }
                previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(color = selectedColor)
                )
            }

            is UiAction.OnSizeClicked -> {
                val selectedSize = Size.values()[action.index]
                Log.d(TAG, "Size changed ${selectedSize.name}")
                val toolsList = previousState.toolsList.map {
                    if (it.type == Tool.SIZE) it.copy(selectedSize = selectedSize) else it
                }
                previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(size = selectedSize)
                )
            }

            else -> null
        }
    }
}