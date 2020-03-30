package mobiledev.unb.ca.labexam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mobiledev.unb.ca.labexam.model.GamesInfo;
import mobiledev.unb.ca.labexam.util.JsonUtils;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_FILE_NAME = "LabExamPrefs";

    private RecyclerView mRecyclerView;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the RecyclerView and configure it
        mRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // TODO: SharedPreferences
        //  Setup the instance of shared preferences you will be using
        initSharedPreferences();

        // TODO
        //  Create an instance of LoadDataTask and execute it
        LoadDataTask loadDataTask = new LoadDataTask();
        loadDataTask.execute();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<GamesInfo> mDataset;

        public MyAdapter(ArrayList<GamesInfo> myDataset) {
            mDataset = myDataset;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView mTextView;
            public CheckBox mCheckBox;

            public ViewHolder(LinearLayout v) {
                super(v);
                mTextView = v.findViewById(R.id.item_textview);
                mCheckBox = v.findViewById(R.id.item_checkbox);
            }
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // TODO
            //  Get the GamesInfo at index position in mDataSet
            //  (Hint: you might need to declare this variable as final.)
            //Log.w("Position ", String.valueOf(position));
            final GamesInfo gamesInfo = mDataset.get(position);

            // TODO
            //  Set the TextView in the ViewHolder (holder) to be the name for the city
            holder.mTextView.setText(gamesInfo.getTitle());

            // TODO
            //  Set the onClickListener for the TextView in the ViewHolder (holder) such
            //  that when it is clicked, it creates an explicit intent to launch DetailActivity
            //  Hint: You will need to put extra pieces of information in this intent.
            final String number = gamesInfo.getNumber();
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra(Constants.INTENT_KEY_NUMBER, number);
                    intent.putExtra(Constants.INTENT_KEY_YEAR, gamesInfo.getYear());
                    intent.putExtra(Constants.INTENT_KEY_DATES, gamesInfo.getDates());
                    intent.putExtra(Constants.INTENT_KEY_HOST_CITY, gamesInfo.getHostCity());
                    intent.putExtra(Constants.INTENT_KEY_WIKIPEDIA_LINK, gamesInfo.getWikipediaLink());

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Automatically set the check box to be selected
                        updateSharedPreferences(number, true);

                        startActivity(intent);
                    }
                }
            });

            // TODO: SharedPreference
            //  Set the CheckBox in the ViewHolder (holder) to be checked if the
            //  value stored in the shared preferences for the id for this GamesInfo is true, and to
            //  be not checked otherwise; if there is no value in the shared
            //  preferences for this id, then the checkbox should not be checked
            //  (i.e., assume a default value of false for ids that are not in
            //  the shared preferences).
            holder.mCheckBox.setChecked(readCheckedState(number));

            // Hints:
            // https://developer.android.com/reference/android/content/SharedPreferences.html#getBoolean(java.lang.String,%20boolean)
            // https://developer.android.com/reference/android/widget/CheckBox.html
            // https://developer.android.com/reference/android/widget/CompoundButton.html#setChecked(boolean)//

            holder.mCheckBox.setOnCheckedChangeListener(
                    // TODO: Fix the code so that the stored values do not change when the list is scrolled
                    new CompoundButton.OnCheckedChangeListener() {
                        // This method is called when a CheckBox is clicked, and its status
                        // changes from checked to not checked, or from not checked to checked.
                        // isChecked will be true if the CheckBox is now checked, and false if
                        // the CheckBox is now not checked.
                        public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                            updateSharedPreferences(number, isChecked);
                        }
                    }
            );
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    // TODO Complete the TODOs for LoadDataTask below
    //  Note: This class must use JsonUtils to load the data on a worker/background thread, and
    //  then set the data for the RecyclerView in MainActivity on the UI thread.
    private class LoadDataTask extends AsyncTask<Void, Void, ArrayList<GamesInfo>> {

        protected ArrayList<GamesInfo> doInBackground(Void... params) {
            // TODO
            //  Use JsonUtils to load the data from the JSON assets file and return the list of cities
            JsonUtils jsonUtils = new JsonUtils(getApplicationContext());
            return jsonUtils.getHostCities();
        }

        protected void onPostExecute(ArrayList<GamesInfo> result) {
            // TODO
            //  Use result to set the adapter for the RecyclerView in MainActivity
            MyAdapter myAdapter = new MyAdapter(result);
            mRecyclerView.setAdapter(myAdapter);
        }
    }

    private void initSharedPreferences() {
        //Log.w("Init ", "here");
        prefs = getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    private boolean readCheckedState(String number) {
        return prefs.getBoolean(number, false);
    }

    private void updateSharedPreferences(String number, boolean isChecked) {
        // TODO: SharedPreferences
        //  Get a SharedPreferences.Editor for SharedPreferences
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.html#edit()
        SharedPreferences.Editor editor = prefs.edit();

        // TODO: Shared Preferences
        //  Set the value stored in SharedPreferences for the id for this GamesInfo to be
        //  the value of isChecked
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#putBoolean(java.lang.String,%20boolean)
        editor.putBoolean(number, isChecked);

        // TODO: SharedPreferences
        //  Apply the changes from this editor
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#apply()
        editor.commit();

        /*Map<String, ?> allEntries = prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.w("map values", entry.getKey() + ": " + entry.getValue().toString());
        }*/
    }
}
