package ru.alexadler9.canvas.feature.view.canvas

import ru.alexadler9.canvas.domain.Color
import ru.alexadler9.canvas.domain.Size
import ru.alexadler9.canvas.domain.Style

data class CanvasViewState(
    val style: Style,
    val color: Color,
    val size: Size
)