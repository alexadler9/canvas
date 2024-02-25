package ru.alexadler9.canvas.feature.view.tools

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import ru.alexadler9.canvas.domain.Color
import ru.alexadler9.canvas.domain.Size
import ru.alexadler9.canvas.domain.Style
import ru.alexadler9.canvas.domain.Tool

sealed class ToolItem {

    /**
     * Tool for setting the style.
     */
    data class StyleModel(@DrawableRes val style: Int) : ToolItem()

    /**
     * Tool for setting the color.
     */
    data class PaletteModel(@ColorRes val color: Int) : ToolItem()

    /**
     * Tool for setting the size.
     */
    data class SizeModel(val size: Int) : ToolItem()

    /**
     * Parent tool for setting the current tool.
     */
    data class ToolModel(
        val type: Tool,
        val isSelected: Boolean = false,
        val selectedStyle: Style = Style.NORMAL,
        val selectedColor: Color = Color.BLACK,
        val selectedSize: Size = Size.MEDIUM
    ) : ToolItem()
}