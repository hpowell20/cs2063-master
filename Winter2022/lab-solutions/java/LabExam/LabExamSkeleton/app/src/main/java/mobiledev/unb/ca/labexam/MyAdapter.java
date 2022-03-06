package mobiledev.unb.ca.labexam;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mobiledev.unb.ca.labexam.helper.SharedPreferencesEntry;
import mobiledev.unb.ca.labexam.helper.SharedPreferencesHelper;
import mobiledev.unb.ca.labexam.model.GamesInfo;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final static String TAG = "MyAdapter";

    private final List<GamesInfo> dataset;
    private final AppCompatActivity parentActivity;
    private final SharedPreferencesHelper prefsHelper;

    public MyAdapter(AppCompatActivity parentActivity, List<GamesInfo> myDataset, SharedPreferencesHelper prefsHelper) {
        this.parentActivity = parentActivity;
        dataset = myDataset;
        this.prefsHelper = prefsHelper;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public CheckBox mCheckBox;

        public ViewHolder(LinearLayout v) {
            super(v);
            mTextView = v.findViewById(R.id.item_textview);
            mCheckBox = v.findViewById(R.id.item_checkbox);
        }
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        // TODO
        //  Get the item at index position in mDataSet
        final GamesInfo gamesInfo = dataset.get(position);

        // TODO
        //  Set the TextView in the ViewHolder to be the title attribute
        holder.mTextView.setText(gamesInfo.getTitle());

        // TODO
        //  Set the onClickListener for the TextView in the ViewHolder such
        //  that when it is clicked, it creates an explicit intent to launch DetailActivity
        //  with extra pieces of information in this intent.
        final String number = gamesInfo.getNumber();
        holder.mTextView.setOnClickListener(v -> {
            Intent intent = new Intent(parentActivity, DetailActivity.class);
            intent.putExtra(Constants.INTENT_KEY_NUMBER, number);
            intent.putExtra(Constants.INTENT_KEY_YEAR, gamesInfo.getYear());
            intent.putExtra(Constants.INTENT_KEY_DATES, gamesInfo.getDates());
            intent.putExtra(Constants.INTENT_KEY_HOST_CITY, gamesInfo.getHostCity());
            intent.putExtra(Constants.INTENT_KEY_WIKIPEDIA_LINK, gamesInfo.getWikipediaLink());

            try {
                // Automatically set the check box to be selected
                prefsHelper.saveCheckedState(new SharedPreferencesEntry(number, true));

                parentActivity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "Unable to start activity", e);
            }
        });

        // TODO: SharedPreference
        //  Set the CheckBox in the ViewHolder (holder) to be checked if the
        //  value stored in the shared preferences for the number for this GamesInfo is true, and to
        //  be not checked otherwise; if there is no value in the shared
        //  preferences for this id, then the checkbox should not be checked
        //  (i.e., assume a default value of false for anything not in
        //  the shared preferences).
        holder.mCheckBox.setChecked(prefsHelper.readCheckedState(number).getChecked());

        // Hints:
        // https://developer.android.com/reference/android/content/SharedPreferences.html#getBoolean(java.lang.String,%20boolean)
        // https://developer.android.com/reference/android/widget/CheckBox.html
        // https://developer.android.com/reference/android/widget/CompoundButton.html#setChecked(boolean)//

        // This method is called when a CheckBox is clicked, and its status
        // changes from checked to not checked, or from not checked to checked.
        // isChecked will be true if the CheckBox is now checked, and false if
        // the CheckBox is now not checked.
        holder.mCheckBox.setOnCheckedChangeListener(
                (v, isChecked) -> {
                    // TODO: SharedPreference
                    //  Using the SharedPreferencesHelper update the saved state
                    prefsHelper.saveCheckedState(new SharedPreferencesEntry(number, isChecked));
                }
        );
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public void onViewRecycled(@NonNull MyAdapter.ViewHolder holder) {
        holder.mCheckBox.setOnCheckedChangeListener(null);
        super.onViewRecycled(holder);
    }
}
