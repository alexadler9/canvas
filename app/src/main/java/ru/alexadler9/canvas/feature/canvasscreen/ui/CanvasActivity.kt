package ru.alexadler9.canvas.feature.canvasscreen.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexadler9.canvas.databinding.ActivityCanvasBinding
import ru.alexadler9.canvas.feature.view.tools.ToolsLayout

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
    }

    private fun render(viewState: ViewState) {
        binding.canvasView.render(viewState.canvasViewState)
        (binding.layoutPalette as ToolsLayout).render(viewState.colorsList)
    }
}