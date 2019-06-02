package info.aiavci.drawaclock


//import org.opencv.highgui.HighGui
import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

object ObjectDetection {

    fun detectCircleUsingHoughCircleTransform(filename: String) {
        val src = Imgcodecs.imread(filename, Imgcodecs.IMREAD_COLOR)

        //convert to gray
        val gray = Mat()
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY)

        //blur
        Imgproc.medianBlur(gray, gray, 5)

        //detect circles
        val circles = Mat()
        Imgproc.HoughCircles(
            gray, circles, Imgproc.HOUGH_GRADIENT, 1.0,
            gray.rows().toDouble(), // change this value to detect circles with different distances to each other
            150.0, 30.0, 200, 0
        ) // change the last two parameters
        // (min_radius & max_radius) to detect larger circles

        for (x in 0 until circles.cols()) {
            val c = circles.get(0, x)
            val center = Point(Math.round(c[0]).toDouble(), Math.round(c[1]).toDouble())
            // circle center
            Imgproc.circle(src, center, 1, Scalar(0.0, 100.0, 100.0), 3, 8, 0)
            // circle outline
            val radius = Math.round(c[2]).toInt()
            Imgproc.circle(src, center, radius, Scalar(255.0, 0.0, 255.0), 3, 8, 0)
        }

    }

    fun detectCircleUsingContours(inputBitmap: Bitmap): Boolean {
        val srcMat = Mat()

        Utils.bitmapToMat(inputBitmap, srcMat)

        Imgproc.cvtColor(srcMat, srcMat, Imgproc.COLOR_RGB2GRAY)

        val result =  Mat()
        Imgproc.adaptiveThreshold(srcMat, result, 255.0, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 3, 0.0)

        val contours = mutableListOf<MatOfPoint>()

        // System.out.println("Done converting image..");

        Imgproc.findContours(
            result, contours, Mat(), Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_SIMPLE
        )

        val contourImg = Mat(result.size(), result.type())
        //  System.out.println(contours.size());
        val points = contours[0]

        //then use center point to calculate the radius of the results and see if it varies
        var xMax = 0.0
        var xMin = 0.0
        var yMax = 0.0
        var yMin = 0.0


        for (i in 0 until points.size(0)) {
            val point = points.get(i, 0)
            if (i == 0) {
                xMin = point[0]
                yMin = point[1]
            }
            if (point[0] > xMax) xMax = point[0]
            if (point[0] < xMin) xMin = point[0]
            if (point[1] > yMax) yMax = point[1]
            if (point[1] < yMin) yMin = point[1]

            //System.out.println(Arrays.toString(point));
        }

        val center_point = doubleArrayOf((xMax - xMin) / 2, (yMax - yMin) / 2)
        // System.out.println(xMax +" "+ xMin+ " "+ yMax+ " "+ yMin);
        //System.out.println(Arrays.toString(center_point));

        var maxR = 0.0
        var minR = 0.0
        for (i in 0 until points.size(0)) {
            val point = points.get(i, 0)
            val radius =
                Math.sqrt(Math.pow(point[0] - center_point[0], 2.0) + Math.pow(point[1] - center_point[1], 2.0))
            if (i == 0) {
                minR = radius
            }
            if (maxR < radius) {
                maxR = radius
            }
            if (minR > radius) {
                minR = radius
            }
        }
        //System.out.println(maxR +" " +minR);

        for (i in contours.indices) {
            Imgproc.drawContours(contourImg, contours, i, Scalar(255.0, 255.0, 255.0), 1)
        }

        val tol = 39

        return maxR < center_point[0] + center_point[0] * tol / 100 && minR > center_point[0] - center_point[0] * tol / 100
    }

    /**
     * Checks if last drawn item is what's expected
     */
    fun isNumberIdentical(
        lastProperImage: Bitmap,
        currentImage: Bitmap,
        expectedValue: String
    ): Boolean {

        return false
    }
}