package mobiledev.unb.ca.lab2activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ActivityTwo extends AppCompatActivity {
    // Strings will serve as keys when saving state between activities
    private static final String CREATE_VALUE = "create";
    private static final String START_VALUE = "start";
    private static final String RESUME_VALUE = "resume";
    private static final String RESTART_VALUE = "restart";

    // String for LogCat documentation
    private final static String TAG = "Lab 2 - Activity Two";

    // Values for tracking the number of state changes
    private int onCreateCount = 0;
    private int onStartCount = 0;
    private int onResumeCount = 0;
    private int onRestartCount = 0;

    private TextView createTextView = null;
    private TextView startTextView = null;
    private TextView resumeTextView = null;
    private TextView restartTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        createTextView = findViewById(R.id.onCreate);
        startTextView = findViewById(R.id.onStart);
        restartTextView = findViewById(R.id.onRestart);
        resumeTextView = findViewById(R.id.onResume);

        if (savedInstanceState != null) {
            onCreateCount = savedInstanceState.getInt(CREATE_VALUE);
            onStartCount = savedInstanceState.getInt(START_VALUE);
            onResumeCount = savedInstanceState.getInt(RESUME_VALUE);
            onRestartCount = savedInstanceState.getInt(RESTART_VALUE);
        }

        //  Increment onCreateCount
        onCreateCount++;
        updateCountsDisplay();
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart() called");
        super.onStart();

        // Increment onStartCount
        onStartCount++;
        updateCountsDisplay();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume() called");
        super.onResume();

        // Increment onResumeCount
        onResumeCount++;
        updateCountsDisplay();
    }

    @Override
    public void onRestart() {
        Log.i(TAG, "onRestart() called");
        super.onRestart();

        // Increment onRestartCount
        onRestartCount++;
        updateCountsDisplay();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(CREATE_VALUE, onCreateCount);
        savedInstanceState.putInt(START_VALUE, onStartCount);
        savedInstanceState.putInt(RESTART_VALUE, onRestartCount);
        savedInstanceState.putInt(RESUME_VALUE, onResumeCount);

        // Must always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void updateCountsDisplay() {
        createTextView.setText(getString(R.string.onCreateMessage, onCreateCount));
        startTextView.setText(getString(R.string.onStartMessage, onStartCount));
        resumeTextView.setText(getString(R.string.onResumeMessage, onResumeCount));
        restartTextView.setText(getString(R.string.onRestartMessage, onRestartCount));
    }
}