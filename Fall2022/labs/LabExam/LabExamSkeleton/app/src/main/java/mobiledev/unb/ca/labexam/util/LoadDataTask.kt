package mobiledev.unb.ca.labexam.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import mobiledev.unb.ca.labexam.model.EventInfo
import mobiledev.unb.ca.labexam.MyAdapter
import java.util.concurrent.Executors

class LoadDataTask(private val activity: AppCompatActivity) {
    private val appContext: Context = activity.applicationContext
    private var recyclerView: RecyclerView? = null
    private var sharedPreferences: SharedPreferences? = null

    fun setRecyclerView(recyclerView: RecyclerView?): LoadDataTask {
        this.recyclerView = recyclerView
        return this
    }

    fun setSharedPreferences(sharedPreferences: SharedPreferences?): LoadDataTask {
        this.sharedPreferences = sharedPreferences
        return this
    }

    fun execute() {
        Executors.newSingleThreadExecutor()
            .execute {
                val mainHandler = Handler(Looper.getMainLooper())
                // TODO
                //  Load the data from the JSON assets file and return the list of host nations

                // TODO
                //  Using the updateDisplay method update the UI with the results
            }
    }

    private fun updateDisplay(eventsInfoList: List<EventInfo>) {
        // TODO
        //  Setup the RecyclerView using the setupRecyclerView method

        // TODO
        //  Create a Toast indicating that the file has been loaded
    }

    private fun setupRecyclerView(eventsInfoList: List<EventInfo>) {
        recyclerView!!.adapter = MyAdapter(activity, eventsInfoList, sharedPreferences!!)
    }
}