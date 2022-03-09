package mobiledev.unb.ca.labexam.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import mobiledev.unb.ca.labexam.model.GamesInfo
import mobiledev.unb.ca.labexam.MyAdapter
import java.util.concurrent.Executors

class LoadDataTask(private val activity: AppCompatActivity) {
    private val appContext: Context
    private var recyclerView: RecyclerView? = null
    private var sharedPreferences: SharedPreferences? = null

    fun setRecyclerView(recyclerView: RecyclerView?): LoadDataTask {
        this.recyclerView = recyclerView
        return this
    }

    fun setSharedPreferencesHelper(sharedPreferences: SharedPreferences?): LoadDataTask {
        this.sharedPreferences = sharedPreferences
        return this
    }

    fun execute() {
        Executors.newSingleThreadExecutor()
            .execute { val mainHandler = Handler(Looper.getMainLooper()) }
    }

    private fun setupRecyclerView(gamesInfoList: List<GamesInfo>) {
        recyclerView!!.adapter = MyAdapter(activity, gamesInfoList, sharedPreferences!!)
    }

    init {
        appContext = activity.applicationContext
    }
}