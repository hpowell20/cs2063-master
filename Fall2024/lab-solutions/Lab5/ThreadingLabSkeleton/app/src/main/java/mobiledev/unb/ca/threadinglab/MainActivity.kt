package mobiledev.unb.ca.threadinglab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import mobiledev.unb.ca.threadinglab.util.LoadDataTask

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO 1
        //  Get a reference to the RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)

        // TODO 2
        //  Get a reference to the progress indicator
        //  HINT: This is not a standard ProgressBar as we have seen in class; it
        //        is of type CircularProgressIndicator.
        //        The class import has already been added.
        val circularProgressIndicator = findViewById<CircularProgressIndicator>(R.id.circularProgressIndicator)
        //val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // TODO 3
        //  Create an instance of LoadDataTask using this activity in the constructor
        //  and use the setters to pass in the recycler view object
        //  needed during execution
        /*val loadDataTask = LoadDataTask(activity = this,
            recyclerView = recyclerView,
            circularProgressIndicator = circularProgressIndicator)
        loadDataTask.execute() */
        LoadDataTask(this).
            setRecyclerView(recyclerView).
            setCircularProgressIndicator(circularProgressIndicator).
            //setProgressBar(progressBar).
            execute()
    }
}