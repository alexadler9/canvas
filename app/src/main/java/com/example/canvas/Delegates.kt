package com.example.canvas

import android.graphics.PorterDuff
import android.widget.ImageView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer

fun colorAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ToolItem.ColorModel, Item>(
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

fun toolsAdapterDelegate(
    onToolsClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ToolItem.ToolModel, Item>(
        R.layout.item_tools
    ) {

        val ivTool: ImageView = findViewById(R.id.ivTool)
        itemView.setOnClickListener { onToolsClick(adapterPosition) }

        bind {
            ivTool.setImageResource(item.type.value)

            when (item.type) {
//                TOOLS.SIZE -> {
//                    itemView.tvToolsText.visibility = View.VISIBLE
//                    itemView.ivToolsText.text = item.selectedSize.value.toString()
//                }

                TOOLS.PALETTE -> {
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