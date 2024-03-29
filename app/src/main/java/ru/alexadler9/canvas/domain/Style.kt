package ru.alexadler9.canvas.domain

import androidx.annotation.DrawableRes
import ru.alexadler9.canvas.R

/**
 * List of styles for setting the canvas.
 */
enum class Style(
    @DrawableRes
    val value: Int
) {
    NORMAL(R.drawable.shape_solid_line),
    DASH(R.drawable.shape_dashed_line)
}