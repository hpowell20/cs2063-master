package mobiledev.unb.ca.labexam

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import mobiledev.unb.ca.labexam.model.EventInfo
import android.content.SharedPreferences
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.CheckBox
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.CompoundButton

class MyAdapter(
    private val parentActivity: AppCompatActivity,
    private val dataset: List<EventInfo>,
    private val sharedPreferences: SharedPreferences
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(v: LinearLayout) : RecyclerView.ViewHolder(v) {
        var mTextView: TextView = v.findViewById(R.id.item_textview)
        var mCheckBox: CheckBox = v.findViewById(R.id.item_checkbox)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false) as LinearLayout
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO
        //  Get the item at index position in dataSet
        val gamesInfo = dataset[position]

        // TODO
        //  Set the TextView in the ViewHolder to be the eventTitle attribute
        holder.mTextView.text = gamesInfo.eventTitle

        // TODO
        //  Part 1 - Set the onClickListener for the TextView in the ViewHolder such
        //    that when it is clicked, it creates an explicit intent to launch DetailActivity
        //    with extra pieces of information in this intent.
        // TODO: SharedPreferences
        //  Part 2 - Update shared preferences to indicate this has been viewed
        //    HINT: The private function updateSharedPreferences can be used for this
        val number = gamesInfo.number
        holder.mTextView.setOnClickListener {
            val intent = Intent(parentActivity, DetailActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_NUMBER, number)
                putExtra(Constants.INTENT_KEY_YEAR, gamesInfo.year)
                putExtra(Constants.INTENT_KEY_DATES, gamesInfo.dates)
                putExtra(Constants.INTENT_KEY_HOST_CITY, gamesInfo.hostCity)
                putExtra(Constants.INTENT_KEY_WIKIPEDIA_LINK, gamesInfo.wikipediaLink)
            }

            try {
                // OPTIONAL - Update shared preferences to indicate this has been viewed
                // Automatically set the check box to be selected
                updateSharedPreferences(number, true)
                parentActivity.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "Unable to start activity", e)
            }
        }

        // TODO: SharedPreferences
        //  Set the CheckBox in the ViewHolder (holder) to be checked if the
        //  value stored in the shared preferences for the number for this EventInfo is true, and to
        //  be not checked otherwise; if there is no value in the shared
        //  preferences for this id, then the checkbox should not be checked
        //  (i.e., assume a default value of false for anything not in
        //  the shared preferences).
        holder.mCheckBox.isChecked = readCheckedState(number)

        // Hints:
        // https://developer.android.com/reference/android/content/SharedPreferences.html#getBoolean(java.lang.String,%20boolean)
        // https://developer.android.com/reference/android/widget/CheckBox.html
        // https://developer.android.com/reference/android/widget/CompoundButton.html#setChecked(boolean)//

        // This method is called when a CheckBox is clicked, and its status
        // changes from checked to not checked, or from not checked to checked.
        // isChecked will be true if the CheckBox is now checked, and false if
        // the CheckBox is now not checked.
        holder.mCheckBox.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            // TODO: SharedPreferences
            //   Save the CheckBox selected state
            //   HINT: The private updateSharedPreferences function can be used here
            updateSharedPreferences(number, isChecked)
        }
    }

    private fun updateSharedPreferences(prefsKey: String, prefsValue: Boolean): Boolean {
        // TODO: SharedPreferences
        //  Get a SharedPreferences.Editor for SharedPreferences
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.html#edit()
        val editor = sharedPreferences.edit()

        // TODO: Shared Preferences
        //  Set the value stored in SharedPreferences for the EventInfo number to be the value of isChecked
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#putBoolean(java.lang.String,%20boolean)
        editor.putBoolean(prefsKey, prefsValue)

        // TODO: SharedPreferences
        //  Apply the changes from this editor
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#commit()
        return editor.commit()
    }

    private fun readCheckedState(prefsKey: String?): Boolean {
        return sharedPreferences.getBoolean(prefsKey, false)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.mCheckBox.setOnCheckedChangeListener(null)
        super.onViewRecycled(holder)
    }

    companion object {
        private const val TAG = "MyAdapter"
    }
}