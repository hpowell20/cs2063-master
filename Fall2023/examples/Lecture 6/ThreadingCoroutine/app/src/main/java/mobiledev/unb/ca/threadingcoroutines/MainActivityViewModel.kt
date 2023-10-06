package mobiledev.unb.ca.threadingcoroutines

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MainActivityViewModel : ViewModel() {
    private val mBitmapResID = R.drawable.painter

    fun loadImage(appContext: Context, imageView: ImageView?, progressBar: ProgressBar?) {
        viewModelScope.launch{
            // Show the progress bar
            progressBar!!.visibility = ProgressBar.VISIBLE

            val bitmap = doLoadBitmap(appContext, progressBar)
            imageView!!.setImageBitmap(bitmap)

            // Hide the progress bar
            progressBar.visibility = ProgressBar.INVISIBLE
        }
    }

    private suspend fun doLoadBitmap(appContext: Context,
                                    progressBar: ProgressBar?): Bitmap? {
        // Create a new coroutine to move the execution off the UI thread
        return withContext(Dispatchers.IO) {
            // Simulating long-running operation
            for (i in 1 until 10) {
                sleep()
                progressBar!!.progress = i * 10
            }

            // Decode the image and return it
            val bitmap = BitmapFactory.decodeResource(appContext.resources, mBitmapResID)
            bitmap
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
}