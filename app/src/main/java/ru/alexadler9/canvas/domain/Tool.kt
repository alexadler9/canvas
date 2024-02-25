package ru.alexadler9.canvas.domain

import androidx.annotation.DrawableRes
import ru.alexadler9.canvas.R

/**
 * List of tools for setting the canvas.
 */
enum class Tool(
    @DrawableRes
    val value: Int
) {
    STYLE(R.drawable.shape_solid_line),
    PALETTE(R.drawable.ic_baseline_brightness_1_24),
    SIZE(android.R.color.transparent)
}