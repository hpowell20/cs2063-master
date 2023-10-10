package mobiledev.unb.ca.threadinglab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO 1
        //  Get the intent that started this activity, and get the extras from it
        //  corresponding to the title and description of the course
        val currIntent = intent
        val courseTitle = currIntent.getStringExtra(Constants.INTENT_EXTRA_COURSE_TITLE)
        val courseDesc = currIntent.getStringExtra(Constants.INTENT_EXTRA_COURSE_DESCRIPTION)

        // TODO 2
        //  Set the description TextView to be the course description
        val descTextView = findViewById<TextView>(R.id.description_textview)
        descTextView.text = courseDesc

        // TODO 3
        //  Make the TextView scrollable
        //  HINT: Look at the movementMethod attribute for descTextView
        descTextView.movementMethod = ScrollingMovementMethod()

        // TODO 4
        //  Set the title of the action bar to be the course title
        supportActionBar?.title = courseTitle
    }
}