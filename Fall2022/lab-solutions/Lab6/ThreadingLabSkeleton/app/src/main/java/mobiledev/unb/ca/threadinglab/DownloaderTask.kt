package mobiledev.unb.ca.threadinglab

import android.content.Context
import android.os.Handler
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import mobiledev.unb.ca.threadinglab.model.GeoData
import mobiledev.unb.ca.threadinglab.util.JsonUtils
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
        refreshButton!!.isEnabled = false

        // TODO
        //  Set the progress bar's maximum to be DOWNLOAD_TIME, its initial progress to be
        //  0, and also make sure it's visible.
        //  Hint: Read the documentation on ProgressBar
        //  http://developer.android.com/reference/android/widget/ProgressBar.html
        progressBar!!.isIndeterminate = false
        progressBar!!.progress = 0
        progressBar!!.max = DOWNLOAD_TIME
        progressBar!!.visibility = ProgressBar.VISIBLE

        // Perform background call to read the information from the URL
        Executors.newSingleThreadExecutor().execute {
            val handler = Handler(Looper.getMainLooper())

            // TODO
            //  Create an instance of JsonUtils and get the data from it,
            //  store the data in mGeoDataList
            val jsonUtils = JsonUtils()
            val mGeoDataList: ArrayList<GeoData>? = jsonUtils.getGeoData()
            if (null == mGeoDataList) {
                Toast.makeText(appContext,
                    activity.getString(R.string.download_error_msg),
                    Toast.LENGTH_SHORT).show()
            }

            // Simulating long-running operation
            for (i in 1..DOWNLOAD_TIME) {
                sleep()
                // TODO
                //  Update the progress bar using values
                handler.post { progressBar!!.progress = i * 10 }
            }

            // TODO
            //  Using the updateDisplay method update the UI with the results
            handler.post { updateDisplay(mGeoDataList!!) }
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
        refreshButton!!.isEnabled = true

        // TODO
        //  Reset the progress bar, and make it disappear
        progressBar!!.progress = 0
        progressBar!!.visibility = ProgressBar.INVISIBLE

        // TODO
        //  Setup the RecyclerView
        setupRecyclerView(mGeoDataList)

        // TODO
        //  Create a Toast indicating that the download is complete
        Toast.makeText(appContext,
            activity.getString(R.string.download_complete),
            Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(mGeoDataList: List<GeoData>) {
        recyclerView!!.adapter =
            ItemRecyclerViewAdapter(mGeoDataList, activity, activity.isTwoPane)
    }

    companion object {
        private const val DOWNLOAD_TIME = 4 // Download time simulation
    }
}