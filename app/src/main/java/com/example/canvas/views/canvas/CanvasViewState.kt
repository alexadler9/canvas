package com.example.canvas.views.canvas

import com.example.canvas.views.COLOR
import com.example.canvas.views.SIZE
import com.example.canvas.views.TOOL

data class CanvasViewState(
    val color: COLOR,
    val size: SIZE,
    val tool: TOOL
)