package ru.alexadler9.canvas.feature.view.tools

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import ru.alexadler9.canvas.R
import ru.alexadler9.canvas.base.setAdapterAndCleanupOnDetachFromWindow

class ToolsLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var onClick: (Int) -> Unit = {}

    private val rvTools: RecyclerView by lazy { findViewById(R.id.rvTools) }

    private val adapterDelegate = ListDelegationAdapter(
        styleAdapterDelegate {
            onClick(it)
        },
        colorAdapterDelegate {
            onClick(it)
        },
        sizeAdapterDelegate {
            onClick(it)
        }
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        rvTools.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rvTools.setAdapterAndCleanupOnDetachFromWindow(adapterDelegate)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun render(list: List<ToolItem>) {
        adapterDelegate.items = list
        adapterDelegate.notifyDataSetChanged()
    }

    fun setOnClickListener(onClick: (Int) -> Unit) {
        this.onClick = onClick
    }
}