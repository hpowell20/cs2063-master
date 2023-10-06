package mobiledev.unb.ca.threading.executorservice

import androidx.fragment.app.Fragment
import android.content.Context
import android.graphics.Bitmap
import android.widget.ProgressBar
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.lang.RuntimeException
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

class ThreadExecutorFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null
    var imageBitmap: Bitmap? = null
        private set

    fun onButtonPressed() {
        LoadIconTask(this).execute()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFragmentInteractionListener) {
            context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    // Methods to call back to the hosting Activity
    private fun setProgressBarVisibility(isVisible: Int) {
        if (null != mListener) {
            mListener!!.setProgressBarVisibility(isVisible)
        }
    }

    private fun setImageBitmap(result: Bitmap) {
        imageBitmap = result
        if (null != mListener) {
            mListener!!.setImageBitmap(result)
        }
    }

    private fun setProgress(value: Int) {
        if (null != mListener) {
            mListener!!.setProgress(value)
        }
    }

    // Interface to Hosting Activity
    interface OnFragmentInteractionListener {
        fun setProgressBarVisibility(isVisible: Int)
        fun setImageBitmap(result: Bitmap?)
        fun setProgress(value: Int?)
    }

    internal class LoadIconTask(fragment: ThreadExecutorFragment) {
        // GC can reclaim weakly referenced variables.
        private val mAsyncTaskFragment: WeakReference<ThreadExecutorFragment> = WeakReference(fragment)

        fun execute() {
            Log.w("Fragment", "execute")
            val taskFragment = mAsyncTaskFragment.get()

            Executors.newSingleThreadExecutor().execute {
                val handler = Handler(Looper.getMainLooper())

                // Show the progress bar
                handler.post { taskFragment!!.setProgressBarVisibility(ProgressBar.VISIBLE) }

                // Simulating long-running operation
                for (i in 1 until DOWNLOAD_TIME) {
                    sleep()
                    //  Update the progress bar using values
                    handler.post { taskFragment!!.setProgress(i * 10) }
                }

                // Show the image
                handler.post {
                    taskFragment!!.setImageBitmap(
                        BitmapFactory.decodeResource(taskFragment.resources, PAINTER))
                }

                // Hide the progress bar
                handler.post { taskFragment!!.setProgressBarVisibility(ProgressBar.INVISIBLE) }
            }
        }

        private fun sleep() {
            try {
                val mDelay = 500
                Thread.sleep(mDelay.toLong())
            } catch (e: InterruptedException) {
                Log.e(TAG, e.toString())
            }
        }
    }

    companion object {
        const val TAG = "ThreadExecutorFragment"
        private const val DOWNLOAD_TIME = 10 // Download time simulation
        private val PAINTER = R.drawable.painter
    }
}