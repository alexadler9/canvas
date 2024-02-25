package ru.alexadler9.canvas.data.local

import co.windly.ktxprefs.annotation.DefaultString
import co.windly.ktxprefs.annotation.Prefs

@Prefs(value = "AppPreferences")
class AppPreferences(
    /** Last selected style for setting the canvas. */
    @DefaultString(value = "")
    internal val style: String,

    /** Last selected color for setting the canvas. */
    @DefaultString(value = "")
    internal val color: String,

    /** Last selected size for setting the canvas. */
    @DefaultString(value = "")
    internal val size: String
)