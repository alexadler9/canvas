package ru.alexadler9.canvas.feature.canvasscreen.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexadler9.canvas.R
import ru.alexadler9.canvas.databinding.ActivityCanvasBinding
import ru.alexadler9.canvas.domain.Tool

private const val TAG = "CANVAS_ACTIVITY"

@AndroidEntryPoint
class CanvasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCanvasBinding

    private val viewModel: CanvasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCanvasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Add menu items using the MenuProvider API.

        val menuHost: MenuHost = this
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection.
                return when (menuItem.itemId) {
                    R.id.menuItemTools -> {
                        viewModel.processUiAction(UiAction.OnMenuToolsClicked)
                        true
                    }

                    R.id.menuItemClear -> {
                        viewModel.processUiAction(UiAction.OnMenuClearClicked)
                        true
                    }

                    else -> false
                }
            }
        })

        // Configure viewModel.

        viewModel.viewState
            .onEach(::render)
            .launchIn(lifecycleScope)

        viewModel.viewEvents
            .onEach(::handleEvent)
            .launchIn(lifecycleScope)

        // Configure listeners for tools.

        with(binding) {
            canvasView.setOnClickField {
                viewModel.processUiAction(UiAction.OnCanvasClicked)
            }
            layoutTool.root.setOnClickListener {
                viewModel.processUiAction(UiAction.OnToolClicked(it))
            }
            layoutStyle.root.setOnClickListener {
                viewModel.processUiAction(UiAction.OnStyleClicked(it))
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
            with(layoutTool.root) {
                render(viewState.toolsList)
                isVisible = viewState.toolsPanelVisible
            }
            with(layoutStyle.root) {
                render(viewState.stylesList)
                isVisible = viewState.toolsVisibility[Tool.STYLE.ordinal]
            }
            with(layoutPalette.root) {
                render(viewState.colorsList)
                isVisible = viewState.toolsVisibility[Tool.PALETTE.ordinal]
            }
            with(layoutSize.root) {
                render(viewState.sizesList)
                isVisible = viewState.toolsVisibility[Tool.SIZE.ordinal]
            }
        }
    }

    private fun handleEvent(viewEvent: ViewEvent?) {
        viewEvent?.let {
            when (viewEvent) {
                is ViewEvent.OnClearCanvas -> {
                    binding.canvasView.clear()
                }
            }
        }
    }
}