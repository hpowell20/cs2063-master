package mobiledev.unb.ca.lab2activitylifecycle;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityOne extends AppCompatActivity {
    // String for LogCat documentation
    private final static String TAG = "Lab 2 - Activity One";

    // HINT:
    // To track the number of times activity lifecycle methods
    // have been called for each respective Activity we will need
    // to increment a counter inside the method each time it is
    // called by the system. Below are the variables you will use
    // to increment during each lifecycle method call. We will be
    // tracking only these four lifecycle methods during this lab.
    private int onCreateCount = 0;
    private int onStartCount = 0;
    private int onResumeCount = 0;
    private int onRestartCount = 0;

    // TODO 2
    //  Declare four private TextView Objects. They will contain counts of the
    //  four lifecycle method calls indicated in the activity layout XML.
    //  NOTE:
    //  To contain references to Android SDK Object types they must be programmatically
    //  declared as instances of the object type, much like when
    //  declaring an int, double, or String.
    private TextView createTextView = null;
    private TextView startTextView = null;
    private TextView resumeTextView = null;
    private TextView restartTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        // HINT for 4:
        // This is how Android View layout resource references are obtained
        Button activityTwoButton = findViewById(R.id.btnLaunchActivityTwo);
        activityTwoButton.setOnClickListener(v -> {
            // HINT for 3:
            // Intents are a way to announce to the Android operating system that
            // your application intends to perform some request. These requests
            // can be directly calling some specified Activity, as we will be doing
            // here, or it can announce the intent of having some particular
            // activity type respond to its request time; for instance indicating
            // it will need access to an email application activity. We will
            // investigate intents further in a future lab!

            // TODO 3:
            //  Launch the ActivityTwo class using the intent below. For more information,
            //  consult the Android API documentation for starting activities:
            //  https://developer.android.com/reference/android/app/Activity#startActivity(android.content.Intent)
            Intent intent = new Intent(ActivityOne.this, ActivityTwo.class);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "Unable to start activity", e);
            }
        });

        // TODO 4
        //  Use the above Button resource reference example to capture TextView
        //  references for the four private TextView objects
        createTextView = findViewById(R.id.onCreate);
        startTextView = findViewById(R.id.onStart);
        restartTextView = findViewById(R.id.onRestart);
        resumeTextView = findViewById(R.id.onResume);

        // HINT for 6:
        // This checks whether or not a savedInstanceState currently exists
        // If it does, counter values will be loaded from its previous state
        if (savedInstanceState != null) {
            // TODO 6
            //  If a savedInstanceState Bundle exists then there have already
            //  been system calls made to activity lifecycle methods. We can
            //  use this Bundle to set current values.
            onCreateCount = savedInstanceState.getInt(Constants.CREATE_VALUE);
            onStartCount = savedInstanceState.getInt(Constants.START_VALUE);
            onResumeCount = savedInstanceState.getInt(Constants.RESUME_VALUE);
            onRestartCount = savedInstanceState.getInt(Constants.RESTART_VALUE);
        }

        // TODO 8
        //  Increment onCreateCount
        onCreateCount++;
        updateCountsDisplay();
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart() called");
        super.onStart();

        // TODO 8
        //  Increment onStartCount
        onStartCount++;
        updateCountsDisplay();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume() called");
        super.onResume();

        // TODO 8
        //  Increment onResumeCount
        onResumeCount++;
        updateCountsDisplay();
    }

    @Override
    public void onRestart() {
        Log.i(TAG, "onRestart() called");
        super.onRestart();

        // TODO 8
        //  Increment onRestartCount
        onRestartCount++;
        updateCountsDisplay();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // TODO 5
        //  Following the example below, save the current counters to a
        //  savedInstanceState Bundle so they can be refreshed when
        //  returning to this Activity.
        savedInstanceState.putInt(Constants.CREATE_VALUE, onCreateCount);
        savedInstanceState.putInt(Constants.START_VALUE, onStartCount);
        savedInstanceState.putInt(Constants.RESTART_VALUE, onRestartCount);
        savedInstanceState.putInt(Constants.RESUME_VALUE, onResumeCount);

        // Must always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void updateCountsDisplay() {
        // TODO 7
        //  Update all of the TextView resources to the correct counter
        //  HINT:
        //  Follow the provided example where yourOnCreateTextVariableName
        //  refers to any of the private TextView objects
        createTextView.setText(getString(R.string.onCreateMessage, onCreateCount));
        startTextView.setText(getString(R.string.onStartMessage, onStartCount));
        resumeTextView.setText(getString(R.string.onResumeMessage, onResumeCount));
        restartTextView.setText(getString(R.string.onRestartMessage, onRestartCount));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_one, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
