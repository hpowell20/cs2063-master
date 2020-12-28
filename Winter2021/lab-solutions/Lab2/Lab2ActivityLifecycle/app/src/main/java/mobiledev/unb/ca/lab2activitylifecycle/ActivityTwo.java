package mobiledev.unb.ca.lab2activitylifecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTwo extends AppCompatActivity {
    // Strings will serve as keys when saving state between activities
    private static final String CREATE_VALUE = "create";
    private static final String START_VALUE = "start";
    private static final String RESUME_VALUE = "resume";
    private static final String RESTART_VALUE = "restart";

    // String for LogCat documentation
    private final static String TAG = "Lab 2 - Activity Two";

    private int onCreateCount = 0;
    private int onStartCount = 0;
    private int onResumeCount = 0;
    private int onRestartCount = 0;

    private TextView textViewOnCreate;
    private TextView textViewOnStart;
    private TextView textViewOnResume;
    private TextView textViewOnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        textViewOnCreate = findViewById(R.id.onCreate);
        textViewOnStart = findViewById(R.id.onStart);
        textViewOnResume = findViewById(R.id.onResume);
        textViewOnRestart = findViewById(R.id.onRestart);

        if (savedInstanceState != null) {
            onCreateCount = savedInstanceState.getInt(CREATE_VALUE);
            onStartCount = savedInstanceState.getInt(START_VALUE);
            onRestartCount = savedInstanceState.getInt(RESTART_VALUE);
            onResumeCount = savedInstanceState.getInt(RESUME_VALUE);
        }

        onCreateCount++;
        updateCountsDisplay();
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart() called");
        super.onStart();

        onStartCount++;
        updateCountsDisplay();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume() called");
        super.onResume();

        onResumeCount++;
        updateCountsDisplay();
    }

    @Override
    public void onRestart() {
        Log.i(TAG, "onRestart() called");
        super.onRestart();

        onRestartCount++;
        updateCountsDisplay();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt(CREATE_VALUE, onCreateCount);
        savedInstanceState.putInt(START_VALUE, onStartCount);
        savedInstanceState.putInt(RESUME_VALUE, onResumeCount);
        savedInstanceState.putInt(RESTART_VALUE, onRestartCount);

        // Must always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void updateCountsDisplay() {

        // HINT: The YourOnCreateTextVariableName is replaced with the variable set in TODO 2
        textViewOnCreate.setText("onCreate() calls: " + onCreateCount);
        textViewOnStart.setText("onStart() calls: " + onStartCount);
        textViewOnResume.setText("onResume() calls: " + onResumeCount);
        textViewOnRestart.setText("onRestart() calls: " + onRestartCount);
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
