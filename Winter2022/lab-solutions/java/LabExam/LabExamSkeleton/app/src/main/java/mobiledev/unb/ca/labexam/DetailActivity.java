package mobiledev.unb.ca.labexam;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // TODO
        //  Get the intent that started this activity along with the extras added to it
        final Intent intent = getIntent();

        // TODO
        //  Set the details for the number, year, and dates text views
        TextView numberTextView = findViewById(R.id.number_textview);
        numberTextView.setText(intent.getStringExtra(Constants.INTENT_KEY_NUMBER));

        TextView yearTextView = findViewById(R.id.year_textview);
        yearTextView.setText(intent.getStringExtra(Constants.INTENT_KEY_YEAR));

        TextView datesTextView = findViewById(R.id.dates_textview);
        datesTextView.setText(intent.getStringExtra(Constants.INTENT_KEY_DATES));

        // TODO
        //  Set an onClickListener such that when this button is clicked, an implicit intent is started
        //  to open the wikipedia URL in a web browser. Be sure to check that there is
        //  an application installed that can handle this intent before starting it.
        //  If the intent can't be started, show a toast indicating this.
        // Hints:
        // https://developer.android.com/reference/android/content/Intent.html#resolveActivity(android.content.pm.PackageManager)
        // https://developer.android.com/guide/components/intents-common.html#Browser
        // https://developer.android.com/reference/android/net/Uri.html#parse(java.lang.String)
        Button wikiButton = findViewById(R.id.wiki_button);
        wikiButton.setOnClickListener(v -> {
            Uri uri = Uri.parse(intent.getStringExtra(Constants.INTENT_KEY_WIKIPEDIA_LINK));
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);

            // Ensure that there is an web application to handle the intent
            try {
                startActivity(browserIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.err_no_web_app_installed),
                        Toast.LENGTH_SHORT).show();
            }
        });

        // TODO
        //  Set the title of the action bar to be the host city name
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(intent.getStringExtra(Constants.INTENT_KEY_HOST_CITY));
        }
    }
}
