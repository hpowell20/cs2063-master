package mobiledev.unb.ca.threadingasynctask

import android.app.Activity
import mobiledev.unb.ca.threadingasynctask.AsyncTaskFragment.OnFragmentInteractionListener
import android.widget.ProgressBar
import android.os.Bundle
import android.widget.Toast
import android.graphics.Bitmap
import android.widget.Button
import android.widget.ImageView

class AsyncTaskActivity : Activity(), OnFragmentInteractionListener {
    private var mImageView: ImageView? = null
    private var mProgressBar: ProgressBar? = null
    private var mAsyncTaskFragment: AsyncTaskFragment? = null
    private lateinit var loadButton: Button

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        mImageView = findViewById(R.id.imageView)
        mProgressBar = findViewById(R.id.progressBar)

        loadButton = findViewById(R.id.loadButton)
        loadButton.setOnClickListener { onClickLoadButton() }

        val otherButton = findViewById<Button>(R.id.otherButton)
        otherButton.setOnClickListener { onClickOtherButton() }

        if (null != savedInstanceState) {
            mProgressBar!!.visibility = savedInstanceState.getInt(PROGRESS_BAR_VISIBLE_KEY)
            mProgressBar!!.progress = savedInstanceState.getInt(PROGRESS_BAR_PROGRESS_KEY)
            mAsyncTaskFragment = fragmentManager
                .findFragmentByTag(AsyncTaskFragment.TAG) as AsyncTaskFragment
            mImageView!!.setImageBitmap(mAsyncTaskFragment!!.imageBitmap)
        } else {
            // Create headless Fragment that runs LoadIconAsyncTask and stores the loaded bitmap
            mAsyncTaskFragment = AsyncTaskFragment()
            fragmentManager
                .beginTransaction()
                .add(mAsyncTaskFragment, AsyncTaskFragment.TAG)
                .commit()
        }
    }

    fun onClickLoadButton() {
        loadButton.isEnabled = false
        mAsyncTaskFragment?.onButtonPressed()
    }

    fun onClickOtherButton() {
        Toast.makeText(this@AsyncTaskActivity, "I'm Working",
            Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PROGRESS_BAR_VISIBLE_KEY, mProgressBar!!.visibility)
        outState.putInt(PROGRESS_BAR_PROGRESS_KEY, mProgressBar!!.progress)
    }

    // Callbacks from AsyncTaskFragment
    override fun setProgressBarVisibility(isVisible: Int) {
        mProgressBar!!.visibility = isVisible
    }

    override fun setProgress(value: Int?) {
        if (value != null) {
            mProgressBar!!.progress = value
        }
    }

    override fun setImageBitmap(result: Bitmap?) {
        mImageView!!.setImageBitmap(result)
    }

    companion object {
        private const val PROGRESS_BAR_PROGRESS_KEY = "PROGRESS_BAR_PROGRESS_KEY"
        private const val PROGRESS_BAR_VISIBLE_KEY = "PROGRESS_BAR_VISIBLE_KEY"
    }
}