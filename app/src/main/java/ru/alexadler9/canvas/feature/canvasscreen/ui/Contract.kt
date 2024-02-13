package ru.alexadler9.canvas.feature.canvasscreen.ui

import ru.alexadler9.canvas.base.Action

data class ViewState(
    val stub: Boolean
)

sealed class ViewEvent {
}

sealed class UiAction : Action {
}

sealed class DataAction : Action {
}