package mobiledev.unb.ca.threadinglab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO 1
        //  Get a reference to the RecyclerView

        // TODO 2
        //   Get a reference to the circular progress bar

        // TODO 3
        //  Create an instance of LoadDataTask using this activity in the
        //  constructor and use the setters to pass in the
        //  recycler view and progress bar objects needed during execution
    }
}