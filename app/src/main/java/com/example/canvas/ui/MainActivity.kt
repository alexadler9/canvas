package com.example.canvas.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.canvas.R
import com.example.canvas.views.canvas.CanvasView
import com.example.canvas.views.tools.ToolsLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PALETTE_VIEW = 0
        private const val TOOLS_VIEW = 1
    }

    private val viewModel: MainViewModel by viewModel()

    private var toolsList: List<ToolsLayout> = listOf()

    private val layoutPalette: ToolsLayout by lazy { findViewById(R.id.layoutPalette) }
    private val layoutTools: ToolsLayout by lazy { findViewById(R.id.layoutTools) }
    private val ivCleaning: ImageView by lazy { findViewById(R.id.ivCleaning) }
    private val ivTools: ImageView by lazy { findViewById(R.id.ivTools) }
    private val canvasView: CanvasView by lazy { findViewById(R.id.canvasView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivCleaning.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnIconCleaningClicked)
            canvasView.clear()
        }
        ivTools.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnIconToolsClicked)
        }

        canvasView.setOnClickField {
            viewModel.processUiEvent(UiEvent.OnCanvasClicked)
        }

        toolsList = listOf(layoutPalette, layoutTools)

        layoutPalette.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnPaletteClicked(it))
        }
        layoutTools.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolsClicked(it))
        }

        viewModel.viewState.observe(this, ::render)
    }

    private fun render(viewState: ViewState) {
        with(toolsList[PALETTE_VIEW]) {
            render(viewState.colorList)
            isVisible = viewState.isPaletteVisible
        }
        with(toolsList[TOOLS_VIEW]) {
            render(viewState.toolsList)
            isVisible = viewState.isToolsVisible
        }
        canvasView.render(viewState.canvasViewState)
    }
}