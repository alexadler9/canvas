package ru.alexadler9.canvas.feature.view.tools

import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import ru.alexadler9.canvas.R
import ru.alexadler9.canvas.feature.view.Tool

/**
 * Adapter for parent tool containing other tools.
 */
fun toolAdapterDelegate(
    onToolClick: (Int) -> Unit
): AdapterDelegate<List<ToolItem>> =
    adapterDelegateLayoutContainer<ToolItem.ToolModel, ToolItem>(
        R.layout.item_tool
    ) {

        val ivTool: ImageView = findViewById(R.id.ivTool)
        val tvTool: TextView = findViewById(R.id.tvTool)
        itemView.setOnClickListener { onToolClick(adapterPosition) }

        bind {
            ivTool.setImageResource(item.type.value)

            when (item.type) {
                Tool.STYLE -> {
                    // Display current style from the tool style.
                    ivTool.setImageResource(item.selectedStyle.value)
                }

                Tool.PALETTE -> {
                    // Paint the tool image with the current color from the palette tool.
                    ivTool.setColorFilter(
                        ResourcesCompat.getColor(
                            itemView.resources,
                            item.selectedColor.value,
                            null
                        ),
                        PorterDuff.Mode.SRC_IN
                    )
                }

                Tool.SIZE -> {
                    // Display current size from the tool size.
                    ivTool.isVisible = false
                    tvTool.isVisible = true
                    tvTool.text = getString(R.string.tool_size, item.selectedSize.value)
                }
            }
        }
    }

/**
 * Adapter for style tool.
 */
fun styleAdapterDelegate(
    onToolClick: (Int) -> Unit
): AdapterDelegate<List<ToolItem>> =
    adapterDelegateLayoutContainer<ToolItem.StyleModel, ToolItem>(
        R.layout.item_style
    ) {

        val ivStyle: ImageView = findViewById(R.id.ivStyle)
        itemView.setOnClickListener { onToolClick(adapterPosition) }

        bind {
            ivStyle.setImageResource(item.style)
        }
    }

/**
 * Adapter for palette tool.
 */
fun paletteAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<ToolItem>> =
    adapterDelegateLayoutContainer<ToolItem.PaletteModel, ToolItem>(
        R.layout.item_palette
    ) {

        val ivPalette: ImageView = findViewById(R.id.ivPalette)
        itemView.setOnClickListener { onClick(adapterPosition) }

        bind {
            ivPalette.setColorFilter(
                ResourcesCompat.getColor(context.resources, item.color, null),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

/**
 * Adapter for size tool.
 */
fun sizeAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<ToolItem>> =
    adapterDelegateLayoutContainer<ToolItem.SizeModel, ToolItem>(
        R.layout.item_size
    ) {

        val tvSize: TextView = findViewById(R.id.tvSize)
        itemView.setOnClickListener { onClick(adapterPosition) }

        bind {
            tvSize.text = getString(R.string.tool_size, item.size)
        }
    }