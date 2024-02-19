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
        stylesList = enumValues<Style>().map { ToolItem.StyleModel(it.value) },
        sizesList = enumValues<Size>().map { ToolItem.SizeModel(it.value) },
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
                val selectedStyle = Style.values()[action.index]
                Log.d(TAG, "Style changed ${selectedStyle.name}")
                previousState.copy(
                    canvasViewState = previousState.canvasViewState.copy(style = selectedStyle)
                )
            }

            is UiAction.OnSizeClicked -> {
                val selectedSize = Size.values()[action.index]
                Log.d(TAG, "Size changed ${selectedSize.name}")
                previousState.copy(
                    canvasViewState = previousState.canvasViewState.copy(size = selectedSize)
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