package mobiledev.unb.ca.pinchandzoomdemo

import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.view.ScaleGestureDetector
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.ImageView
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private var iv: ImageView? = null
    private val matrix = Matrix()
    private var scale = 1f

    // ScaleGestureListener is used to handle pinch gestures
    private var scaleGestureDetector: ScaleGestureDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iv = findViewById(R.id.imageView)
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        scaleGestureDetector!!.onTouchEvent(ev)
        return true
    }

    private inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scale *= detector.scaleFactor
            scale = max(0.1f, min(scale, 5.0f))
            matrix.setScale(scale, scale)
            iv!!.imageMatrix = matrix
            return true
        }
    }
}