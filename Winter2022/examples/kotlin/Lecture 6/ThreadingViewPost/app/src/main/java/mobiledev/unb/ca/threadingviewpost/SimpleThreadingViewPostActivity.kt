package mobiledev.unb.ca.threadingviewpost

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
import android.widget.ImageView

class SimpleThreadingViewPostActivity : Activity() {
    private var mImageView: ImageView? = null
    private val mDelay = 5000

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        mImageView = findViewById(R.id.imageView)

        val loadButton = findViewById<Button>(R.id.loadButton)
        loadButton.setOnClickListener { onClickLoadButton(mImageView) }

        val otherButton = findViewById<Button>(R.id.otherButton)
        otherButton.setOnClickListener { onClickOtherButton() }
    }

    fun onClickOtherButton() {
        Toast.makeText(this@SimpleThreadingViewPostActivity, "I'm Working",
            Toast.LENGTH_SHORT).show()
    }

    fun onClickLoadButton(view: View?) {
        view!!.isEnabled = false
        Thread {
            try {
                Thread.sleep(mDelay.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            // Using View.post() to access the thread
            mImageView!!.post {
                mImageView!!.setImageBitmap(BitmapFactory.decodeResource(resources,
                    R.drawable.painter))
            }
        }.start()
    }
}