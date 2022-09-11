package mobiledev.unb.ca.lab2activitylifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActivityOne : AppCompatActivity() {
    // NOTE:
    // To track the number of times activity lifecycle methods
    // have been called for each respective Activity we will need
    // to increment a counter inside the method each time it is
    // called by the system. Below are the variables you will use
    // to increment during each lifecycle method call. We will be
    // tracking only these four lifecycle methods during this lab.
    private var onCreateCount = 0
    private var onStartCount = 0
    private var onResumeCount = 0
    private var onRestartCount = 0

    // TODO 2
    //  Declare four private TextView Objects and initialize them to null.
    //  They will contain counts of the four lifecycle method calls indicated
    //  in the activity layout XML (create, start, resume, and restart).
    //  NOTE:
    //   To contain references to Android SDK Object types they must be programmatically
    //   declared as instances of the object type, much like when
    //   declaring an int, double, or String.
    //  HINT:
    //   Follow the provided example where createTextView
    //   refers to any of the private TextView objects
    private var createTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate() called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)

        val activityTwoButton = findViewById<Button>(R.id.btnLaunchActivityTwo)
        activityTwoButton.setOnClickListener {
            // TODO 3:
            //  Launch the ActivityTwo class using the intent below. For more information,
            //  consult the Android API documentation for starting activities:
            //  https://developer.android.com/reference/android/app/Activity#startActivity(android.content.Intent)
            //  NOTE:
            //   Intents are a way to announce to the Android operating system that
            //   your application intends to perform some request. These requests
            //   can be directly calling some specified Activity, as we will be doing
            //   here, or it can announce the intent of having some particular
            //   activity type respond to its request time; for instance indicating
            //   it will need access to an email application activity. We will
            //   investigate intents further in a future lab!
            val intent = Intent(this@ActivityOne, ActivityTwo::class.java)
        }

        // TODO 4
        //  Use the above Button resource reference example to capture TextView
        //  references for the four private TextView objects
        //  NOTE:
        //   This is how Android View layout resource references are obtained
        //  HINT:
        //  Follow the provided example for createTextView for the other
        //  three private TextView objects
        createTextView = findViewById(R.id.onCreate)

        // TODO 6
        //  If a savedInstanceState Bundle exists then there have already
        //  been system calls made to activity lifecycle methods. We can
        //  use this Bundle to set current values.
        //  NOTE: The expression savedInstanceState? is a safe access expression
        //  HINT:
        //   This checks whether or not a savedInstanceState currently exists
        //   If it does, counter values will be loaded from its previous state
        if (savedInstanceState != null) {
            onCreateCount = savedInstanceState.getInt(CREATE_VALUE)
        }

        // TODO 8
        //  Increment onCreateCount

        updateCountsDisplay()
    }

    public override fun onStart() {
        Log.i(TAG, "onStart() called")
        super.onStart()

        // TODO 8
        //  Increment onStartCount

        updateCountsDisplay()
    }

    public override fun onResume() {
        Log.i(TAG, "onResume() called")
        super.onResume()

        // TODO 8
        //  Increment onResumeCount

        updateCountsDisplay()
    }

    public override fun onRestart() {
        Log.i(TAG, "onRestart() called")
        super.onRestart()

        // TODO 8
        //  Increment onRestartCount

        updateCountsDisplay()
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // TODO 5
        //  Following the example below, save the current counters to a
        //  savedInstanceState Bundle so they can be refreshed when
        //  returning to this Activity.
        savedInstanceState.putInt(CREATE_VALUE, onCreateCount)

        // Must always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun updateCountsDisplay() {
        // TODO 7
        //  Update all of the TextView resources to the correct counter
        //  HINT:
        //   Follow the provided example where createTextView
        //   refers to any of the private TextView objects
        createTextView!!.text = getString(R.string.onCreateMessage, onCreateCount)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_activity_one, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    companion object {
        // String for LogCat documentation
        private const val TAG = "Lab 2 - Activity One"

        // Strings will serve as keys when saving state between activities
        private const val CREATE_VALUE = "create"
        private const val START_VALUE = "start"
        private const val RESUME_VALUE = "resume"
        private const val RESTART_VALUE = "restart"
    }
}