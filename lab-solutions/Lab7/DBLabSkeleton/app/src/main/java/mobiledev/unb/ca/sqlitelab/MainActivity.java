package mobiledev.unb.ca.sqlitelab;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // Cursor query attributes
    private final String[] FROM = {DatabaseHelper.ITEM, DatabaseHelper.NUM};
    private final int[] TO = {R.id.item_textview, R.id.num_textview};

    private DBManager mDBManager;

    private Button mAddButton;
    private EditText mSearchEditText;
    private EditText mItemEditText;
    private EditText mNumberEditText;
    private TextView mResultsTextView;
    private ListView mListView;

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
        mListView = findViewById(R.id.listview);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Check if some text has been entered in both the item and number
                //  EditTexts. If so, create and execute an AddTask, passing its
                //  doInBackground method the text from these EditTexts. If not,
                //  display a toast indicating that the data entered was incomplete.
               Context context = getApplicationContext();
               String itemText =  mItemEditText.getText().toString();
               if (TextUtils.isEmpty(itemText)) {
                   Toast.makeText(context, getString(R.string.err_no_item_value_entered), Toast.LENGTH_SHORT).show();
                   return;
               }

               String numberText = mNumberEditText.getText().toString();
               if (TextUtils.isEmpty(numberText)) {
                   Toast.makeText(context, getString(R.string.err_no_number_value_entered), Toast.LENGTH_SHORT).show();
                   return;
               }

               // Hide the keyboard
               KeyboardUtils.hideKeyboard(MainActivity.this);

               // Clear the search results
               updateListView(null);
               
               // Create the new item
               AddTask addTask = new AddTask();
               addTask.execute(itemText, numberText);
            }
        });

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO v is the search EditText. (EditText is a subclass of TextView.)
                    //  Get the text from this view. Create and execute a QueryTask passing
                    //  its doInBackground method this text.
                    String text = v.getText().toString();
                    if (!TextUtils.isEmpty(text)) {
                        KeyboardUtils.hideKeyboard(MainActivity.this);
                        QueryTask queryTask = new QueryTask();
                        queryTask.execute(text);

                        return true;
                    }

                    // Show error message
                    Toast.makeText(getApplicationContext(), getString(R.string.err_no_search_term_entered), Toast.LENGTH_SHORT).show();
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
            try {
                String item = params[0];
                int num = Integer.parseInt(params[1]);
                mDBManager.insertRecord(item, num);
            } catch (NullPointerException npe) {
                Toast.makeText(getApplicationContext(), getString(R.string.err_not_numeric_value), Toast.LENGTH_SHORT).show();
            }

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
            mItemEditText.setText("");
            mNumberEditText.setText("");
            Toast.makeText(getApplicationContext(), getString(R.string.msg_record_added), Toast.LENGTH_SHORT).show();
        }
    }


    private class QueryTask extends AsyncTask<String, Void, Cursor> {
        protected Cursor doInBackground(String... params) {
            // TODO Get the query String from params. Query the database to
            //  retrieve all rows that have an item that matches this query,
            //  and return this Cursor object. Make sure that the results
            //  are sorted appropriately.
            return mDBManager.listAllRecordsByItem(params[0]);
        }

        protected void onPostExecute(Cursor result) {
            // TODO Use a SimpleCursorAdapter to set the adapter for
            //  the ListView (mListview) to be the Cursor passed
            //  to onPostExecute. If there are no results, set the
            //  results TextView to indicate that there are no results.
            //  Again, you might need to write a bit of extra code here,
            //  or elsewhere, to get the UI to behave nicely.
            int cursorCount = result.getCount();
            if (cursorCount <= 0) {
                mResultsTextView.setText(getString(R.string.msg_no_results_found));
            } else {
                String text = cursorCount == 1 ?
                        getString(R.string.msg_single_result_found, cursorCount) :
                        getString(R.string.msg_multiple_results_found, cursorCount);
                mResultsTextView.setText(text);
            }

            updateListView(result);
        }
    }

    private void updateListView(Cursor cursor) {
        if (null == cursor) {
            mResultsTextView.setText("");
        }

        mSearchEditText.setText("");

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.list_layout,
                cursor,
                FROM,
                TO,
                0);

        adapter.notifyDataSetChanged();
        mListView.setAdapter(adapter);
    }
}
