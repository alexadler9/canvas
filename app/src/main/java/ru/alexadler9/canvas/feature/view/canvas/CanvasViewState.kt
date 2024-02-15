package ru.alexadler9.canvas.feature.view.canvas

import ru.alexadler9.canvas.feature.view.Color
import ru.alexadler9.canvas.feature.view.Size
import ru.alexadler9.canvas.feature.view.Style

data class CanvasViewState(
    val style: Style,
    val color: Color,
    val size: Size
)