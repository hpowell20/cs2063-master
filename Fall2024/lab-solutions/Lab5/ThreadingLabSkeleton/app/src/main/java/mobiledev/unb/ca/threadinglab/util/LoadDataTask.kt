package mobiledev.unb.ca.threadinglab.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.progressindicator.CircularProgressIndicator
import mobiledev.unb.ca.threadinglab.MyAdapter
import mobiledev.unb.ca.threadinglab.R
import mobiledev.unb.ca.threadinglab.model.Course
import java.util.concurrent.Executors

//class LoadDataTask(private val activity: AppCompatActivity,
//                   private val recyclerView: RecyclerView,
//                   private val circularProgressIndicator: CircularProgressIndicator) {
class LoadDataTask(private val activity: AppCompatActivity) {
    private val appContext: Context = activity.applicationContext
    private lateinit var recyclerView: RecyclerView
    private lateinit var circularProgressIndicator: CircularProgressIndicator
    private lateinit var progressBar: ProgressBar

    fun setRecyclerView(recyclerView: RecyclerView): LoadDataTask {
        this.recyclerView = recyclerView
        return this
    }

    fun setCircularProgressIndicator(circularProgressIndicator: CircularProgressIndicator): LoadDataTask {
        this.circularProgressIndicator = circularProgressIndicator
        return this
    }

    fun setProgressBar(progressBar: ProgressBar): LoadDataTask {
        this.progressBar = progressBar
        return this
    }

    fun execute() {
        Executors.newSingleThreadExecutor()
            .execute {
                val mainHandler = Handler(Looper.getMainLooper())

                // Show the circular progress bar
                circularProgressIndicator.visibility = ProgressBar.VISIBLE
                // progressBar.visibility = ProgressBar.VISIBLE

                // TODO 1
                //  Load the data from the JSON assets file and return the list of courses
                val jsonUtils = JsonUtils(appContext)
                val coursesList = jsonUtils.getCourses()

                // Simulate a long-running operation
                for (i in 1 until DOWNLOAD_TIME) {
                    sleep()
                }

                // TODO 2
                //  Using the updateDisplay method update the UI with the results
                //  HINT:
                //   This call must be made sending a post message through the
                //   mainHandler to the UI thread
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
        // TODO 3
        //  Pass in the course list to the setupRecyclerView method
        setupRecyclerView(courseList)

        // Hide the circular progress bar
        circularProgressIndicator.visibility = ProgressBar.INVISIBLE
        //progressBar.visibility =  ProgressBar.INVISIBLE

        // TODO 4
        //  Create a Toast indicating that the file has been loaded
        //    HINT: Read this for help with Toast:
        //    http://developer.android.com/guide/topics/ui/notifiers/toasts.html
        Toast.makeText(appContext,
            "Course List Loaded",
            Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(courseList: ArrayList<Course>) {
        recyclerView.adapter = MyAdapter(activity, courseList)
    }

    companion object {
        private const val DOWNLOAD_TIME = 5 // Download time simulation
    }
}