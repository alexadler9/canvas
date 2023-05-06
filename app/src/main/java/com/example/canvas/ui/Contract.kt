package com.example.canvas.ui

import com.example.base.Event
import com.example.canvas.views.COLOR
import com.example.canvas.views.TOOL
import com.example.canvas.views.canvas.CanvasViewState
import com.example.canvas.views.tools.ToolItem

data class ViewState(
    val toolsList: List<ToolItem.ToolModel>,
    val colorList: List<ToolItem.ColorModel>,
    val isToolsVisible: Boolean,
    val isPaletteVisible: Boolean,
    val canvasViewState: CanvasViewState
)

sealed class UiEvent() : Event {
    object OnIconCleaningClicked : UiEvent()
    object OnIconToolsClicked : UiEvent()
    object OnCanvasClicked : UiEvent()
    data class OnToolsClicked(val index: Int) : UiEvent()
    data class OnPaletteClicked(val index: Int) : UiEvent()
}

sealed class DataEvent() : Event {
    data class OnSetDefaultTools(val tool: TOOL, val color: COLOR) : DataEvent()
}