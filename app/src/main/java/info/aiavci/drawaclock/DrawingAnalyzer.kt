package info.aiavci.drawaclock

import android.graphics.Bitmap

/**
 * Helper class to analyze drawing content
 */
class DrawingAnalyzer {
    //Next item to look for
    var nextItemToLookFor = "1"

    // Map of images that have been successfully registered
    var successfulImages = mutableListOf<Bitmap>()

    /**
     * Detects whether or not an image has a circle
     */
    fun isCircleACircle(): Boolean {
        return true
    }

    /**
     * Returns the number detected
     */
    fun numberDetected(): Int {

        return 1//1 - 12
    }

    /**
     * Determines object length
     */
    fun objectLength(): Int {
        return 55
    }
}