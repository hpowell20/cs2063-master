package mobiledev.unb.ca.gesturedemo

import androidx.appcompat.app.AppCompatActivity
import android.view.GestureDetector
import android.view.GestureDetector.OnDoubleTapListener
import androidx.core.view.GestureDetectorCompat
import android.widget.TextView
import android.os.Bundle
import android.view.MotionEvent

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener, OnDoubleTapListener {
    private var mDetector: GestureDetectorCompat? = null
    private var mTextView: TextView? = null

    // Called when the activity is first created.
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextView = findViewById(R.id.textView)

        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = GestureDetectorCompat(this, this)

        // Set the gesture detector as the double tap listener
        mDetector!!.setOnDoubleTapListener(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector!!.onTouchEvent(event)
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event)
    }

    override fun onDown(event: MotionEvent): Boolean {
        val text = getString(R.string.lbl_on_down, event.toString())
        mTextView!!.text = text
        return true
    }

    override fun onFling(event1: MotionEvent?, event2: MotionEvent,
                         velocityX: Float, velocityY: Float): Boolean {
        val text = getString(R.string.lbl_on_fling,
            event1.toString(),
            event2.toString(),
            velocityX,
            velocityY)
        mTextView!!.text = text
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        val text = getString(R.string.lbl_on_long_press, event.toString())
        mTextView!!.text = text
    }

    override fun onScroll(event1: MotionEvent?, event2: MotionEvent,
                          distanceX: Float, distanceY: Float): Boolean {
        val text = getString(R.string.lbl_on_scroll,
            event1.toString(),
            event2.toString(),
            distanceX,
            distanceY)
        mTextView!!.text = text
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        val text = getString(R.string.lbl_on_show_press, event.toString())
        mTextView!!.text = text
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        val text = getString(R.string.lbl_on_single_tap_up, event.toString())
        mTextView!!.text = text
        return true
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
        val text = getString(R.string.lbl_on_double_tap, event.toString())
        mTextView!!.text = text
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        val text = getString(R.string.lbl_on_double_tap_event, event.toString())
        mTextView!!.text = text
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        val text = getString(R.string.lbl_on_single_tap_confirmed, event.toString())
        mTextView!!.text = text
        return true
    }
}