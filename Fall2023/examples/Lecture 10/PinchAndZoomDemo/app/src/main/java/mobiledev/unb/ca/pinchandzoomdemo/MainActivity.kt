package mobiledev.unb.ca.pinchandzoomdemo

import androidx.appcompat.app.AppCompatActivity
import android.view.ScaleGestureDetector
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.ImageView
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private var imageView: ImageView? = null

    // ScaleGestureListener is used to handle pinch gestures
    private var scaleGestureDetector: ScaleGestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener(imageView!!))
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        scaleGestureDetector!!.onTouchEvent(ev)
        return true
    }

    inner class ScaleListener internal constructor(private var mImageView: ImageView): SimpleOnScaleGestureListener() {
        private var scaleFactor = 1.0f

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector!!.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            mImageView.scaleX = scaleFactor
            mImageView.scaleY = scaleFactor
            return true
        }
    }
}