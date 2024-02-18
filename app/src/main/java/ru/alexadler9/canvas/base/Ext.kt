package ru.alexadler9.canvas.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setAdapterAndCleanupOnDetachFromWindow(recyclerViewAdapter: RecyclerView.Adapter<*>) {
    adapter = recyclerViewAdapter
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {

        override fun onViewDetachedFromWindow(v: View) {
            adapter = null
            removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View) {
        }
    })
}