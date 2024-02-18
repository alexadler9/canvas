package ru.alexadler9.canvas.feature.canvasscreen.ui

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexadler9.canvas.R
import ru.alexadler9.canvas.feature.view.canvas.CanvasView

private const val TAG = "CANVAS_ACTIVITY"

class CanvasActivity : AppCompatActivity() {

    private val viewModel: CanvasViewModel by viewModels()

    private val canvasView: CanvasView by lazy { findViewById(R.id.canvasView) }
    private val btnSize: Button by lazy { findViewById(R.id.btnSize) }
    private val btnColor: Button by lazy { findViewById(R.id.btnColor) }
    private val btnStyle: Button by lazy { findViewById(R.id.btnStyle) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        viewModel.viewState
            .onEach(::render)
            .launchIn(lifecycleScope)

        btnSize.setOnClickListener {
            viewModel.processUiAction(UiAction.OnSizeClicked)
        }
        btnColor.setOnClickListener {
            viewModel.processUiAction(UiAction.OnColorClicked)
        }
        btnStyle.setOnClickListener {
            viewModel.processUiAction(UiAction.OnStyleClicked)
        }
    }

    private fun render(viewState: ViewState) {
        canvasView.render(viewState.canvasViewState)
    }
}