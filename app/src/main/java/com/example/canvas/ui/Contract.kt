package com.example.canvas.ui

import com.example.base.Event
import com.example.canvas.views.COLOR
import com.example.canvas.views.SIZE
import com.example.canvas.views.TOOL
import com.example.canvas.views.canvas.CanvasViewState
import com.example.canvas.views.tools.ToolItem

data class ViewState(
    val toolsList: List<ToolItem.ToolModel>,
    val colorsList: List<ToolItem.ColorModel>,
    val sizesList: List<ToolItem.SizeModel>,
    val isToolsVisible: Boolean,
    val isPaletteVisible: Boolean,
    val isSizesVisible: Boolean,
    val canvasViewState: CanvasViewState
)

sealed class UiEvent() : Event {
    object OnIconCleaningClicked : UiEvent()
    object OnIconToolsClicked : UiEvent()
    object OnCanvasClicked : UiEvent()
    data class OnToolClicked(val index: Int) : UiEvent()
    data class OnColorClicked(val index: Int) : UiEvent()
    data class OnSizeClicked(val index: Int) : UiEvent()
}

sealed class DataEvent() : Event {
    data class OnSetDefaultTools(val tool: TOOL, val color: COLOR, val size: SIZE) : DataEvent()
}