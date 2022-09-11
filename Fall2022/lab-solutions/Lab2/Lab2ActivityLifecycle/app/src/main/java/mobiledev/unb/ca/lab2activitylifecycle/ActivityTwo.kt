package mobiledev.unb.ca.lab2activitylifecycle

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActivityTwo : AppCompatActivity() {
    private var createTextView: TextView? = null
    private var startTextView: TextView? = null
    private var resumeTextView: TextView? = null
    private var restartTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate() called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        createTextView = findViewById(R.id.onCreate)
        startTextView = findViewById(R.id.onStart)
        resumeTextView = findViewById(R.id.onResume)
        restartTextView = findViewById(R.id.onRestart)

        if (savedInstanceState != null) {
            savedInstanceState.getInt(Constants.CREATE_VALUE).also { onCreateCount = it }
            savedInstanceState.getInt(Constants.START_VALUE).also { onStartCount = it }
            savedInstanceState.getInt(Constants.RESUME_VALUE).also { onResumeCount = it }
            savedInstanceState.getInt(Constants.RESTART_VALUE).also { onRestartCount = it }
        }

        //  Increment onCreateCount
        onCreateCount++

        updateCountsDisplay()
    }

    public override fun onStart() {
        Log.i(TAG, "onStart() called")
        super.onStart()

        //  Increment onStartCount
        onStartCount++

        updateCountsDisplay()
    }

    public override fun onResume() {
        Log.i(TAG, "onResume() called")
        super.onResume()

        //  Increment onResumeCount
        onResumeCount++

        updateCountsDisplay()
    }

    public override fun onRestart() {
        Log.i(TAG, "onRestart() called")
        super.onRestart()

        //  Increment onRestartCount
        onRestartCount++

        updateCountsDisplay()
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putInt(Constants.CREATE_VALUE, onCreateCount)
        savedInstanceState.putInt(Constants.START_VALUE, onStartCount)
        savedInstanceState.putInt(Constants.RESUME_VALUE, onResumeCount)
        savedInstanceState.putInt(Constants.RESTART_VALUE, onRestartCount)

        // Must always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun updateCountsDisplay() {
        createTextView!!.text = getString(R.string.onCreateMessage, onCreateCount)
        startTextView!!.text = getString(R.string.onStartMessage, onStartCount)
        resumeTextView!!.text = getString(R.string.onResumeMessage, onResumeCount)
        restartTextView!!.text = getString(R.string.onRestartMessage, onRestartCount)
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
        private const val TAG = "Lab 2 - Activity Two"

        // Values for tracking the counts
        private var onCreateCount = 0
        private var onStartCount = 0
        private var onResumeCount = 0
        private var onRestartCount = 0
    }
}