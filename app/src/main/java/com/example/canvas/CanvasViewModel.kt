package com.example.canvas

import com.example.base.BaseViewModel
import com.example.base.Event

class CanvasViewModel : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState(
        colorList = enumValues<COLOR>().map { ToolItem.ColorModel(it.value) },
        toolsList = enumValues<TOOLS>().map { ToolItem.ToolModel(it) },
        isPaletteVisible = false,
        isToolVisible = false,
        canvasViewState = CanvasViewState(
            color = COLOR.BLACK,
            size = SIZE.MEDIUM,
            tools = TOOLS.NORMAL
        )
    )

    init {
        processDataEvent(
            DataEvent.OnSetDefaultTools(
                tool = TOOLS.NORMAL,
                color = COLOR.BLACK
            )
        )
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnToolbarClicked -> {
                return previousState.copy(
                    isToolVisible = !previousState.isToolVisible,
                    isPaletteVisible = false
                )
            }

            is UiEvent.OnToolsClicked -> {
                when (event.index) {
                    TOOLS.PALETTE.ordinal -> {
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
                            canvasViewState = previousState.canvasViewState.copy(tools = TOOLS.values()[event.index])
                        )
                    }
                }
            }

            is UiEvent.OnPaletteClicked -> {
                val selectedColor = COLOR.values()[event.index]
                val toolsList = previousState.toolsList.map() {
                    if (it.type == TOOLS.PALETTE) {
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