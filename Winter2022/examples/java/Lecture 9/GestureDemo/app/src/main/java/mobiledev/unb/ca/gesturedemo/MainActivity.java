package mobiledev.unb.ca.gesturedemo;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

public class MainActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private GestureDetectorCompat mDetector;
    private TextView mTextView;

    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);
        
        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this,this);
        
        // Set the gesture detector as the double tap listener
        mDetector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        String text = getString(R.string.lbl_on_down, event.toString());
        mTextView.setText(text);
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        String text = getString(R.string.lbl_on_fling,
                event1.toString(),
                event2.toString(),
                velocityX,
                velocityY);
        mTextView.setText(text);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        String text = getString(R.string.lbl_on_long_press, event.toString());
        mTextView.setText(text);
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        String text = getString(R.string.lbl_on_scroll,
                event1.toString(),
                event2.toString(),
                distanceX,
                distanceY);
        mTextView.setText(text);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        String text = getString(R.string.lbl_on_show_press, event.toString());
        mTextView.setText(text);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        String text = getString(R.string.lbl_on_single_tap_up, event.toString());
        mTextView.setText(text);
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        String text = getString(R.string.lbl_on_double_tap, event.toString());
        mTextView.setText(text);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        String text = getString(R.string.lbl_on_double_tap_event, event.toString());
        mTextView.setText(text);
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        String text = getString(R.string.lbl_on_single_tap_confirmed, event.toString());
        mTextView.setText(text);
        return true;
    }
}
