package mobiledev.unb.ca.simplegesturedemo;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

public class MainActivity extends AppCompatActivity {

    private GestureDetectorCompat mDetector;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textview);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return this.mDetector.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            mTextView.setText(R.string.lbl_on_down);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            String text = getString(R.string.lbl_on_fling, velocityX, velocityY);

            mTextView.setText(text);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            mTextView.setText(R.string.lbl_on_single_tap_confirmed);
            return true;
        }
    }
}

