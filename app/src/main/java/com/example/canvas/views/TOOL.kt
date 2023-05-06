package com.example.canvas.views

import androidx.annotation.DrawableRes
import com.example.canvas.R

enum class TOOL(
    @DrawableRes
    val value: Int
) {
    NORMAL(R.drawable.shape_solid_line),
    DASH(R.drawable.shape_dashed_line),
    PALETTE(R.drawable.ic_baseline_brightness_1_24)
}