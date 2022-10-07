package mobiledev.unb.ca.recyclerviewlab

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mobiledev.unb.ca.recyclerviewlab.model.Course
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO 1
        //  Get the ArrayList of Courses from the JsonUtils class
        //  (Ideally we would do this loading off of the main thread. We'll get to that
        //  in the next lab. Today we're focusing on displaying scrolling lists.)

        // TODO 2
        //  Get a reference to the RecyclerView

        // TODO 3
        //  Set its adapter to be an instance of MyAdapter, which you will need to create
        //  using the ArrayList of courses from above
        //  HINT: Take note of the constructor parameters; for parentActivity use this class
    }

    /**
     * The RecyclerView.Adapter class provides a layer of abstraction between the
     * RecyclerView's LayoutManager and the underlying data that is being displayed,
     * in this case the ArrayList of Course objects.
     *
     * @param mDataset The list of courses
     * @param parentActivity The activity used to create intents for launching activities
     */
    class MyAdapter(private val mDataset: ArrayList<Course>, private val parentActivity: Activity) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        // ViewHolder represents an individual item to display. In this case
        // it will just be a single TextView (displaying the title of a course)
        // but RecyclerView gives us the flexibility to do more complex things
        // (e.g., display an image and some text).
        class ViewHolder(var mTextView: TextView) : RecyclerView.ViewHolder(
            mTextView)

        // The inflate method of the LayoutInflater class can be used to obtain the
        // View object corresponding to an XML layout resource file. Here
        // onCreateViewHolder inflates the TextView corresponding to item_layout.xml
        // and uses it to instantiate a ViewHolder.
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false) as TextView
            return ViewHolder(v)
        }

        // onBindViewHolder binds a ViewHolder to the data at the specified
        // position in mDataset
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // TODO 1
            //  Get the Course at index position in mDataSet
            //  (Hint: you might need to declare this variable as final.)

            // TODO 2
            //  Set the TextView in the ViewHolder (holder) to be the title for this Course

            // TODO 3
            //  Set the onClickListener for the TextView in the ViewHolder (holder) such
            //  that when it is clicked, it creates an explicit intent to launch DetailActivity
            //  from the parentActivity object passed into the class constructor
            //  HINT: You will need to put two extra pieces of information in this intent:
            //      The Course title
            //      The Course description
        }

        override fun getItemCount(): Int {
            return mDataset.size
        }
    }
}