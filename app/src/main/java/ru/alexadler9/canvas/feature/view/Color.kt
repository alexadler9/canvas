package ru.alexadler9.canvas.feature.view

import androidx.annotation.ColorRes
import ru.alexadler9.canvas.R

/**
 * List of colors for setting the canvas.
 */
enum class Color(@ColorRes val value: Int) {
    BLACK(R.color.black),
    TEAL(R.color.teal_700)
}