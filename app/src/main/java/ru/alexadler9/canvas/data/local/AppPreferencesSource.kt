package ru.alexadler9.canvas.data.local

import android.content.Context

/**
 * Source for accessing user settings.
 */
class AppPreferencesSource(private val appContext: Context) : IAppPreferencesSource {

    /**
     * Get the last selected style for setting the canvas.
     */
    override fun getStyle() = appContext.requireAppPreferences().getStyle()

    /**
     * Save the style.
     * @param style The style.
     */
    override fun setStyle(style: String) {
        appContext.requireAppPreferences().setStyle(style)
    }

    /**
     * Get the last selected color for setting the canvas.
     */
    override fun getColor() = appContext.requireAppPreferences().getColor()

    /**
     * Save the color.
     * @param color The color.
     */
    override fun setColor(color: String) {
        appContext.requireAppPreferences().setColor(color)
    }

    /**
     * Get the last selected size for setting the canvas.
     */
    override fun getSize() = appContext.requireAppPreferences().getSize()

    /**
     * Save the size.
     * @param size The size.
     */
    override fun setSize(size: String) {
        appContext.requireAppPreferences().setSize(size)
    }
}