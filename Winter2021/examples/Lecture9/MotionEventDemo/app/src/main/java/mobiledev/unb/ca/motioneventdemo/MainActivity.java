package mobiledev.unb.ca.motioneventdemo;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textview);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {

        // Extract the action from the event
        final int action = motionEvent.getActionMasked();

        switch(action) {
          case (MotionEvent.ACTION_DOWN) :
            mTextView.setText("Action was DOWN");
            return true;
          case (MotionEvent.ACTION_MOVE) :
            mTextView.setText("Action was MOVE");
            return true;
          case (MotionEvent.ACTION_UP) :
            mTextView.setText("Action was UP");
            return true;
          case (MotionEvent.ACTION_CANCEL) :
            mTextView.setText("Action was CANCEL");
            return true;
          case (MotionEvent.ACTION_OUTSIDE) :
            mTextView.setText("Movement occurred outside bounds " +
                              "of current screen element");
            return true;
          default :
            return super.onTouchEvent(motionEvent);
        }
    }
}
