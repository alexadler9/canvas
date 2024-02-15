package ru.alexadler9.canvas.feature.canvasscreen.ui

import ru.alexadler9.canvas.base.Action
import ru.alexadler9.canvas.feature.view.canvas.CanvasViewState

data class ViewState(
    val canvasViewState: CanvasViewState
)

sealed class ViewEvent {
}

sealed class UiAction : Action {
    object OnStyleClicked : UiAction()
    object OnColorClicked : UiAction()
    object OnSizeClicked : UiAction()
}

sealed class DataAction : Action {
}