package ru.alexadler9.canvas.feature.canvasscreen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.alexadler9.canvas.data.PreferencesRepository

class CanvasViewModelFactory(private val repository: PreferencesRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CanvasViewModel::class.java)) {
            return CanvasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}