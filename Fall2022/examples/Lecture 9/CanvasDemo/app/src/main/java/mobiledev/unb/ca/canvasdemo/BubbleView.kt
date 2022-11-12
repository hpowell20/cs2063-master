package mobiledev.unb.ca.canvasdemo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import java.util.Random
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

@SuppressLint("ViewConstructor")
class BubbleView (context: Context?, displayWidth: Int, displayHeight: Int) :
    View(context) {

    // Reference to the thread job
    private var mMoverFuture: ScheduledFuture<*>? = null

    // Display dimensions
    private var mStepX: Int
    private var mStepY: Int
    private val mPainter = Paint()

    private val mDisplayWidth: Int = displayWidth
    private val mDisplayHeight: Int = displayHeight
    private var mCurrX: Float = displayWidth / 2.0f
    private var mCurrY: Float = displayHeight / 2.0f

    // Reference to the scaled bitmap object
    private var scaledBitmap: Bitmap

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(scaledBitmap, mCurrX, mCurrY, mPainter)
    }

    fun startMovement() {
        // Creates a WorkerThread
        val executor = Executors.newScheduledThreadPool(1)

        // Execute the run() in Worker Thread every REFRESH_RATE milliseconds
        // Save reference to this job in mMoverFuture
        mMoverFuture = executor.scheduleWithFixedDelay({
            val stillOnScreen = moveWhileOnScreen()
            if (stillOnScreen) {
                this@BubbleView.postInvalidate()
            }
        }, 0, REFRESH_RATE.toLong(), TimeUnit.MILLISECONDS)
    }

    private fun moveWhileOnScreen(): Boolean {
        mCurrX += mStepX
        mCurrY += mStepY

        // Return true if the BubbleView is on the screen
        return mCurrX <= mDisplayWidth
                && mCurrX + BITMAP_SIZE >= 0
                && mCurrY <= mDisplayHeight
                && mCurrY + BITMAP_SIZE >= 0
    }

    companion object {
        private const val BITMAP_SIZE = 64
        private const val MAX_STEP = 10
        private const val REFRESH_RATE = 40
    }

    init {
        // Set the bubble image from the drawable resource
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.b64)
        scaledBitmap = Bitmap.createScaledBitmap(bitmap, BITMAP_SIZE, BITMAP_SIZE,false)

        // Start in roughly the centre
        val r = Random()

        // Pick a random x and y step
        mStepX = r.nextInt(MAX_STEP) + 1
        mStepY = r.nextInt(MAX_STEP) + 1

        // Pick a random x and y direction to step
        mStepX = if (r.nextBoolean()) mStepX else -mStepX
        mStepY = if (r.nextBoolean()) mStepY else -mStepY

        // Smooth out the edges
        mPainter.isAntiAlias = true
    }
}