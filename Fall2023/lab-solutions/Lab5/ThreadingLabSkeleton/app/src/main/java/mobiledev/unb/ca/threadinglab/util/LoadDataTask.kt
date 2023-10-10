package mobiledev.unb.ca.threadinglab.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import mobiledev.unb.ca.threadinglab.MyAdapter
import mobiledev.unb.ca.threadinglab.model.Course
import java.util.concurrent.Executors

class LoadDataTask(private val activity: AppCompatActivity) {
    private val appContext: Context = activity.applicationContext
    private var recyclerView: RecyclerView? = null

    fun setRecyclerView(recyclerView: RecyclerView?): LoadDataTask {
        this.recyclerView = recyclerView
        return this
    }

    fun execute() {
        Executors.newSingleThreadExecutor()
            .execute {
                val mainHandler = Handler(Looper.getMainLooper())
                // TODO
                //  Load the data from the JSON assets file and return the list of courses
                val jsonUtils = JsonUtils(appContext)
                val coursesList = jsonUtils.getCourses()

                // Simulate a long-running operation
                for (i in 1 until DOWNLOAD_TIME) {
                    sleep()
                }

                // TODO
                //  Using the updateDisplay method update the UI with the results
                mainHandler.post { updateDisplay(coursesList) }
            }
    }

    private fun sleep() {
        try {
            val mDelay = 500
            Thread.sleep(mDelay.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun updateDisplay(courseList: ArrayList<Course>) {
        // TODO
        //  Setup the RecyclerView using the setupRecyclerView method
        setupRecyclerView(courseList)

        // TODO
        //  Create a Toast indicating that the file has been loaded
        Toast.makeText(appContext,
            "Course List Loaded",
            Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(courseList: ArrayList<Course>) {
        recyclerView!!.adapter = MyAdapter(activity, courseList)
    }

    companion object {
        private const val DOWNLOAD_TIME = 5 // Download time simulation
    }
}