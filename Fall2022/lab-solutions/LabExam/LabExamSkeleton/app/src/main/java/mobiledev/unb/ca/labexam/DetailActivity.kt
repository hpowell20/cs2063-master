package mobiledev.unb.ca.labexam

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO
        //  Get the intent that started this activity along with the extras added to it
        val currIntent = intent

        // TODO
        //  Set the details for the number, year, and dates text views
        val numberTextView = findViewById<TextView>(R.id.number_textview)
        numberTextView.text = currIntent.getStringExtra(Constants.INTENT_KEY_NUMBER)

        val yearTextView = findViewById<TextView>(R.id.year_textview)
        yearTextView.text = currIntent.getStringExtra(Constants.INTENT_KEY_YEAR)

        val datesTextView = findViewById<TextView>(R.id.dates_textview)
        datesTextView.text = currIntent.getStringExtra(Constants.INTENT_KEY_DATES)

        // TODO
        //  Set an onClickListener such that when this button is clicked, an implicit intent is started
        //  to open the wikipedia URL in a web browser. Be sure to check that there is
        //  an application installed that can handle this intent before starting it.
        //  If the intent can't be started, show a toast indicating this.
        // Hints:
        // https://developer.android.com/reference/android/content/Intent.html#resolveActivity(android.content.pm.PackageManager)
        // https://developer.android.com/guide/components/intents-common.html#Browser
        // https://developer.android.com/reference/android/net/Uri.html#parse(java.lang.String)
        val wikiButton: Button = findViewById(R.id.wiki_button)
        wikiButton.setOnClickListener {
            val uri: Uri =
                Uri.parse(currIntent.getStringExtra(Constants.INTENT_KEY_WIKIPEDIA_LINK))
            val browserIntent = Intent(Intent.ACTION_VIEW, uri)

            // Ensure that there is an web application to handle the intent
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(applicationContext,
                    getString(R.string.err_no_web_app_installed),
                    Toast.LENGTH_SHORT).show()
            }
        }

        // TODO
        //  Set the title of the action bar to be the host city name
        val actionBar: ActionBar? = supportActionBar
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = intent.getStringExtra(Constants.INTENT_KEY_HOST_CITY)
        }
    }
}