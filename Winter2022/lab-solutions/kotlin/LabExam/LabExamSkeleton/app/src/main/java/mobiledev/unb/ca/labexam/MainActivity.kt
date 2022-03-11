package mobiledev.unb.ca.labexam

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mobiledev.unb.ca.labexam.helper.SharedPreferencesHelper
import mobiledev.unb.ca.labexam.util.LoadDataTask





class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get a reference to the RecyclerView and configure it
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // TODO: SharedPreferences
        //  Setup the instance of shared preferences you will be using
        val prefsHelper = initSharedPreferences()

        // TODO
        //  Create an instance of LoadDataTask; pass in the recycler view
        //  and shared preferences then execute it
        val loadDataTask = LoadDataTask(this)
            .setRecyclerView(recyclerView)
            .setSharedPreferencesHelper(prefsHelper)
        loadDataTask.execute()
    }

    // Private Helper Methods
    private fun initSharedPreferences(): SharedPreferencesHelper {
        val prefs = getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return SharedPreferencesHelper(prefs)
    }

    companion object {
        private const val PREFS_FILE_NAME = "LabExamPrefs"
    }
}