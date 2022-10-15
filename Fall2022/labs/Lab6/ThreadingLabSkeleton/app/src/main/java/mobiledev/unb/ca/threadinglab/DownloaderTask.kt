package mobiledev.unb.ca.threadinglab

import android.content.Context
import android.os.Handler
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import android.os.Looper
import android.widget.Button
import mobiledev.unb.ca.threadinglab.model.GeoData
import java.util.ArrayList
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class DownloaderTask(private val activity: GeoDataListActivity) {
    private val appContext: Context = activity.applicationContext
    private var refreshButton: Button? = null
    private var progressBar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null

    fun setRefreshButton(refreshButton: Button?): DownloaderTask {
        this.refreshButton = refreshButton
        return this
    }

    fun setProgressBar(progressBar: ProgressBar?): DownloaderTask {
        this.progressBar = progressBar
        return this
    }

    fun setRecyclerView(recyclerView: RecyclerView?): DownloaderTask {
        this.recyclerView = recyclerView
        return this
    }

    fun execute() {
        // TODO
        //  Disable the button so it can't be clicked again once a download has been started
        //  Hint: Button is subclass of TextView. Read this document to see how to disable it.
        //  http://developer.android.com/reference/android/widget/TextView.html

        // TODO
        //  Set the progress bar's maximum to be DOWNLOAD_TIME, its initial progress to be
        //  0, and also make sure it's visible.
        //  Hint: Read the documentation on ProgressBar
        //  http://developer.android.com/reference/android/widget/ProgressBar.html

        // Perform background call to read the information from the URL
        Executors.newSingleThreadExecutor().execute {
            val handler = Handler(Looper.getMainLooper())

            // TODO
            //  Get the ArrayList of GeoData objects from the JsonUtils class and store the
            //  data in mGeoDataList

            // Simulating long-running operation
            for (i in 1 until DOWNLOAD_TIME) {
                sleep()
                // TODO
                //  Update the progress bar using values
            }

            // TODO
            //  Call the updateDisplay method update the UI with the results
        }
    }

    private fun sleep() {
        try {
            val mDelay = 500
            TimeUnit.MILLISECONDS.sleep(mDelay.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun updateDisplay(mGeoDataList: ArrayList<GeoData>) {
        // TODO
        //  With the download completed, enable the button again

        // TODO
        //  Reset the progress bar, and make it disappear

        // TODO
        //  Setup the RecyclerView

        // TODO
        //  Create a Toast message indicating that the download is complete
        //  HINT: Use appContext for the context value
    }

    private fun setupRecyclerView(mGeoDataList: List<GeoData>) {
        recyclerView!!.adapter =
            ItemRecyclerViewAdapter(mGeoDataList, activity, activity.isTwoPane)
    }

    companion object {
        private const val DOWNLOAD_TIME = 4 // Download time simulation
    }
}