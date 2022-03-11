package mobiledev.unb.ca.labexam

import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import mobiledev.unb.ca.labexam.helper.SharedPreferencesEntry
import mobiledev.unb.ca.labexam.helper.SharedPreferencesHelper
import mobiledev.unb.ca.labexam.model.GamesInfo


class MyAdapter(
    private val parentActivity: AppCompatActivity,
    private val dataset: List<GamesInfo>,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(v: LinearLayout) : RecyclerView.ViewHolder(v) {
        var mTextView: TextView = v.findViewById(R.id.item_textview)
        var mCheckBox: CheckBox = v.findViewById(R.id.item_checkbox)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false) as LinearLayout
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO
        //  Get the item at index position in mDataSet
        val gamesInfo = dataset[position]

        // TODO
        //  Set the TextView in the ViewHolder to be the title attribute
        holder.mTextView.text = gamesInfo.title

        // TODO
        //  Set the onClickListener for the TextView in the ViewHolder such
        //  that when it is clicked, it creates an explicit intent to launch DetailActivity
        //  with extra pieces of information in this intent.
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
                // Automatically set the check box to be selected
                sharedPreferencesHelper.saveCheckedState(SharedPreferencesEntry(number, true))
                parentActivity.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "Unable to start activity", e)
            }
        }

        // TODO: SharedPreference
        //  Set the CheckBox in the ViewHolder (holder) to be checked if the
        //  value stored in the shared preferences for the number for this GamesInfo is true, and to
        //  be not checked otherwise; if there is no value in the shared
        //  preferences for this id, then the checkbox should not be checked
        //  (i.e., assume a default value of false for anything not in
        //  the shared preferences).
        holder.mCheckBox.isChecked = sharedPreferencesHelper.readCheckedState(number).checked

        // Hints:
        // https://developer.android.com/reference/android/content/SharedPreferences.html#getBoolean(java.lang.String,%20boolean)
        // https://developer.android.com/reference/android/widget/CheckBox.html
        // https://developer.android.com/reference/android/widget/CompoundButton.html#setChecked(boolean)//

        // This method is called when a CheckBox is clicked, and its status
        // changes from checked to not checked, or from not checked to checked.
        // isChecked will be true if the CheckBox is now checked, and false if
        // the CheckBox is now not checked.
        holder.mCheckBox.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            // TODO: SharedPreference
            //  Using the SharedPreferencesHelper update the saved state
            sharedPreferencesHelper.saveCheckedState(SharedPreferencesEntry(number, isChecked))
        }
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