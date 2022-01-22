package course.examples.ui.button

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button

class ButtonActivity : Activity() {
    private var mButton: Button? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        // Get a reference to the Press Me Button
        mButton = findViewById(R.id.button)

        // Reset Button Text if restarting
        if (null != savedInstanceState) {
            mButton?.text = getString(R.string.got_pressed_string, mCount)
        }

        // Set an OnClickListener on this Button
        // Called each time the user clicks the Button
        mButton?.setOnClickListener { _: View? ->
            // Maintain a count of user presses
            // Display count as text on the Button
            mButton?.text = getString(R.string.got_pressed_string, ++mCount)
        }
    }

    companion object {
        private var mCount = 0
    }
}