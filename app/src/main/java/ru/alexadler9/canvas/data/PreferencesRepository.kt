package ru.alexadler9.canvas.data

import ru.alexadler9.canvas.base.enumContains
import ru.alexadler9.canvas.data.local.IAppPreferencesSource
import ru.alexadler9.canvas.domain.Color
import ru.alexadler9.canvas.domain.Size
import ru.alexadler9.canvas.domain.Style
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for working with local app settings.
 */
@Singleton
class PreferencesRepository @Inject constructor(
    private val appPreferencesSource: IAppPreferencesSource
) {

    /**
     * Get the last selected style for setting the canvas.
     */
    fun getStyle(): Style {
        val style = appPreferencesSource.getStyle()
        return if (enumContains<Style>(style))
            Style.valueOf(style) else
            Style.values().first()
    }

    /**
     * Save the style.
     * @param style The style.
     */
    fun setStyle(style: Style) {
        appPreferencesSource.setStyle(style.name)
    }

    /**
     * Get the last selected color for setting the canvas.
     */
    fun getColor(): Color {
        val color = appPreferencesSource.getColor()
        return if (enumContains<Color>(color))
            Color.valueOf(color) else
            Color.values().first()
    }

    /**
     * Save the color.
     * @param color The color.
     */
    fun setColor(color: Color) {
        appPreferencesSource.setColor(color.name)
    }

    /**
     * Get the last selected size for setting the canvas.
     */
    fun getSize(): Size {
        val size = appPreferencesSource.getSize()
        return if (enumContains<Size>(size))
            Size.valueOf(size) else
            Size.values().first()
    }

    /**
     * Save the size.
     * @param size The size.
     */
    fun setSize(size: Size) {
        appPreferencesSource.setSize(size.name)
    }
}