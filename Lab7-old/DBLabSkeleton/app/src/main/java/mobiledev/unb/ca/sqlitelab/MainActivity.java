package mobiledev.unb.ca.sqlitelab;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private DBManager mDBManager;

    private Button mAddButton;
    private EditText mSearchEditText;
    private EditText mItemEditText;
    private EditText mNumberEditText;
    private TextView mResultsTextView;
    private ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate an instance of the DBManager class
        mDBManager = new DBManager(this);

        // Set the references for the views defined in the layout files
        mAddButton = findViewById(R.id.add_button);
        mSearchEditText = findViewById(R.id.search_edit_text);
        mItemEditText = findViewById(R.id.item_edit_text);
        mNumberEditText = findViewById(R.id.number_edit_text);
        mResultsTextView = findViewById(R.id.results_text_view);
        mListview = findViewById(R.id.listview);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Check if some text has been entered in both the item and number
                //  EditTexts. If so, create and execute an AddTask, passing its
                //  doInBackground method the text from these EditTexts. If not,
                //  display a toast indicating that the data entered was incomplete.
            }
        });

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO v is the search EditText. (EditText is a subclass of TextView.)
                    //  Get the text from this view. Create and execute a QueryTask passing
                    //  its doInBackground method this text.
                }

                return false;
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        mDBManager.close();
    }

    private class AddTask extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... params) {
            // TODO Get the item and number that were passed to this method
            //  as params. Add a corresponding row to the the database.

            return null;
        }

        protected void onPostExecute(Void result) {
            // TODO You will need to write a bit of extra code to get the
            //  UI to behave nicely, e.g., showing and hiding the keyboard
            //  at the right time, clearing text fields appropriately. Some
            //  of that code will likely go here, but you might also make
            //  changes elsewhere in the app. Exactly how you make the
            //  UI behave is up to you, but you should make reasonable
            //  choices.
        }
    }


    private class QueryTask extends AsyncTask<String, Void, Cursor> {
        protected Cursor doInBackground(String... params) {
            // TODO Get the query String from params. Query the database to
            //  retrieve all rows that have an item that matches this query,
            //  and return this Cursor object. Make sure that the results
            //  are sorted appropriately.

            // TODO Remove this return statement when you're done this part
            return null;
        }

        protected void onPostExecute(Cursor result) {
            // TODO Use a SimpleCursorAdapter to set the adapter for
            //  the ListView (mListview) to be the Cursor passed
            //  to onPostExecute.
            //  If there are no results, set the results TextView to indicate that there are no results.
            //  If there are results, set the results TextView to indicate that there are results.
            //  Again, you might need to write a bit of extra code here or elsewhere, to get the UI to behave nicely.
        }
    }
}
