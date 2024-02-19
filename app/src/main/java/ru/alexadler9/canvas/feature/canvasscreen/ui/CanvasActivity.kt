package ru.alexadler9.canvas.feature.canvasscreen.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexadler9.canvas.R
import ru.alexadler9.canvas.databinding.ActivityCanvasBinding

private const val TAG = "CANVAS_ACTIVITY"

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
                return true
            }
        })

        // Configure viewModel.

        viewModel.viewState
            .onEach(::render)
            .launchIn(lifecycleScope)

        // Configure listeners for tools.

        with(binding) {
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
            layoutStyle.root.render(viewState.stylesList)
            layoutPalette.root.render(viewState.colorsList)
            layoutSize.root.render(viewState.sizesList)
        }
    }
}