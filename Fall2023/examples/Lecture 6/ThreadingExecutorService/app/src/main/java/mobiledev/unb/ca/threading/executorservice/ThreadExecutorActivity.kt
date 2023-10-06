package mobiledev.unb.ca.threading.executorservice

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mobiledev.unb.ca.threading.executorservice.ThreadExecutorFragment.OnFragmentInteractionListener

class ThreadExecutorActivity : AppCompatActivity(), OnFragmentInteractionListener {
    private var mImageView: ImageView? = null
    private var mProgressBar: ProgressBar? = null
    private var mAsyncTaskFragment: ThreadExecutorFragment? = null
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
            mAsyncTaskFragment = supportFragmentManager
                .findFragmentByTag(ThreadExecutorFragment.TAG) as ThreadExecutorFragment
            mImageView!!.setImageBitmap(mAsyncTaskFragment!!.imageBitmap)
        } else {
            // Create headless Fragment that runs LoadIconTask and stores the loaded bitmap
            mAsyncTaskFragment = ThreadExecutorFragment()
            supportFragmentManager
                .beginTransaction()
                .add(mAsyncTaskFragment!!, ThreadExecutorFragment.TAG)
                .commit()
        }
    }

    fun onClickLoadButton() {
        loadButton.isEnabled = false
        mAsyncTaskFragment?.onButtonPressed()
    }

    fun onClickOtherButton() {
        Toast.makeText(this@ThreadExecutorActivity, "I'm Working",
            Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PROGRESS_BAR_VISIBLE_KEY, mProgressBar!!.visibility)
        outState.putInt(PROGRESS_BAR_PROGRESS_KEY, mProgressBar!!.progress)
    }

    // Callbacks from ThreadExecutorFragment
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