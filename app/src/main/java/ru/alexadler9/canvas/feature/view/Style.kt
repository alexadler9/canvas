package ru.alexadler9.canvas.feature.view

import androidx.annotation.DrawableRes
import ru.alexadler9.canvas.R

enum class Style(
    @DrawableRes
    val value: Int
) {
    NORMAL(R.drawable.shape_solid_line),
    DASH(R.drawable.shape_dashed_line)
}