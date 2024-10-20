package mobiledev.unb.ca.labexam

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import mobiledev.unb.ca.labexam.util.LoadDataTask

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get a reference to the progress bar
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // Get a reference to the RecyclerView and configure it
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // TODO: SharedPreferences
        //  Setup the instance of shared preferences you will be using
        val sharedPreferences = initSharedPreferences()

        // TODO
        //  1. Create an instance of LoadDataTask using this activity in the constructor
        //  2. Use the setters to pass in the objects needed during execution
        //  (progress bar, recycler view, and shared preferences)
        //  3. Execute it
        LoadDataTask(this)
            .setRecyclerView(recyclerView)
            .setSharedPreferences(sharedPreferences)
            .setProgressBar(progressBar)
            .execute()
    }

    // Private Helper Methods
    private fun initSharedPreferences(): SharedPreferences {
        return getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREFS_FILE_NAME = "LabExamPrefs"
    }
}