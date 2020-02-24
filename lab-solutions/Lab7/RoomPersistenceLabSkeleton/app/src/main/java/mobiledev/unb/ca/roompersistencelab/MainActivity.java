package mobiledev.unb.ca.roompersistencelab;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ItemViewModel mItemViewModel;
    private ItemsAdapter mItemsAdapter;
    private ListView mListView;

    private Button mAddButton;
    private EditText mSearchEditText;
    private EditText mItemEditText;
    private EditText mNumberEditText;
    private TextView mResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                //  EditTexts.
                //  If so call the addItem method using the the text from these EditTexts.
                //  If not display a toast indicating that the data entered was incomplete.
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

                // Create the record
                addItem(itemText, numberText);
            }
        });

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO v is the search EditText. (EditText is a subclass of TextView.)
                    //  Get the text from this view. Call the searchRecords method using the item name.
                    String text = v.getText().toString();
                    if (!TextUtils.isEmpty(text)) {
                        KeyboardUtils.hideKeyboard(MainActivity.this);
                        searchRecords(text);

                        return true;
                    }

                    // Show error message if no search field added
                    Toast.makeText(getApplicationContext(), getString(R.string.err_no_search_term_entered), Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        // Set the ViewModel
        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        // TODO for me: See if there is a way to use this syntax?
        /*mItemViewModel.getItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                if(items != null) {
                    mItemsAdapter = new ItemsAdapter(getApplicationContext(), items);
                    mListView.setAdapter(mItemsAdapter);
                }
                mItemsAdapter.notifyDataSetChanged();
            }
        });*/
    }

    private void addItem(String item, String num) {
        // TODO Make a call to the view model to create a record in the database table
        mItemViewModel.insert(item, Integer.parseInt(num));

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

    private void searchRecords(String item) {
        // TODO Make a call to the view model to search for records in the database that match the query item.
        //  Make sure that the results are sorted appropriately.
        List<Item> items = mItemViewModel.findItemsByName(item);

        // Uncomment if using LiveData; issue with this approach is that the search list does not clear out
        /*LiveData<List<Item>> items = mItemViewModel.findItemsByName(item);
        items.observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                if(items != null) {
                    int itemsCount = items.size();
                    if (itemsCount <= 0) {
                        mResultsTextView.setText(getString(R.string.msg_no_results_found));
                    } else {
                        String text = itemsCount == 1 ?
                                getString(R.string.msg_single_result_found, itemsCount) :
                                getString(R.string.msg_multiple_results_found, itemsCount);
                        mResultsTextView.setText(text);
                    }

                    //updateListView(itemsList);

                    //mItemsAdapter = new ItemsAdapter(getApplicationContext(), items);
                    //mListView.setAdapter(mItemsAdapter);
                }
                //mItemsAdapter.notifyDataSetChanged();
                updateListView(items);
            }
        });*/

        // TODO Update the results section.
        //  If there are no results, set the results TextView to indicate that there are no results.
        //  If there are results, set the results TextView to indicate that there are results.
        //  Again, you might need to write a bit of extra code here or elsewhere, to get the UI to behave nicely.
        int itemsCount = items.size();
        if (itemsCount <= 0) {
            mResultsTextView.setText(getString(R.string.msg_no_results_found));
        } else {
            String text = itemsCount == 1 ?
                    getString(R.string.msg_single_result_found, itemsCount) :
                    getString(R.string.msg_multiple_results_found, itemsCount);
            mResultsTextView.setText(text);
        }

        updateListView(items);
    }


    private void updateListView(List<Item> items) {
        if (null == items) {
            mResultsTextView.setText("");
            items = new ArrayList<Item>();
        }

        mSearchEditText.setText("");

        mItemsAdapter = new ItemsAdapter(getApplicationContext(), items);
        mListView.setAdapter(mItemsAdapter);
        mItemsAdapter.notifyDataSetChanged();
    }
}
