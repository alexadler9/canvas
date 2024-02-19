package ru.alexadler9.canvas.feature.view.tools

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

sealed class ToolItem {
    data class StyleModel(@DrawableRes val style: Int) : ToolItem()
    data class SizeModel(val size: Int) : ToolItem()
    data class ColorModel(@ColorRes val color: Int) : ToolItem()
}