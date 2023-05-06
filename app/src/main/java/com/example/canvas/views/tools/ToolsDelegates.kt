package com.example.canvas.views.tools

import android.graphics.PorterDuff
import android.widget.ImageView
import com.example.canvas.R
import com.example.canvas.views.TOOL
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer

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

fun toolAdapterDelegate(
    onToolClick: (Int) -> Unit
): AdapterDelegate<List<ToolItem>> =
    adapterDelegateLayoutContainer<ToolItem.ToolModel, ToolItem>(
        R.layout.item_tool
    ) {

        val ivTool: ImageView = findViewById(R.id.ivTool)
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