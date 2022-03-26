package mobiledev.unb.ca.canvasdemo

import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.os.Bundle
import android.graphics.BitmapFactory

class MainActivity : AppCompatActivity() {
    private var mFrame: RelativeLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFrame = findViewById(R.id.frame)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        // Set the bubble image from the drawable resource
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.b64)

        // Start the animation with 5 balls
        for (i in 0..4) {
            val bubbleView = BubbleView(applicationContext,
                bitmap,
                mFrame!!.width,
                mFrame!!.height)
            mFrame!!.addView(bubbleView)
        }
    }
}