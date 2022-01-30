package mobiledev.unb.ca.lab2activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ActivityTwo extends AppCompatActivity {
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
            onCreateCount = savedInstanceState.getInt(Constants.CREATE_VALUE);
            onStartCount = savedInstanceState.getInt(Constants.START_VALUE);
            onResumeCount = savedInstanceState.getInt(Constants.RESUME_VALUE);
            onRestartCount = savedInstanceState.getInt(Constants.RESTART_VALUE);
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
        savedInstanceState.putInt(Constants.CREATE_VALUE, onCreateCount);
        savedInstanceState.putInt(Constants.START_VALUE, onStartCount);
        savedInstanceState.putInt(Constants.RESTART_VALUE, onRestartCount);
        savedInstanceState.putInt(Constants.RESUME_VALUE, onResumeCount);

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