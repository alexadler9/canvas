package com.example.canvas.views.tools

import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.canvas.R
import com.example.canvas.views.TOOL
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer

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
                TOOL.PALETTE -> {
                    ivTool.setColorFilter(
                        itemView.resources.getColor(item.selectedColor.value, null),
                        PorterDuff.Mode.SRC_IN
                    )
                }

                TOOL.SIZE -> {
                    ivTool.isVisible = false
                    tvTool.isVisible = true
                    tvTool.text = getString(R.string.tool_size, item.selectedSize.value)
                }

                else -> {
                    if (item.isSelected) {
                        ivTool.setBackgroundResource(R.drawable.background_selected)
                    } else {
                        ivTool.setBackgroundResource(android.R.color.transparent)
                    }
                }
            }
        }
    }

fun colorAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<ToolItem>> =
    adapterDelegateLayoutContainer<ToolItem.ColorModel, ToolItem>(
        R.layout.item_palette
    ) {

        val ivPalette: ImageView = findViewById(R.id.ivPalette)
        itemView.setOnClickListener { onClick(adapterPosition) }

        bind {
            ivPalette.setColorFilter(
                context.resources.getColor(item.color, null),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

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