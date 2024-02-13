package ru.alexadler9.canvas.feature.canvasscreen.ui

import ru.alexadler9.canvas.base.Action
import ru.alexadler9.canvas.base.BaseViewModel

class CanvasViewModel : BaseViewModel<ViewState, ViewEvent>() {

    override val initialViewState = ViewState(
        stub = true
    )

    override fun reduce(action: Action, previousState: ViewState): ViewState? {
        return null
    }
}