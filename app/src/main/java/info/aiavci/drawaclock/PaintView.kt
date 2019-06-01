package info.aiavci.drawaclock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Custom view that allows user to create drawings
 */
class PaintView: View {

    private val paint = Paint().apply {
        isAntiAlias = true

        strokeWidth = 6f

        color = Color.BLACK

        style = Paint.Style.STROKE

        strokeJoin = Paint.Join.ROUND
    }

    private val path = Path()

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun performClick(): Boolean = super.performClick()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.performClick()

        val eventX = event.x
        val eventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(eventX, eventY)

                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(eventX, eventY)
            }

            MotionEvent.ACTION_UP -> {
            }

            else -> return false
        }

        invalidate()

        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(path, paint)
    }

    /**
     * Clears image
     */
    fun clearImage() {
        path.reset()

        invalidate()
    }
}