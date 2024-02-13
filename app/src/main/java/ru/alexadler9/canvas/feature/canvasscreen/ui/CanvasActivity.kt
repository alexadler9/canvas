package ru.alexadler9.canvas.feature.canvasscreen.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexadler9.canvas.R

const val TAG = "CANVAS_ACTIVITY"

class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        val viewModel = ViewModelProvider(this).get(CanvasViewModel::class.java)

        viewModel.viewState
            .onEach(::render)
            .launchIn(lifecycleScope)
    }

    private fun render(viewState: ViewState) {
        Log.d(TAG, viewState.stub.toString())
    }
}