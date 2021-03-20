package mobiledev.unb.ca.simplegestureviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GestureDetectorCompat;

/**
 * Custom text view which will allow for the
 */
public class CustomTextView extends AppCompatTextView {

    private GestureDetectorCompat mDetector;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDetectorObject(GestureDetectorCompat mDetector) {
        this.mDetector = mDetector;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        return mDetector.onTouchEvent(event);
    }

    // Because we call this from onTouchEvent, this code will be executed for both
    // normal touch events and for when the system calls this using Accessibility
    @Override
    public boolean performClick() {
        super.performClick();
        showToastMessage();
        return true;
    }

    private void showToastMessage() {
        Toast.makeText(getContext(), "Action taken on performClick", Toast.LENGTH_LONG).show();
    }
}