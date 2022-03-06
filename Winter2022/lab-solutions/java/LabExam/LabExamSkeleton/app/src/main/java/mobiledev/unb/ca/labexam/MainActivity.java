package mobiledev.unb.ca.labexam;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mobiledev.unb.ca.labexam.helper.SharedPreferencesHelper;
import mobiledev.unb.ca.labexam.util.LoadDataTask;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_FILE_NAME = "LabExamPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the RecyclerView and configure it
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // TODO: SharedPreferences
        //  Setup the instance of shared preferences you will be using
        SharedPreferencesHelper prefsHelper = initSharedPreferences();

        // TODO
        //  Create an instance of LoadDataTask and execute it
        LoadDataTask loadDataTask = new LoadDataTask(this)
                .setRecyclerView(recyclerView)
                .setSharedPreferencesHelper(prefsHelper);
        loadDataTask.execute();
    }

    // Private Helper Methods
    private SharedPreferencesHelper initSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
        return new SharedPreferencesHelper(prefs);
    }
}
