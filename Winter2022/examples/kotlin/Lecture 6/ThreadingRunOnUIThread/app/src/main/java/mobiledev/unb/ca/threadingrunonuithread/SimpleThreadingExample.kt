package mobiledev.unb.ca.threadingrunonuithread

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import android.graphics.BitmapFactory
import android.widget.Button
import android.widget.ImageView

class SimpleThreadingExample : Activity() {
    private var mImageView: ImageView? = null
    private val mDelay = 5000

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        mImageView = findViewById(R.id.imageView)

        val loadButton = findViewById<Button>(R.id.loadButton)
        loadButton.setOnClickListener { onClickLoadButton() }

        val otherButton = findViewById<Button>(R.id.otherButton)
        otherButton.setOnClickListener { onClickOtherButton() }
    }

    fun onClickOtherButton() {
        Toast.makeText(this@SimpleThreadingExample, "I'm Working",
            Toast.LENGTH_SHORT).show()
    }

    fun onClickLoadButton() {
        Thread {
            try {
                Thread.sleep(mDelay.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            // Use Activity.runOnUiThread to access the UI thread
            runOnUiThread {
                mImageView!!.setImageBitmap(BitmapFactory.decodeResource(resources,
                    R.drawable.painter))
            }
        }.start()
    }
}