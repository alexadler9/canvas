package com.example.canvas

import com.example.base.Event

data class ViewState(
    val toolsList: List<ToolItem.ToolModel>,
    val colorList: List<ToolItem.ColorModel>,
    val isPaletteVisible: Boolean,
    val isToolVisible: Boolean,
    val canvasViewState: CanvasViewState
)

sealed class UiEvent() : Event {
    data class OnPaletteClicked(val index: Int) : UiEvent()
    data class OnColorClicked(val index: Int) : UiEvent()
    data class OnSizeClicked(val index: Int) : UiEvent()
    data class OnToolsClicked(val index: Int) : UiEvent()
    object OnDrawViewClicked : UiEvent()
    object OnToolbarClicked : UiEvent()
}

sealed class DataEvent() : Event {
    data class OnSetDefaultTools(val tool: TOOLS, val color: COLOR) : DataEvent()
}