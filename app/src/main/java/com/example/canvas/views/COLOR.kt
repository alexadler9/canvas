package com.example.canvas.views

import androidx.annotation.ColorRes
import com.example.canvas.R

enum class COLOR(
    @ColorRes
    val value: Int
) {
    BLACK(R.color.black),
    RED(R.color.red),
    GREEN(R.color.green),
    BLUE(R.color.blue),
    YELLOW(R.color.yellow),
    ORANGE(R.color.orange);

    companion object {
        private val map = values().associateBy(COLOR::value)
        fun from(color: Int) = map[color] ?: BLACK
    }
}