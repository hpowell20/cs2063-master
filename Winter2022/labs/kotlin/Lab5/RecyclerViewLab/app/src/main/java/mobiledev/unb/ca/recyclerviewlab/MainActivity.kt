package mobiledev.unb.ca.recyclerviewlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO 1
        //  Get the ArrayList of Courses from the JsonUtils class
        //  (Ideally we would do this loading off of the main thread. We'll get to that
        //  in the next lab. Today we're focusing on displaying scrolling lists.)

        // TODO 2
        //  Get a reference to the RecyclerView and set its adapter
        //  to be an instance of MyAdapter, which you will need to create
        //  using the ArrayList of courses from above.
    }
}