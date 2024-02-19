package ru.alexadler9.canvas.feature.canvasscreen.ui

import ru.alexadler9.canvas.base.Action
import ru.alexadler9.canvas.feature.view.canvas.CanvasViewState
import ru.alexadler9.canvas.feature.view.tools.ToolItem

data class ViewState(
    val sizesList: List<ToolItem.SizeModel>,
    val colorsList: List<ToolItem.ColorModel>,
    val canvasViewState: CanvasViewState
)

sealed class ViewEvent {
}

sealed class UiAction : Action {
    object OnStyleClicked : UiAction()
    data class OnColorClicked(val index: Int) : UiAction()
    data class OnSizeClicked(val index: Int) : UiAction()
}

sealed class DataAction : Action {
}