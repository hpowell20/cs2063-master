package mobiledev.unb.ca.recyclerviewlab

import mobiledev.unb.ca.recyclerviewlab.model.Course
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import java.util.ArrayList

// The RecyclerView.Adapter class provides a layer of abstraction between the
// RecyclerView's LayoutManager and the underlying data that is being displayed,
// in this case the ArrayList of Course objects.
class MyAdapter(private val mDataset: ArrayList<Course>, private val parentActivity: Activity) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    // ViewHolder represents an individual item to display. In this case
    // it will just be a single TextView (displaying the title of a course)
    // but RecyclerView gives us the flexibility to do more complex things
    // (e.g., display an image and some text).
    class ViewHolder(private var mTextView: TextView) : RecyclerView.ViewHolder(
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
        // TODO 3
        //  Get the Course at index position in mDataSet
        //  (Hint: you might need to declare this variable as final.)

        // TODO 4
        //  Set the TextView in the ViewHolder (holder) to be the title for this Course

        // TODO 5
        //  Set the onClickListener for the TextView in the ViewHolder (holder) such
        //  that when it is clicked, it creates an explicit intent to launch DetailActivity
        //  HINT: You will need to put two extra pieces of information in this intent:
        //      The Course title and it's description
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }
}