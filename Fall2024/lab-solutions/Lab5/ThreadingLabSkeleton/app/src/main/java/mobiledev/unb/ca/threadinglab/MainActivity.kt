package mobiledev.unb.ca.threadinglab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import mobiledev.unb.ca.threadinglab.util.LoadDataTask

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO 1
        //  Get a reference to the RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)

        // TODO 2
        //  Create an instance of LoadDataTask using this activity in the constructor
        //  and use the setters to pass in the recycler view object
        //  needed during execution
        LoadDataTask(this).setRecyclerView(recyclerView).execute()
    }
}