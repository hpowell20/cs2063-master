package mobiledev.unb.ca.roompersistencelab

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import mobiledev.unb.ca.roompersistencelab.ui.ItemViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mItemViewModel: ItemViewModel
    private lateinit var mListView: ListView

    private var mSearchEditText: EditText? = null
    private var mItemEditText: EditText? = null
    private var mNumberEditText: EditText? = null
    private var mResultsTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the references for the views defined in the layout files
        mItemEditText = findViewById(R.id.item_edit_text)
        mNumberEditText = findViewById(R.id.number_edit_text)
        mResultsTextView = findViewById(R.id.results_text_view)
        mListView = findViewById(R.id.listview)

        val mAddButton = findViewById<Button>(R.id.add_button)
        mAddButton.setOnClickListener {
            // TODO 1
            //  Check if some text has been entered in both the item and number EditTexts.
            //  If not display a toast indicating that the data entered was incomplete.
            //  HINT:
            //    Have a look at the TextUtils class (https://developer.android.com/reference/android/text/TextUtils)

            // TODO 2
            //  Call the addItem method using the the text from these EditTexts.
        }

        mSearchEditText = findViewById(R.id.search_edit_text)
        mSearchEditText?.setOnEditorActionListener { v: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // TODO
                //  v is the search EditText. (EditText is a subclass of TextView.)
                //  Get the text from this view.
                //  Call the searchRecords method using the item name.
            }
            false
        }

        // Set the ViewModel
        mItemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]
    }

    private fun addItem(item: String, num: String) {
        // TODO 1
        //  Make a call to the view model to create a record in the database table

        // TODO 2
        //  You will need to write a bit of extra code to get the
        //  UI to behave nicely, e.g., showing and hiding the keyboard
        //  at the right time, clearing text fields appropriately.
        //  Some of that code will likely go here, but you might also make
        //  changes elsewhere in the app. Exactly how you make the
        //  UI behave is up to you, but you should make reasonable
        //  choices.
        //  HINT:
        //    There is a utility object called KeyboardUtils which may be helpful here
    }

    private fun searchRecords(item: String) {
        // TODO 1
        //  Make a call to the view model to search for records in the database that match the query item.
        //  Make sure that the results are sorted appropriately

        // TODO 2
        //  Update the results section.
        //  If there are no results, set the results TextView to indicate that there are no results.
        //  If there are results, set the results TextView to indicate that there are results.
        //  Again, you might need to write a bit of extra code here or elsewhere, to get the UI to behave nicely.
        //  HINT:
        //    When displaying the results you will need to set the ItemsAdapter object and the
        //    adapter attribute of mListView
    }
}