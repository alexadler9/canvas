package ru.alexadler9.canvas.feature.view.tools

import androidx.annotation.ColorRes

sealed class ToolItem {
    data class SizeModel(val size: Int) : ToolItem()
    data class ColorModel(@ColorRes val color: Int) : ToolItem()
}