package ru.alexadler9.canvas.feature.canvasscreen.ui

import android.util.Log
import ru.alexadler9.canvas.base.Action
import ru.alexadler9.canvas.base.BaseViewModel
import ru.alexadler9.canvas.feature.view.Color
import ru.alexadler9.canvas.feature.view.Size
import ru.alexadler9.canvas.feature.view.Style
import ru.alexadler9.canvas.feature.view.canvas.CanvasViewState
import ru.alexadler9.canvas.feature.view.tools.ToolItem

private const val TAG = "CANVAS_VM"

class CanvasViewModel : BaseViewModel<ViewState, ViewEvent>() {

    override val initialViewState = ViewState(
        colorsList = enumValues<Color>().map { ToolItem.ColorModel(it.value) },
        canvasViewState = CanvasViewState(
            style = Style.NORMAL,
            color = Color.BLACK,
            size = Size.MEDIUM,
        )
    )

    override fun reduce(action: Action, previousState: ViewState): ViewState? {
        return when (action) {
            is UiAction.OnStyleClicked -> {
                val newStyle = previousState.canvasViewState.style.run {
                    Style.values()[(ordinal + 1) % Style.values().size]
                }
                Log.d(TAG, "Style changed ${newStyle.name}")
                previousState.copy(
                    canvasViewState = previousState.canvasViewState.copy(style = newStyle)
                )
            }

            is UiAction.OnSizeClicked -> {
                val newSize = previousState.canvasViewState.size.run {
                    Size.values()[(ordinal + 1) % Size.values().size]
                }
                Log.d(TAG, "Size changed ${newSize.name}")
                previousState.copy(
                    canvasViewState = previousState.canvasViewState.copy(size = newSize)
                )
            }

            is UiAction.OnColorClicked -> {
                val selectedColor = Color.values()[action.index]
                Log.d(TAG, "Color changed ${selectedColor.name}")
                previousState.copy(
                    canvasViewState = previousState.canvasViewState.copy(color = selectedColor)
                )
            }

            else -> null
        }
    }
}