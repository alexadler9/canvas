package ru.alexadler9.canvas.domain

import androidx.annotation.ColorRes
import ru.alexadler9.canvas.R

/**
 * List of colors for setting the canvas.
 */
enum class Color(@ColorRes val value: Int) {
    BLACK(R.color.black),
    RED(R.color.red),
    GREEN(R.color.green),
    BLUE(R.color.blue),
    TEAL(R.color.teal_700),
    PURPLE(R.color.purple_500),
    YELLOW(R.color.yellow),
    ORANGE(R.color.orange)
}