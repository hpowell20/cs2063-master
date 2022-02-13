package mobiledev.unb.ca.threadingviewpostdelayed

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
import android.widget.ImageView

class SimpleThreadingViewPostDelayedActivity : Activity() {
    private var mImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mImageView = findViewById(R.id.imageView)

        val loadButton = findViewById<Button>(R.id.loadButton)
        loadButton.setOnClickListener { onClickLoadButton(mImageView) }

        val otherButton = findViewById<Button>(R.id.otherButton)
        otherButton.setOnClickListener { onClickOtherButton() }
    }

    fun onClickOtherButton() {
        Toast.makeText(this@SimpleThreadingViewPostDelayedActivity, "I'm Working",
            Toast.LENGTH_SHORT).show()
    }

    fun onClickLoadButton(view: View?) {
        view!!.isEnabled = false
        Thread {
            // Using View.postDelayed() to access the thread
            mImageView!!.postDelayed({
                mImageView!!.setImageBitmap(BitmapFactory.decodeResource(resources,
                    R.drawable.painter))
            }, LOAD_DELAY.toLong())
        }.start()
    }

    companion object {
        const val LOAD_DELAY = 5000
    }
}