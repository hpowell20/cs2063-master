package mobiledev.unb.ca.simplegesturedemo

import android.os.Bundle
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat

class MainActivity : AppCompatActivity() {
    private var mDetector: GestureDetectorCompat? = null
    private var mTextView: TextView? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextView = findViewById(R.id.textview)
        mDetector = GestureDetectorCompat(this, MyGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mDetector!!.onTouchEvent(event)
    }

    internal inner class MyGestureListener : SimpleOnGestureListener() {
        override fun onDown(event: MotionEvent): Boolean {
            mTextView!!.setText(R.string.lbl_on_down)
            return true
        }

        override fun onFling(
            event1: MotionEvent?, event2: MotionEvent,
            velocityX: Float, velocityY: Float,
        ): Boolean {
            val text = getString(R.string.lbl_on_fling, velocityX, velocityY)
            mTextView!!.text = text
            return true
        }

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            mTextView!!.setText(R.string.lbl_on_single_tap_confirmed)
            return true
        }
    }
}