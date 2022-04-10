package course.labs.graphicslab

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.math.sqrt
import kotlin.math.pow

class BubbleActivity : Activity() {
    // The Main view
    private var mFrame: RelativeLayout? = null

    // Bubble image's bitmap
    private var bitmap: Bitmap? = null

    // Display dimensions
    private var displayWidth = 0
    private var displayHeight = 0

    // Gesture Detector
    private var gestureDetector: GestureDetector? = null

    // A TextView to hold the current number of bubbles
    private var bubbleCountTextView: TextView? = null

    // Sound variables
    // SoundPool
    private var soundPool: SoundPool? = null

    // ID for the bubble popping sound
    private var soundID = 0

    // Audio volume
    private var streamVolume = 0f

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        // Set up user interface
        mFrame = findViewById(R.id.frame)
        bubbleCountTextView = findViewById(R.id.bubbles_text)

        // Initialize the number of bubbles
        updateNumBubblesTextView()

        // Load basic bubble Bitmap
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.b64)
    }

    override fun onResume() {
        super.onResume()
        setStreamVolume()

        // TODO
        //  Make a new SoundPool, allowing up to 10 streams
        //  Store this as mSoundPool
        createNewSoundPool()

        // TODO
        //  Set a SoundPool OnLoadCompletedListener that calls setupGestureDetector()
        soundPool!!.setOnLoadCompleteListener { _: SoundPool?, _: Int, status: Int ->
            if (0 == status) {
                setupGestureDetector()
            } else {
                Log.e(TAG, "Unable to load sound")
                finish()
            }
        }
        // TODO
        //  Load the sound from res/raw/bubble_pop.wav
        //  Store this as soundID
        soundID = soundPool!!.load(this, R.raw.bubble_pop, 1)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Get the size of the display so this View knows where borders are
            displayWidth = mFrame!!.width
            displayHeight = mFrame!!.height
        }
    }

    private fun updateNumBubblesTextView() {
        val text = getString(R.string.txt_number_of_bubbles, mFrame!!.childCount)
        bubbleCountTextView!!.text = text
    }

    // Setup the SoundPool instance
    private fun createNewSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(SOUND_POOL_MAX_STREAMS)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    // Setup the stream volume
    private fun setStreamVolume() {
        // Manage bubble popping sound
        // Use AudioManager.STREAM_MUSIC as stream type

        // AudioManager audio settings for adjusting the volume
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        // Current volume Index of particular stream type
        val currentVolumeIndex = audioManager.getStreamVolume(STREAM_TYPE)
            .toFloat()

        // Get the maximum volume index for a particular stream type
        val maxVolumeIndex = audioManager.getStreamMaxVolume(STREAM_TYPE)
            .toFloat()

        // Set the volume between 0 --> 1
        streamVolume = currentVolumeIndex / maxVolumeIndex
        volumeControlStream = STREAM_TYPE
    }

    // Set up GestureDetector
    private fun setupGestureDetector() {
        gestureDetector = GestureDetector(this,
            object : SimpleOnGestureListener() {
                override fun onDown(motionEvent: MotionEvent): Boolean {
                    return true
                }

                // If a fling gesture starts on a BubbleView then change the
                // BubbleView's velocity based on x and y velocity from
                // this gesture
                override fun onFling(
                    event1: MotionEvent, event2: MotionEvent,
                    velocityX: Float, velocityY: Float,
                ): Boolean {
                    // TODO
                    //  Implement onFling actions (See comment above for expected behaviour)
                    //  You can get all Views in mmFrame one at a time using the ViewGroup.getChildAt() method
                    val x = event1.x
                    val y = event1.y
                    val numBubbles = mFrame!!.childCount

                    for (i in 0 until numBubbles) {
                        val bubbleView = mFrame!!.getChildAt(i) as BubbleView
                        if (bubbleView.intersects(x, y)) {
                            bubbleView.deflect(velocityX, velocityY)
                        }
                    }

                    return true
                }

                // If a single tap intersects a BubbleView, then pop the BubbleView
                // Otherwise, create a new BubbleView at the tap's location and add
                // it to mFrame. Hint: Don't forget to start the movement of the
                // BubbleView.
                // Also update the number of bubbles displayed in the appropriate TextView
                override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
                    // TODO - Implement onSingleTapConfirmed actions.
                    //  (See comment above for expected behaviour.)
                    //  You can get all Views in mmFrame using the
                    //  ViewGroup.getChildCount() method
                    val x = event.x
                    val y = event.y
                    var bubblePopped = false

                    val numBubbles = mFrame!!.childCount
                    for (i in 0 until numBubbles) {
                        val bubbleView = mFrame!!.getChildAt(i) as BubbleView
                        if (bubbleView.intersects(x, y)) {
                            bubbleView.stopMovement(true)
                            bubblePopped = true
                        }
                    }

                    if (!bubblePopped) {
                        // Create a new bubble view instance
                        val context = mFrame!!.context
                        val newBubbleView = BubbleView(context, x, y)
                        mFrame!!.addView(newBubbleView)
                        newBubbleView.startMovement()
                        updateNumBubblesTextView()
                    }

                    return true
                }
            })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // TODO
        //  Delegate the touch to the gestureDetector
        return gestureDetector!!.onTouchEvent(event)
    }

    override fun onPause() {
        super.onPause()

        // TODO
        //  Release all SoundPool resources
        if (null != soundPool) {
            assert(false)
            soundPool!!.unload(soundID)
            soundPool!!.release()
            soundPool = null
        }
    }

    // BubbleView is a View that displays a bubble.
    // This class handles animating, drawing, and popping amongst other actions.
    // A new BubbleView is created for each bubble on the display
    inner class BubbleView internal constructor(context: Context?, x: Float, y: Float) :
        View(context) {
        private val mPainter = Paint()
        private var mMoverFuture: ScheduledFuture<*>? = null
        private var scaledBitmapSize = 0
        private var scaledBitmap: Bitmap? = null

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
        private fun setRotation(r: Random) {
            // TODO
            //  Set rotation in range [1..5]
            mDRotate = (r.nextInt(5) + 1).toLong()
        }

        private fun setSpeedAndDirection(r: Random) {
            // TODO
            //  Set dx and dy to indicate movement direction and speed
            //  Limit speed in the x and y direction to [-3..3] pixels per movement
            var x = r.nextInt(3) + 1
            if (r.nextBoolean()) {
                x = -x
            }

            var y = r.nextInt(3) + 1
            if (r.nextBoolean()) {
                y = -y
            }

            mDx = x.toFloat()
            mDy = y.toFloat()
        }

        private fun createScaledBitmap(r: Random) {
            // TODO
            //  Set scaled bitmap size (scaledBitmapSize) in range [2..4] * BITMAP_SIZE
            scaledBitmapSize = BITMAP_SIZE * (r.nextInt(3) + 2)

            // TODO
            //  Create the scaled bitmap (scaledBitmap) using size set above
            scaledBitmap = Bitmap.createScaledBitmap(bitmap!!, scaledBitmapSize, scaledBitmapSize, true)
        }

        // Start moving the BubbleView & updating the display
        fun startMovement() {
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
                    stopMovement(false)
                }
            }, 0, Companion.REFRESH_RATE.toLong(), TimeUnit.MILLISECONDS)
        }

        // Returns true if the BubbleView intersects position (x,y)
        @Synchronized
        fun intersects(x: Float, y: Float): Boolean {
            val centerX = xPos + radius
            val centerY = yPos + radius

            // TODO
            //  Return true if the BubbleView intersects position (x,y)
            return sqrt((centerX - x).toDouble().pow(2.0) + (centerY - y).toDouble().pow(2.0)) <= radius
        }

        // Cancel the Bubble's movement
        // Remove Bubble from mmFrame
        // Play pop sound if the BubbleView was popped
        fun stopMovement(wasPopped: Boolean) {
            if (null != mMoverFuture) {
                if (!mMoverFuture!!.isDone) {
                    mMoverFuture!!.cancel(true)
                }

                // This work will be performed on the UI Thread
                mFrame!!.post {
                    // TODO
                    //  Remove the BubbleView from mFrame
                    mFrame!!.removeView(this)

                    // TODO
                    //  Update the TextView displaying the number of bubbles
                    updateNumBubblesTextView()

                    // TODO
                    //  If the bubble was popped by user play the popping sound
                    //  HINT: Use the streamVolume for left and right volume parameters
                    if (wasPopped) {
                        soundPool!!.play(soundID, streamVolume, streamVolume, 1, 0, 1.0f)
                    }
                }
            }
        }

        // Change the Bubble's speed and direction
        @Synchronized
        fun deflect(velocityX: Float, velocityY: Float) {
            mDx = velocityX / Companion.REFRESH_RATE
            mDy = velocityY / Companion.REFRESH_RATE
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
            canvas.rotate(mDRotate.toFloat(), xPos + radius, yPos + radius)

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
            return xPos <= displayWidth &&
                    xPos + radius * 2 >= 0 &&
                    yPos <= displayHeight &&
                    yPos + radius * 2 >= 0
        }

        init {
            // Create a new random number generator to
            // randomize size, rotation, speed and direction
            val r = Random()

            // Creates the bubble bitmap for this BubbleView
            createScaledBitmap(r)

            // Radius of the Bitmap
            radius = (scaledBitmapSize / 2).toFloat()

            // Adjust position to center the bubble under user's finger
            xPos = x - radius
            yPos = y - radius

            // Set the BubbleView's speed and direction
            setSpeedAndDirection(r)

            // Set the BubbleView's rotation
            setRotation(r)
            mPainter.isAntiAlias = true
        }
    }

    companion object {
        private const val TAG = "BubbleActivity"
        private const val STREAM_TYPE = AudioManager.STREAM_MUSIC
        private const val SOUND_POOL_MAX_STREAMS = 10
        private const val BITMAP_SIZE = 64
        private const val REFRESH_RATE = 40
    }
}