package info.aiavci.drawaclock

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import info.aiavci.drawaclock.ObjectDetection.detectCircleUsingContours
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Custom view that allows user to create drawings
 */
class PaintView: View {
    var drawingAnalyzer = DrawingAnalyzer()

    val conf = Bitmap.Config.ARGB_8888

    var isLookingForSomething = false

    var bitmapObject: Bitmap? = null
    var bitmapCanvas: Canvas? = null

    private val paint = Paint().apply {
        isAntiAlias = true

        strokeWidth = 6f

        color = Color.BLACK

        style = Paint.Style.STROKE

        strokeJoin = Paint.Join.ROUND
    }

//    private val path = Path()

    private var listOfPaths = mutableListOf<Path>()

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
//                path.moveTo(eventX, eventY)
                listOfPaths.add(Path().apply {
                    moveTo(eventX, eventY)
                })

                return true
            }

            MotionEvent.ACTION_MOVE -> {
                listOfPaths.last().lineTo(eventX, eventY)
            }

            MotionEvent.ACTION_UP -> {
                bitmapObject = Bitmap.createBitmap(width, height, conf)

                bitmapCanvas = Canvas(bitmapObject).apply {
                    drawColor(Color.WHITE)
                }

                GlobalScope.launch {
                    val bitmap = bitmapObject ?: return@launch

                    if (isLookingForSomething) {

                    } else {
                        // Found what we're looking for
                        drawingAnalyzer.images.add(bitmap)
                    }

                    val result = detectCircleUsingContours(bitmap)


                    Timber.d("Check" + result)

                    saveBitmap("image.png")
                }
            }

            else -> return false
        }

        invalidate()

        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        canvas.drawPath(path, paint)

        listOfPaths.forEach {path ->
            canvas.drawPath(path, paint)

            bitmapCanvas?.drawPath(path, paint)
            // Draw on bitmap
        }
    }

    private fun saveBitmap(fileName: String) {
        try {
            val file = File(context.filesDir, fileName)

            FileOutputStream(file.absoluteFile).use { out ->
                bitmapObject?.compress(Bitmap.CompressFormat.PNG, 100, out) // bmp is your Bitmap instance
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Clears image
     */
    fun clearImage() {
//        path.reset()

        listOfPaths.forEach {path ->
            path.reset()
        }

        invalidate()
    }
}