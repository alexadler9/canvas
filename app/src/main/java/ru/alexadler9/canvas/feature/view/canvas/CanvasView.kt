package ru.alexadler9.canvas.feature.view.canvas

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import ru.alexadler9.canvas.R
import ru.alexadler9.canvas.domain.Style
import kotlin.math.abs

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var onClick: () -> Unit = {}

    private var touchX = 0f
    private var touchY = 0f

    private var currentX = 0f
    private var currentY = 0f
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    // For caching.
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private var path = Path()

    // Painting settings.
    private val paint = Paint().apply {
        color = ResourcesCompat.getColor(resources, R.color.black, null)
        // Smooth out edges of what is drawn without affecting shape:
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled:
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 12f
    }

    fun render(state: CanvasViewState) {
        paint.color = ResourcesCompat.getColor(resources, state.color.value, null)
        paint.strokeWidth = state.size.value.toFloat()
        when (state.style) {
            Style.DASH -> {
                paint.pathEffect = DashPathEffect(
                    floatArrayOf(
                        state.size.value.toFloat() * 2,
                        state.size.value.toFloat() * 2,
                        state.size.value.toFloat() * 2,
                        state.size.value.toFloat() * 2
                    ), 0f
                )
            }

            Style.NORMAL -> {
                paint.pathEffect = null
            }
        }
    }

    fun clear() {
        extraCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    fun setOnClickField(onClickField: () -> Unit) {
        onClick = onClickField
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        touchX = event.x
        touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }

        return true
    }

    private fun updateCurrentXY() {
        currentX = touchX
        currentY = touchY
    }

    private fun touchStart() {
        onClick()
        path.reset()
        // Fixing the starting point.
        path.moveTo(touchX, touchY)
        updateCurrentXY()
    }

    private fun touchMove() {
        val dx = abs(touchX - currentX)
        val dy = abs(touchY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // Drawing a quadratic bezier curve.
            // The end of the previous quadTo() will be the next starting point.
            path.quadTo(
                currentX,
                currentY,
                (touchX + currentX) / 2,
                (touchY + currentY) / 2
            )
            updateCurrentXY()
            // Draw the path in the extra bitmap to cache it.
            extraCanvas.drawPath(path, paint)
            extraCanvas.save()
        }
        invalidate()
    }

    private fun touchUp() {
        path.reset()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        if (::extraBitmap.isInitialized) {
            // To avoid memory leaks, call the recycle() to free old resources.
            extraBitmap.recycle()
        }
        // Create a cached Bitmap and associated Canvas.
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }
}