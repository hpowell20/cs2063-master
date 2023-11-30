package mobiledev.unb.ca.graphicslab

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import kotlin.math.pow
import kotlin.math.sqrt

@SuppressLint("ViewConstructor")
//class BubbleView (context: Context): View(context) {
class BubbleView (context: Context?, displayWidth: Int, displayHeight: Int, x: Float, y: Float) :
    View(context) {
    private val mPainter = Paint()
    private var mMoverFuture: ScheduledFuture<*>? = null
    private var scaledBitmapSize = 0
    private var scaledBitmap: Bitmap? = null

    // Screen size
    private val mDisplayWidth: Int = displayWidth
    private val mDisplayHeight: Int = displayHeight

    // location and direction of the bubble
    private var xPos: Float
    private var yPos: Float
    private val radius: Float

    // Speed of bubble
    private var mDx = 0f
    private var mDy = 0f

    // Rotation and speed of rotation of the bubble
    private var mRotate: Long = 0
    private var mDRotate: Long = 0

    private fun setRotation() {
        // TODO
        //  Set rotation in range [1..5]
//        mDRotate = (1..5).shuffled().first().toLong()
        mDRotate = generateRandomNumberInRange(1, 5).toLong()
    }

//    private fun setRotation(r: Random) {
//        // TODO
//        //  Set rotation in range [1..5]
//        mDRotate = (r.nextInt(5) + 1).toLong()
//    }

    private fun setSpeedAndDirection() {
        // TODO
        //  Set mDx and mDy to indicate movement direction and speed
        //  Limit speed in the x and y direction to [-3..3] pixels per movement
//        mDx = (-3..3).shuffled().first().toFloat()
        mDx = generateRandomNumberInRange(-3, 3).toFloat()
//        mDy = (-3..3).shuffled().first().toFloat()
        mDy = generateRandomNumberInRange(-3, 3).toFloat()
    }

    private fun createScaledBitmap() {
        // Retrieve the original bitmap resource
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.b64)

        // TODO
        //  Set scaled bitmap size (scaledBitmapSize) in range [2..4] * BITMAP_SIZE
        scaledBitmapSize = BITMAP_SIZE * generateRandomNumberInRange(2, 4)

        // TODO
        //  Create the scaled bitmap (scaledBitmap) using size set above
        scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledBitmapSize, scaledBitmapSize, true)
    }

    // Start moving the BubbleView & updating the display
    fun startMovement(mFrame: RelativeLayout?, bubbleCountTextView: TextView?) {
        // Creates a WorkerThread
        val executor = Executors.newScheduledThreadPool(1)

        // Execute the run() in Worker Thread every REFRESH_RATE
        // milliseconds
        // Save reference to this job in mMoverFuture
        mMoverFuture = executor.scheduleWithFixedDelay({
            // TODO
            //  Implement movement logic.
            //  Each time this method is run the BubbleView should
            //  move one step. (Use moveWhileOnScreen() to do this.)
            //  If the BubbleView exits the display, stop the BubbleView's
            //  Worker Thread. (Use stopMovement() to do this.) Otherwise,
            //  request that the BubbleView be redrawn.
            val stillOnScreen = moveWhileOnScreen()
            if (stillOnScreen) {
                this@BubbleView.postInvalidate()
            } else {
                stopMovement(mFrame, bubbleCountTextView)
            }
        }, 0, REFRESH_RATE.toLong(), TimeUnit.MILLISECONDS)
    }

    // Returns true if the BubbleView intersects position (x,y)
    @Synchronized
    fun intersects(x: Float?, y: Float?): Boolean {
        val centerX = xPos + radius
        val centerY = yPos + radius

        // TODO
        //  Return true if the BubbleView intersects position (x,y)
        return sqrt((centerX - x!!).toDouble().pow(2.0) + (centerY - y!!).toDouble().pow(2.0)) <= radius
    }

    // Cancel the Bubble's movement
    // Remove Bubble from mmFrame
    // Play pop sound if the BubbleView was popped
    fun stopMovement(mFrame: RelativeLayout?, bubbleCountTextView: TextView?) {
        if (null != mMoverFuture) {
            if (!mMoverFuture!!.isDone) {
                mMoverFuture!!.cancel(true)
            }

            // This work will be performed on the UI Thread
            mFrame?.post {
                // TODO
                //  Remove the BubbleView from mFrame
                mFrame.removeView(this)

                // TODO
                //  Update the TextView displaying the number of bubbles
                updateNumBubblesTextView(mFrame, bubbleCountTextView)
//                val text = context.getString(R.string.txt_number_of_bubbles, mFrame.childCount)
//                bubbleCountTextView?.text = text

                // TODO
                //  If the bubble was popped by user play the popping sound
                //  HINT: Use the streamVolume for left and right volume parameters
                //                if (wasPopped) {
                //                    soundPool!!.play(soundID, streamVolume, streamVolume, 1, 0, 1.0f)
                //                }
            }
        }
    }

    private fun updateNumBubblesTextView(mFrame: RelativeLayout, bubbleCountTextView: TextView?) {
        val text = context.getString(R.string.txt_number_of_bubbles, mFrame.childCount)
        bubbleCountTextView!!.text = text
    }

    // Change the Bubble's speed and direction
    @Synchronized
    fun deflect(velocityX: Float, velocityY: Float) {
        mDx = velocityX / REFRESH_RATE
        mDy = velocityY / REFRESH_RATE
    }

    // Draw the Bubble at its current location
    @Synchronized
    override fun onDraw(canvas: Canvas) {
        // TODO
        //  Save the canvas
        canvas.save()

        // TODO
        //  Increase the rotation of the original image by mDRotate
        mRotate += mDRotate

        // TODO
        //  Rotate the canvas by current rotation
        //  Hint - Rotate around the bubble's center, not its position
        canvas.rotate(mRotate.toFloat(), xPos + radius, yPos + radius)

        // TODO
        //  Draw the bitmap at it's new location
        canvas.drawBitmap(scaledBitmap!!, xPos, yPos, mPainter)

        // TODO
        //  Restore the canvas
        canvas.restore()
    }

    // Returns true if the BubbleView is still on the screen after the move
    // operation
    @Synchronized
    private fun moveWhileOnScreen(): Boolean {
        // TODO
        //  Move the BubbleView
        xPos += mDx
        yPos += mDy

        return isInView()
    }

    private fun isInView(): Boolean {
        // TODO
        //  Return true if the BubbleView is still on the screen after
        //  the move operation
        return xPos <= mDisplayWidth &&
                xPos + radius * 2 >= 0 &&
                yPos <= mDisplayHeight &&
                yPos + radius * 2 >= 0
    }

    private fun generateRandomNumberInRange(min: Int, max: Int): Int {
        return (min..max).shuffled().first()
//        return ThreadLocalRandom.current().nextInt(min, max + 1)
    }

    companion object {
        private const val BITMAP_SIZE = 64
        private const val REFRESH_RATE = 40
    }

    init {
        // Creates the bubble bitmap for this BubbleView
        createScaledBitmap()

        // Radius of the Bitmap
        radius = (scaledBitmapSize / 2).toFloat()

        // Adjust position to center the bubble under user's finger
        xPos = x - radius
        yPos = y - radius

        // Set the BubbleView's speed and direction
        setSpeedAndDirection()

        // Set the BubbleView's rotation
        setRotation()
        mPainter.isAntiAlias = true
    }
}