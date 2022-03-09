package mobiledev.unb.ca.labexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

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

        // TODO
        //  Create an instance of LoadDataTask; pass in the recycler view
        //  and shared preferences then execute it
    }
}