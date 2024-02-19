package ru.alexadler9.canvas.feature.canvasscreen.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexadler9.canvas.databinding.ActivityCanvasBinding

private const val TAG = "CANVAS_ACTIVITY"

class CanvasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCanvasBinding

    private val viewModel: CanvasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCanvasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.viewState
            .onEach(::render)
            .launchIn(lifecycleScope)

        with(binding) {
            btnStyle.setOnClickListener {
                viewModel.processUiAction(UiAction.OnStyleClicked)
            }
            layoutPalette.root.setOnClickListener {
                viewModel.processUiAction(UiAction.OnColorClicked(it))
            }
            layoutSize.root.setOnClickListener {
                viewModel.processUiAction(UiAction.OnSizeClicked(it))
            }
        }
    }

    private fun render(viewState: ViewState) {
        with(binding) {
            canvasView.render(viewState.canvasViewState)
            layoutPalette.root.render(viewState.colorsList)
            layoutSize.root.render(viewState.sizesList)
        }
    }
}