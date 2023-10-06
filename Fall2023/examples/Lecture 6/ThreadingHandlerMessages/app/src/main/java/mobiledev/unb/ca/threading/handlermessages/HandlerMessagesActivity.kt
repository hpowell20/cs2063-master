package mobiledev.unb.ca.threading.handlermessages

import android.app.Activity
import android.widget.ProgressBar
import android.os.Bundle
import android.os.Parcelable
import android.graphics.Bitmap
import android.widget.Toast
import android.graphics.drawable.BitmapDrawable
import android.widget.Button
import android.widget.ImageView

class HandlerMessagesActivity : Activity() {
    private var mImageView: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var loadIconTask: LoadIconTask? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        mImageView = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.progressBar)

        val loadButton = findViewById<Button>(R.id.loadButton)
        loadButton.setOnClickListener { onClickLoadButton() }

        val otherButton = findViewById<Button>(R.id.otherButton)
        otherButton.setOnClickListener { onClickOtherButton() }

        if (null != savedInstanceState) {
            progressBar!!.visibility = savedInstanceState.getInt(PROGRESS_BAR_VISIBLE_KEY)
            progressBar!!.progress = savedInstanceState.getInt(PROGRESS_BAR_PROGRESS_KEY)
            mImageView!!.setImageBitmap(savedInstanceState.getParcelable<Parcelable>(BITMAP_KEY) as Bitmap?)

            loadIconTask = lastNonConfigurationInstance as LoadIconTask?
            if (null != loadIconTask) {
                loadIconTask!!.setProgressBar(progressBar)
                    .setImageView(mImageView)
            }
        }
    }

    fun onClickLoadButton() {
        loadIconTask = LoadIconTask(applicationContext)
            .setImageView(mImageView)
            .setProgressBar(progressBar)
        loadIconTask!!.start()
    }

    fun onClickOtherButton() {
        Toast.makeText(this@HandlerMessagesActivity, "I'm Working",
            Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PROGRESS_BAR_VISIBLE_KEY, progressBar!!.visibility)
        outState.putInt(PROGRESS_BAR_PROGRESS_KEY, progressBar!!.progress)

        if (null != mImageView!!.drawable) {
            val bm = (mImageView!!.drawable as BitmapDrawable).bitmap
            outState.putParcelable(BITMAP_KEY, bm)
        }
    }

    override fun onRetainNonConfigurationInstance(): Any {
        return loadIconTask!!
    }

    companion object {
        const val SET_PROGRESS_BAR_VISIBILITY = 0
        const val PROGRESS_UPDATE = 1
        const val SET_BITMAP = 2
        private const val PROGRESS_BAR_PROGRESS_KEY = "PROGRESS_BAR_PROGRESS_KEY"
        private const val PROGRESS_BAR_VISIBLE_KEY = "PROGRESS_BAR_VISIBLE_KEY"
        private const val BITMAP_KEY = "BITMAP_KEY"
    }
}