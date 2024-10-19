package mobiledev.unb.ca.composelistlab.utils

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoadDataTask(private val appContext: Context) {
    fun execute() {
        MainScope().launch {
            return@launch getCourseList()
        }
    }

    private suspend fun getCourseList() {
        // Create a new coroutine to move the execution off the UI thread
        return withContext(Dispatchers.IO) {
            // Simulating long-running operation
            for (i in 1 until DOWNLOAD_TIME) {
                sleep()
                // progressBar!!.progress = i * 10
            }

            // Read the file and return the results
            JsonUtils(appContext).courses
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

    companion object {
        private const val DOWNLOAD_TIME = 10 // Download time simulation
    }
}