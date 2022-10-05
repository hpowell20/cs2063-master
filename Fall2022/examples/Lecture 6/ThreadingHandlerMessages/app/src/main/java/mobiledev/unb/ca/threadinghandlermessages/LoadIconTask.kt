package mobiledev.unb.ca.threadinghandlermessages

import android.content.Context
import mobiledev.unb.ca.threadinghandlermessages.LoadIconTask.UIHandler
import mobiledev.unb.ca.threadinghandlermessages.HandlerMessagesActivity
import android.widget.ProgressBar
import mobiledev.unb.ca.threadinghandlermessages.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Message
import android.widget.ImageView
import mobiledev.unb.ca.threadinghandlermessages.LoadIconTask

class LoadIconTask internal constructor(private val mContext: Context) : Thread() {
    private val mHandler: UIHandler
    override fun run() {
        var message = mHandler.obtainMessage(HandlerMessagesActivity.SET_PROGRESS_BAR_VISIBILITY,
            ProgressBar.VISIBLE)
        mHandler.sendMessage(message)
        val mResId = R.drawable.painter
        val tmp = BitmapFactory.decodeResource(mContext.resources, mResId)
        for (i in 1..10) {
            sleep()
            message = mHandler.obtainMessage(HandlerMessagesActivity.PROGRESS_UPDATE, i * 10)
            mHandler.sendMessage(message)
        }
        message = mHandler.obtainMessage(HandlerMessagesActivity.SET_BITMAP, tmp)
        mHandler.sendMessage(message)
        message = mHandler.obtainMessage(HandlerMessagesActivity.SET_PROGRESS_BAR_VISIBILITY,
            ProgressBar.INVISIBLE)
        mHandler.sendMessage(message)
    }

    fun setImageView(imageView: ImageView?): LoadIconTask {
        mHandler.setImageView(imageView)
        return this
    }

    fun setProgressBar(progressBar: ProgressBar?): LoadIconTask {
        mHandler.setProgressBar(progressBar)
        return this
    }

    private fun sleep() {
        try {
            val mDelay = 500
            sleep(mDelay.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    internal class UIHandler : Handler() {
        private var mImageView: ImageView? = null
        private var mProgressBar: ProgressBar? = null
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                HandlerMessagesActivity.SET_PROGRESS_BAR_VISIBILITY -> {
                    mProgressBar!!.visibility = (msg.obj as Int)
                }
                HandlerMessagesActivity.PROGRESS_UPDATE -> {
                    mProgressBar!!.progress = (msg.obj as Int)
                }
                HandlerMessagesActivity.SET_BITMAP -> {
                    mImageView!!.setImageBitmap(msg.obj as Bitmap)
                }
            }
        }

        fun setImageView(mImageView: ImageView?) {
            this.mImageView = mImageView
        }

        fun setProgressBar(mProgressBar: ProgressBar?) {
            this.mProgressBar = mProgressBar
        }
    }

    init {
        mHandler = UIHandler()
    }
}