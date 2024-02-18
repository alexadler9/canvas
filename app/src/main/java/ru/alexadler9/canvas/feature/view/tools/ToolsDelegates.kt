package ru.alexadler9.canvas.feature.view.tools

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import ru.alexadler9.canvas.R

/**
 * Adapter for palette tool.
 */
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
                ResourcesCompat.getColor(context.resources, item.color, null),
                PorterDuff.Mode.SRC_IN
            )
        }
    }