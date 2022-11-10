package mobiledev.unb.ca.labexam.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.os.Looper
import mobiledev.unb.ca.labexam.model.GamesInfo
import mobiledev.unb.ca.labexam.MyAdapter
import mobiledev.unb.ca.labexam.helper.SharedPreferencesHelper
import java.util.concurrent.Executors

class LoadDataTask(private val activity: AppCompatActivity) {
    private val appContext: Context = activity.applicationContext
    private var recyclerView: RecyclerView? = null
    //private var sharedPreferences: SharedPreferences? = null
    private var sharedPreferencesHelper: SharedPreferencesHelper? = null

    fun setRecyclerView(recyclerView: RecyclerView?): LoadDataTask {
        this.recyclerView = recyclerView
        return this
    }

//    fun setSharedPreferences(sharedPreferences: SharedPreferences?): LoadDataTask {
//        this.sharedPreferences = sharedPreferences
//        return this
//    }

    fun setSharedPreferencesHelper(sharedPreferencesHelper: SharedPreferencesHelper?): LoadDataTask {
        this.sharedPreferencesHelper = sharedPreferencesHelper
        return this
    }

    fun execute() {
        Executors.newSingleThreadExecutor()
            .execute {
                val mainHandler = Handler(Looper.getMainLooper())
                // TODO
                //  Load the data from the JSON assets file and return the list of cities
                val jsonUtils = JsonUtils(appContext)
                val gamesInfoList = jsonUtils.hostCities

                // TODO
                //  Use result to set the adapter for the RecyclerView
                mainHandler.post { setupRecyclerView(gamesInfoList!!) }
            }
    }

    private fun setupRecyclerView(gamesInfoList: List<GamesInfo>) {
        recyclerView!!.adapter = MyAdapter(activity, gamesInfoList, sharedPreferencesHelper!!)
    }
}