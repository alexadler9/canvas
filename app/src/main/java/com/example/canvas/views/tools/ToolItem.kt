package com.example.canvas.views.tools

import androidx.annotation.ColorRes
import com.example.canvas.views.COLOR
import com.example.canvas.views.SIZE
import com.example.canvas.views.TOOL

sealed class ToolItem {
    data class ToolModel(
        val type: TOOL,
        val isSelected: Boolean = false,
        val selectedColor: COLOR = COLOR.BLACK,
        val selectedSize: SIZE = SIZE.MEDIUM
    ) : ToolItem()

    data class ColorModel(@ColorRes val color: Int) : ToolItem()
    data class SizeModel(val size: Int) : ToolItem()
}