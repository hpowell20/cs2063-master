package mobiledev.unb.ca.canvasdemo

import android.app.Activity
import android.widget.RelativeLayout
import android.os.Bundle
import android.graphics.Point
import android.os.Build
import android.view.WindowInsets

class MainActivity : Activity() {
    private var mFrame: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFrame = findViewById(R.id.frame)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Determine the screen size
            val (width, height) = getScreenDimensions(this)

            // Start the animation with 5 balls
            for (i in 0..4) {
                val bubbleView = BubbleView(applicationContext, width, height)
                mFrame?.addView(bubbleView)
                bubbleView.startMovement()
            }
        }
    }

    private fun getScreenDimensions(activity: Activity): Pair<Int, Int> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val windowInsets: WindowInsets = windowMetrics.windowInsets
            val insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout())

            val insetsWidth = insets.right + insets.left
            val insetsHeight = insets.top + insets.bottom

            val b = windowMetrics.bounds
            Pair(b.width() - insetsWidth, b.height() - insetsHeight)
        } else {
            val size = Point()
            @Suppress("DEPRECATION")
            val display = activity.windowManager.defaultDisplay // deprecated in API 30
            @Suppress("DEPRECATION")
            display?.getSize(size) // deprecated in API 30
            Pair(size.x, size.y)
        }
    }
}