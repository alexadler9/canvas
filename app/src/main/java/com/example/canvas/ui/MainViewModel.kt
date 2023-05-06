package com.example.canvas.ui

import com.example.base.BaseViewModel
import com.example.base.Event
import com.example.canvas.views.COLOR
import com.example.canvas.views.SIZE
import com.example.canvas.views.TOOL
import com.example.canvas.views.canvas.CanvasViewState
import com.example.canvas.views.tools.ToolItem

class MainViewModel : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState(
        toolsList = enumValues<TOOL>().map { ToolItem.ToolModel(it) },
        colorList = enumValues<COLOR>().map { ToolItem.ColorModel(it.value) },
        isToolsVisible = false,
        isPaletteVisible = false,
        canvasViewState = CanvasViewState(
            color = COLOR.BLACK,
            size = SIZE.MEDIUM,
            tool = TOOL.NORMAL
        )
    )

    init {
        processDataEvent(
            DataEvent.OnSetDefaultTools(
                tool = TOOL.NORMAL,
                color = COLOR.BLACK
            )
        )
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnIconCleaningClicked -> {
                return previousState.copy(
                    isToolsVisible = false,
                    isPaletteVisible = false
                )
            }

            is UiEvent.OnIconToolsClicked -> {
                return previousState.copy(
                    isToolsVisible = !previousState.isToolsVisible,
                    isPaletteVisible = false
                )
            }

            is UiEvent.OnCanvasClicked -> {
                return previousState.copy(
                    isToolsVisible = false,
                    isPaletteVisible = false
                )
            }

            is UiEvent.OnToolsClicked -> {
                when (event.index) {
                    TOOL.PALETTE.ordinal -> {
                        return previousState.copy(isPaletteVisible = !previousState.isPaletteVisible)
                    }

                    else -> {
                        val toolsList = previousState.toolsList.mapIndexed() { index, model ->
                            if (index == event.index) {
                                model.copy(isSelected = true)
                            } else {
                                model.copy(isSelected = false)
                            }
                        }
                        return previousState.copy(
                            toolsList = toolsList,
                            isPaletteVisible = false,
                            canvasViewState = previousState.canvasViewState.copy(tool = TOOL.values()[event.index])
                        )
                    }
                }
            }

            is UiEvent.OnPaletteClicked -> {
                val selectedColor = COLOR.values()[event.index]
                val toolsList = previousState.toolsList.map() {
                    if (it.type == TOOL.PALETTE) {
                        it.copy(selectedColor = selectedColor)
                    } else {
                        it
                    }
                }
                return previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(color = selectedColor)
                )
            }

            is DataEvent.OnSetDefaultTools -> {
                val toolsList = previousState.toolsList.map {
                    if (it.type == event.tool) {
                        it.copy(isSelected = true, selectedColor = event.color)
                    } else {
                        it.copy(isSelected = false)
                    }
                }
                return previousState.copy(toolsList = toolsList)
            }

            else -> return null
        }
    }
}