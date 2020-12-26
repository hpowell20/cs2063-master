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

    private int mOnCreateCount = 0;
    private int mOnStartCount = 0;
    private int mOnResumeCount = 0;
    private int mOnRestartCount = 0;

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
            mOnCreateCount = savedInstanceState.getInt(CREATE_VALUE);
            mOnStartCount = savedInstanceState.getInt(START_VALUE);
            mOnRestartCount = savedInstanceState.getInt(RESTART_VALUE);
            mOnResumeCount = savedInstanceState.getInt(RESUME_VALUE);
        }

        // TODO 8:
        // Increment mOnCreateCount
        mOnCreateCount++;

        updateCountsDisplay();
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart() called");
        super.onStart();

        mOnStartCount++;
        updateCountsDisplay();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume() called");
        super.onResume();

        mOnResumeCount++;
        updateCountsDisplay();
    }

    @Override
    public void onRestart() {
        Log.i(TAG, "onRestart() called");
        super.onRestart();

        mOnRestartCount++;
        updateCountsDisplay();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt(CREATE_VALUE, mOnCreateCount);
        savedInstanceState.putInt(START_VALUE, mOnStartCount);
        savedInstanceState.putInt(RESUME_VALUE, mOnResumeCount);
        savedInstanceState.putInt(RESTART_VALUE, mOnRestartCount);

        // Must always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void updateCountsDisplay() {

        // HINT: The YourOnCreateTextVariableName is replaced with the variable set in TODO 2
        textViewOnCreate.setText("onCreate() calls: " + mOnCreateCount);
        textViewOnStart.setText("onStart() calls: " + mOnStartCount);
        textViewOnResume.setText("onResume() calls: " + mOnResumeCount);
        textViewOnRestart.setText("onRestart() calls: " + mOnRestartCount);
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
