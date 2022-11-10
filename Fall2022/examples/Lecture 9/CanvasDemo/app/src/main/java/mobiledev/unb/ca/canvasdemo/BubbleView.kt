package mobiledev.unb.ca.canvasdemo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import java.util.*

@SuppressLint("ViewConstructor")
class BubbleView(context: Context?, bitmap: Bitmap?, displayWidth: Int, displayHeight: Int) :
    View(context) {

    // Display dimensions
    private var mStepX: Int
    private var mStepY: Int
    private val mPainter = Paint()

    private val mDisplayWidth: Int = displayWidth
    private val mDisplayHeight: Int = displayHeight
    private var mCurrX: Float = mDisplayWidth / 2.0f
    private var mCurrY: Float = mDisplayHeight / 2.0f

    private val h: Handler = Handler(Looper.getMainLooper())
    private val mBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap!!,
        BITMAP_SIZE, BITMAP_SIZE, false)

    override fun onDraw(canvas: Canvas) {
        if (move()) {
            canvas.drawBitmap(mBitmap, mCurrX, mCurrY, mPainter)
            h.postDelayed(r, 30)
        }
    }

    private val r = Runnable {
        invalidate()
        try {
            Thread.sleep(20)
        } catch (e: InterruptedException) {
            Log.i(TAG, "InterruptedException")
        }
    }

    private fun move(): Boolean {
        mCurrX += mStepX
        mCurrY += mStepY

        // Return true if the BubbleView is on the screen
        return mCurrX <= mDisplayWidth
                && mCurrX + BITMAP_SIZE >= 0
                && mCurrY <= mDisplayHeight
                && mCurrY + BITMAP_SIZE >= 0
    }

    companion object {
        private const val TAG = "BubbleView"
        private const val BITMAP_SIZE = 64
        private const val MAX_STEP = 10
    }

    init {
        // Start in roughly the centre
        val r = Random()
        // Pick a random x and y step
        mStepX = r.nextInt(MAX_STEP) + 1
        mStepY = r.nextInt(MAX_STEP) + 1
        // Pick a random x and y direction to step
        mStepX = if (r.nextBoolean()) mStepX else -mStepX
        mStepY = if (r.nextBoolean()) mStepY else -mStepY
        mPainter.isAntiAlias = true
    }
}