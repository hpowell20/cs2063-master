/*
 * When "Load Icon" Button is pressed throws 
 * android.view.ViewRootImpl$CalledFromWrongThreadException: 
 * Only the original thread that created a view hierarchy can touch its views.
 */
package mobiledev.unb.ca.threadingsimple

import android.app.Activity
import android.os.Bundle
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class SimpleThreadingExample : Activity() {
    private var mIView: ImageView? = null
    private val mDelay = 5000

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        mIView = findViewById(R.id.imageView)

        val loadButton = findViewById<Button>(R.id.loadButton)
        loadButton.setOnClickListener { onClickLoadButton() }

        val otherButton = findViewById<Button>(R.id.otherButton)
        otherButton.setOnClickListener { onClickOtherButton() }
    }

    fun onClickLoadButton() {
        Thread {
            try {
                Thread.sleep(mDelay.toLong())
            } catch (e: InterruptedException) {
                Log.e(TAG, e.toString())
            }

            // This doesn't work in Android
            mIView!!.setImageBitmap(BitmapFactory.decodeResource(resources,
                R.drawable.painter))
        }.start()
    }

    fun onClickOtherButton() {
        Toast.makeText(this@SimpleThreadingExample, "I'm Working",
            Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "SimpleThreadingExample"
    }
}