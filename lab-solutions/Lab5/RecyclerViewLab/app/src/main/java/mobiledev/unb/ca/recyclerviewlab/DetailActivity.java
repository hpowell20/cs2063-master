package mobiledev.unb.ca.recyclerviewlab;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // TODO 1
        //  Get the intent that started this activity, and get the extras from it
        //  corresponding to the title and description of the course
        Intent intent = getIntent();
        String courseName = intent.getStringExtra(Constants.INTENT_EXTRA_COURSE_NAME);
        String courseDescription = intent.getStringExtra(Constants.INTENT_EXTRA_COURSE_DESCRIPTION);

        // TODO 2
        //  Set the description TextView to be the course description
        TextView descriptionTextView = findViewById(R.id.description_textview);
        descriptionTextView.setText(courseDescription);

        // TODO 3
        //  Make the TextView scrollable
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());

        // TODO 4
        //  Set the title of the action bar to be the course title
        getSupportActionBar().setTitle(courseName);
    }
}
