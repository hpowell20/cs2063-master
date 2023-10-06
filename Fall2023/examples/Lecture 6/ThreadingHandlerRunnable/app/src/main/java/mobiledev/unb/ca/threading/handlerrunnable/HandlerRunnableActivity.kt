package mobiledev.unb.ca.threading.handlerrunnable

import android.app.Activity
import android.widget.ProgressBar
import android.os.Bundle
import android.os.Parcelable
import android.graphics.Bitmap
import android.widget.Toast
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView

class HandlerRunnableActivity : Activity() {
    private var mLoadIconTask: LoadIconTask? = null
    private var mImageView: ImageView? = null
    private var mProgressBar: ProgressBar? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        mImageView = findViewById(R.id.imageView)
        mProgressBar = findViewById(R.id.progressBar)

        if (null != savedInstanceState) {
            mProgressBar!!.visibility = savedInstanceState.getInt(PROGRESS_BAR_VISIBLE_KEY)
            mProgressBar!!.progress = savedInstanceState.getInt(PROGRESS_BAR_PROGRESS_KEY)
            mImageView!!.setImageBitmap(savedInstanceState.getParcelable<Parcelable>(BITMAP_KEY) as Bitmap?)
            mLoadIconTask = lastNonConfigurationInstance as LoadIconTask?
            if (null != mLoadIconTask) {
                mLoadIconTask!!.setProgressBar(mProgressBar)
                    .setImageView(mImageView)
            }
        }
    }

    fun onClickLoadButton(v: View) {
        v.isEnabled = false
        mLoadIconTask = LoadIconTask(applicationContext)
            .setImageView(mImageView)
            .setProgressBar(mProgressBar)
        mLoadIconTask!!.start()
    }

    fun onClickOtherButton(v: View?) {
        Toast.makeText(this@HandlerRunnableActivity, "I'm Working",
            Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PROGRESS_BAR_VISIBLE_KEY, mProgressBar!!.visibility)
        outState.putInt(PROGRESS_BAR_PROGRESS_KEY, mProgressBar!!.progress)
        if (null != mImageView!!.drawable) {
            val bm = (mImageView!!.drawable as BitmapDrawable).bitmap
            outState.putParcelable(BITMAP_KEY, bm)
        }
    }

    override fun onRetainNonConfigurationInstance(): Any {
        return mLoadIconTask!!
    }

    companion object {
        private const val PROGRESS_BAR_PROGRESS_KEY = "PROGRESS_BAR_PROGRESS_KEY"
        private const val PROGRESS_BAR_VISIBLE_KEY = "PROGRESS_BAR_VISIBLE_KEY"
        private const val BITMAP_KEY = "BITMAP_KEY"
    }
}