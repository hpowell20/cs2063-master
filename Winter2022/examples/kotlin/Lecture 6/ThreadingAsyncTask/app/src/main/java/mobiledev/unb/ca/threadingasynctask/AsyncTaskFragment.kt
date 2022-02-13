package mobiledev.unb.ca.threadingasynctask

import android.app.Fragment
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.AsyncTask
import android.widget.ProgressBar
import android.graphics.BitmapFactory
import android.util.Log
import java.lang.RuntimeException
import java.lang.ref.WeakReference

class AsyncTaskFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null
    var imageBitmap: Bitmap? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun onButtonPressed() {
        LoadIconTask(this).execute(PAINTER)
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

    // Upcalls to hosting Activity
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

    internal class LoadIconTask(fragment: AsyncTaskFragment) : AsyncTask<Int, Int?, Bitmap>() {
        // GC can reclaim weakly referenced variables.
        private val mAsyncTaskFragment: WeakReference<AsyncTaskFragment> = WeakReference(fragment)
        override fun onPreExecute() {
            mAsyncTaskFragment.get()!!.setProgressBarVisibility(ProgressBar.VISIBLE)
        }

        override fun doInBackground(vararg resId: Int?): Bitmap? {
            // simulating long-running operation
            for (i in 1..10) {
                sleep()
                publishProgress(i * 10)
            }
            return resId[0]?.let {
                BitmapFactory.decodeResource(mAsyncTaskFragment.get()!!.resources,
                    it)
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            values[0]?.let { mAsyncTaskFragment.get()!!.setProgress(it) }
        }

        override fun onPostExecute(result: Bitmap) {
            mAsyncTaskFragment.get()!!.setProgressBarVisibility(ProgressBar.INVISIBLE)
            mAsyncTaskFragment.get()!!.setImageBitmap(result)
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
        const val TAG = "AsyncTaskFragment"
        private const val PAINTER = R.drawable.painter
    }
}