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
        colorsList = enumValues<COLOR>().map { ToolItem.ColorModel(it.value) },
        sizesList = enumValues<SIZE>().map { ToolItem.SizeModel(it.value) },
        isToolsVisible = false,
        isPaletteVisible = false,
        isSizesVisible = false,
        canvasViewState = CanvasViewState(
            tool = TOOL.NORMAL,
            color = COLOR.BLACK,
            size = SIZE.MEDIUM,
        )
    )

    init {
        processDataEvent(
            DataEvent.OnSetDefaultTools(
                tool = TOOL.NORMAL,
                color = COLOR.BLACK,
                size = SIZE.MEDIUM
            )
        )
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnIconCleaningClicked -> {
                return previousState.copy(
                    isToolsVisible = false,
                    isPaletteVisible = false,
                    isSizesVisible = false
                )
            }

            is UiEvent.OnIconToolsClicked -> {
                return previousState.copy(
                    isToolsVisible = !previousState.isToolsVisible,
                    isPaletteVisible = false,
                    isSizesVisible = false
                )
            }

            is UiEvent.OnCanvasClicked -> {
                return previousState.copy(
                    isToolsVisible = false,
                    isPaletteVisible = false,
                    isSizesVisible = false
                )
            }

            is UiEvent.OnToolClicked -> {
                when (event.index) {
                    TOOL.PALETTE.ordinal -> {
                        return previousState.copy(
                            isPaletteVisible = !previousState.isPaletteVisible,
                            isSizesVisible = false
                        )
                    }

                    TOOL.SIZE.ordinal -> {
                        return previousState.copy(
                            isPaletteVisible = false,
                            isSizesVisible = !previousState.isSizesVisible
                        )
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
                            isSizesVisible = false,
                            canvasViewState = previousState.canvasViewState.copy(tool = TOOL.values()[event.index])
                        )
                    }
                }
            }

            is UiEvent.OnColorClicked -> {
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

            is UiEvent.OnSizeClicked -> {
                val selectedSize = SIZE.values()[event.index]
                val toolsList = previousState.toolsList.map() {
                    if (it.type == TOOL.SIZE) {
                        it.copy(selectedSize = selectedSize)
                    } else {
                        it
                    }
                }
                return previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(size = selectedSize)
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