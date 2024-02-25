package ru.alexadler9.canvas.data.local

interface IAppPreferencesSource {

    fun getStyle(): String

    fun setStyle(style: String)

    fun getColor(): String

    fun setColor(color: String)

    fun getSize(): String

    fun setSize(size: String)
}