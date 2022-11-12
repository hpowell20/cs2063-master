//package course.labs.graphicslab
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Canvas
//import android.graphics.Paint
//import android.view.View
//import java.util.concurrent.Executors
//import java.util.concurrent.ScheduledFuture
//import java.util.concurrent.TimeUnit
//import kotlin.math.pow
//import kotlin.math.sqrt
//
//class BubbleView internal constructor(context: Context?, x: Float, y: Float) :  View(context) {
//    private val mPainter = Paint()
//    private var mMoverFuture: ScheduledFuture<*>? = null
//
//    // Reference to the scaled bitmap object
//    private var scaledBitmap: Bitmap
//
////    private val mScaledBitmap: Bitmap = Bitmap.createScaledBitmap(mBitmap!!, SCALED_BITMAP_SIZE,
////        SCALED_BITMAP_SIZE, true)
//
//    // location and direction of the bubble
//    private var mXPos: Float
//    private var mYPos: Float
//    private val mRadius: Float = SCALED_BITMAP_SIZE / 2.0f
//
//    // Speed of bubble
//    private var mDx: Float
//    private var mDy: Float
//
//    // Rotation and speed of rotation of the bubble
//    private var mRotate: Long = 0
//    private val mDRotate: Long
//
//    // setSpeedAndDirection called by onSensorChanged(), values based on
//    // accelerometer data and scaled
//    fun setSpeedAndDirection(x: Float, y: Float) {
//        // NOTE the layout is set to landscape.  This means that the coordinate
//        // system does not change, however, the range for x and y are essentially reversed
//        mDx = y
//        mDy = x
//
//        // TODO
//        //  Once your app is working, experiment with alternative
//        //  ways of calculating mDx and mDy based on x and y to change
//        //  the "feel". Some ideas are shown below.
//
//        // Example 1: Uncomment this to make the ball go faster!
//        //mDx = 2 * y;
//        //mDy = 2 * x;
//
//        // Example 2: Uncomment this to make the ball accelerate based on sensor
//        // input. You can also scale the contribution of x and y.
//        //mDx += y
//        //mDy += x
//    }
//
//    // Start moving the BubbleView & updating the display
//    fun startMovement() {
//        // Creates a WorkerThread
//        val executor = Executors.newScheduledThreadPool(1)
//
//        // Execute the run() in Worker Thread every REFRESH_RATE
//        // milliseconds
//        // Save reference to this job in mMoverFuture
//        mMoverFuture = executor.scheduleWithFixedDelay({
//            doMove()
//            this@BubbleView.postInvalidate()
//        }, 0, REFRESH_RATE.toLong(), TimeUnit.MILLISECONDS)
//    }
//
//    fun stopMovement() {
//        if (null != mMoverFuture) {
//            if (!mMoverFuture!!.isDone) {
//                mMoverFuture!!.cancel(true)
//            }
//
//            // This work will be performed on the UI Thread
//            mFrame!!.post {
//                mFrame!!.removeView(this@BubbleView)
//                setPlayerMessage()
//            }
//        }
//    }
//
//    // Return true if x and y intersect the position of the Bubble
//    @Synchronized
//    fun intersects(x: Float, y: Float): Boolean {
//        val centerX = mXPos + mRadius
//        val centerY = mYPos + mRadius
//        return sqrt((centerX - x).toDouble().pow(2.0) + (centerY - y).toDouble().pow(2.0)) <= mRadius
//    }
//
//    // Draw the Bubble at its current location
//    @Synchronized
//    override fun onDraw(canvas: Canvas) {
//        canvas.save()
//        mRotate += mDRotate
//        canvas.rotate(mRotate.toFloat(), mXPos + mRadius, mYPos + mRadius)
//        canvas.drawBitmap(mScaledBitmap, mXPos, mYPos, mPainter)
//        canvas.restore()
//    }
//
//    // Move the Bubble
//    @Synchronized
//    private fun doMove() {
//        // Don't let the bubble go beyond the edge of the screen
//        // Set the speed to 0 if the bubble hits an edge.
//        mXPos += mDx
//        if (mXPos >= mDisplayWidth) {
//            mXPos = mDisplayWidth.toFloat()
//            mDx = 0f
//        } else if (mXPos <= 0) {
//            mXPos = 0f
//            mDx = 0f
//        }
//
//        mYPos += mDy
//        if (mYPos >= mDisplayHeight) {
//            mYPos = mDisplayHeight.toFloat()
//            mDy = 0f
//        } else if (mYPos <= 0) {
//            mYPos = 0f
//            mDy = 0f
//        }
//    }
//
//    init {
//        // Set the bubble image from the drawable resource
//        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ball)
//        scaledBitmap = Bitmap.createScaledBitmap(bitmap, SCALED_BITMAP_SIZE, SCALED_BITMAP_SIZE, true)
//
//        // Adjust position to center the bubble under user's finger
//        mXPos = x - mRadius
//        mYPos = y - mRadius
//
//        // Set speed to 0 initially; it will be updated soon based on sensor input
//        mDx = 0f
//        mDy = 0f
//
//        // Set speed of rotation to 5
//        mDRotate = 5
//        mPainter.isAntiAlias = true
//    }
//
//    companion object {
//        private const val TAG = "BubbleActivity"
//        private const val NO_FILTER = 0
//        private const val LOW_PASS_FILTER = 1
//        private const val HIGH_PASS_FILTER = 2
//        private var mFilter = NO_FILTER
//
//        private const val BITMAP_SIZE = 128
//        private const val REFRESH_RATE = 5
//        private const val SCALED_BITMAP_SIZE = BITMAP_SIZE * 2
//    }
//}