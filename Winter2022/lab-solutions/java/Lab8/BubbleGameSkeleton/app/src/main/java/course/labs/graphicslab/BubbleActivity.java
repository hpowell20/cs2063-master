package course.labs.graphicslab;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BubbleActivity extends Activity {
    private static final String TAG = "BubbleActivity";
    private static final int SOUND_POOL_MAX_STREAMS = 10;
    private static final int STREAM_TYPE = AudioManager.STREAM_MUSIC;

    // The Main view
    private RelativeLayout mFrame;

    // Bubble image's bitmap
    private Bitmap mBitmap;

    // Display dimensions
    private int mDisplayWidth, mDisplayHeight;

    // Gesture Detector
    private GestureDetector gestureDetector;

    // A TextView to hold the current number of bubbles
    private TextView bubbleCountTextView;

    // Sound variables

    // SoundPool
    private SoundPool soundPool;

    // ID for the bubble popping sound
    private int soundID;

    // Audio volume
    private float streamVolume;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Set up user interface
        mFrame = findViewById(R.id.frame);
        bubbleCountTextView = findViewById(R.id.bubbles_text);

        // Initialize the number of bubbles
        updateNumBubblesTextView();

        // Load basic bubble Bitmap
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b64);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setStreamVolume();

        // TODO
        //  Make a new SoundPool, allowing up to 10 streams
        //  Store this as mSoundPool
        createNewSoundPool();

        // TODO
        //  Set a SoundPool OnLoadCompletedListener that calls setupGestureDetector()
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            if (0 == status) {
                setupGestureDetector();
            } else {
                Log.e(TAG, "Unable to load sound");
                finish();
            }
        });

        // TODO
        //  Load the sound from res/raw/bubble_pop.wav
        //  Store this as mSoundID
        soundID = soundPool.load(this, R.raw.bubble_pop, 1);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Get the size of the display so this View knows where borders are
            mDisplayWidth = mFrame.getWidth();
            mDisplayHeight = mFrame.getHeight();
        }
    }

    // Setup the stream volume
    private void setStreamVolume() {
        // AudioManager audio settings for adjusting the volume
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Current volume Index of particular stream type
        float currentVolumeIndex = (float) audioManager.getStreamVolume(STREAM_TYPE);

        // Get the maximum volume index for a particular stream type
        float maxVolumeIndex = (float) audioManager.getStreamMaxVolume(STREAM_TYPE);

        // Set the volume between 0 --> 1
        streamVolume = currentVolumeIndex / maxVolumeIndex;

        setVolumeControlStream(STREAM_TYPE);
    }

    // Setup the SoundPool instance
    private void createNewSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(SOUND_POOL_MAX_STREAMS)
                .setAudioAttributes(audioAttributes)
                .build();
    }

    // Set up GestureDetector
    private void setupGestureDetector() {
        gestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent motionEvent) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent event1, MotionEvent event2,
                                           float velocityX, float velocityY) {
                        // TODO
                        //  Implement onFling actions
                        //  Expected Behaviour:
                        //    If a fling gesture starts on a BubbleView then change the
                        //    BubbleView's velocity based on x and y velocity from
                        //    this gesture
                        //  HINT:
                        //   You can get all Views in mFrame one at a time
                        //   using the ViewGroup.getChildAt() method
                        float x = event1.getX();
                        float y = event1.getY();
                        int numBubbles = mFrame.getChildCount();

                        // Have a look into the methods available in the BubbleView class
                        for (int i = 0; i < numBubbles; i++) {
                            BubbleView bubbleView = (BubbleView) mFrame.getChildAt(i);
                            if (bubbleView.intersects(x, y)) {
                                bubbleView.deflect(velocityX, velocityY);
                            }
                        }

                        return true;
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent event) {
                        // TODO
                        //  Implement onSingleTapConfirmed actions.
                        //  Expected Behaviour:
                        //    If a single tap intersects a BubbleView, then pop the BubbleView
                        //    Otherwise, create a new BubbleView at the tap's location and add
                        //    it to mFrame.
                        //  HINT:
                        //    Don't forget to start the movement of the BubbleView.
                        //    Also update the number of bubbles displayed in the appropriate TextView
                        //    You can get all Views in mFrame
                        //    using the ViewGroup.getChildCount() method

                        float x = event.getX();
                        float y = event.getY();
                        boolean bubblePopped = false;

                        int numBubbles = mFrame.getChildCount();
                        for (int i = 0; i < numBubbles; i++) {
                            BubbleView bubbleView = (BubbleView) mFrame.getChildAt(i);
                            if (bubbleView.intersects(x, y)) {
                                bubbleView.stopMovement(true);
                                bubblePopped = true;
                            }
                        }

                        if (!bubblePopped) {
                            // Create a new bubble view instance
                            Context context = mFrame.getContext();
                            BubbleView newBubbleView = new BubbleView(context, x, y);
                            mFrame.addView(newBubbleView);
                            newBubbleView.startMovement();

                            updateNumBubblesTextView();
                        }

                        return true;
                    }
                });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO
        //  Delegate the touch to the gestureDetector
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // TODO
        //  Release all SoundPool resources
        if (null != soundPool) {
            soundPool.unload(soundID);
            soundPool.release();
            soundPool = null;
        }
    }

    private void updateNumBubblesTextView() {
        String text = getString(R.string.txt_number_of_bubbles, mFrame.getChildCount());
        bubbleCountTextView.setText(text);
    }

    // BubbleView is a View that displays a bubble.
    // This class handles animating, drawing, and popping amongst other actions.
    // A new BubbleView is created for each bubble on the display
    public class BubbleView extends View {
        private static final int BITMAP_SIZE = 64;
        private static final int REFRESH_RATE = 40;
        private final Paint mPainter = new Paint();
        private ScheduledFuture<?> mMoverFuture;
        private int scaledBitmapSize;
        private Bitmap mScaledBitmap;

        // location and direction of the bubble
        private float mXPos;
        private float mYPos;
        private final float mRadius;

        // Speed of bubble
        private float mDx, mDy;

        // Rotation and speed of rotation of the bubble
        private long mRotate, mDRotate;

        BubbleView(Context context, float x, float y) {
            super(context);

            // Create a new random number generator to
            // randomize size, rotation, speed and direction
            Random r = new Random();

            // Creates the bubble bitmap for this BubbleView
            createScaledBitmap(r);

            // Radius of the Bitmap
            mRadius = scaledBitmapSize / 2.0f;

            // Adjust position to center the bubble under user's finger
            mXPos = x - mRadius;
            mYPos = y - mRadius;

            // Set the BubbleView's speed and direction
            setSpeedAndDirection(r);

            // Set the BubbleView's rotation
            setRotation(r);

            mPainter.setAntiAlias(true);
        }

        private void setRotation(Random r) {
            // TODO
            //  Set rotation in range [1..5]
            mDRotate = r.nextInt(5) + 1;
        }

        private void setSpeedAndDirection(Random r) {
            // TODO
            //  Set dx and dy to indicate movement direction and speed
            //  Limit speed in the x and y direction to [-3..3] pixels per movement
            int x = r.nextInt(3) + 1;
            if (r.nextBoolean()) {
                x = -x;
            }

            int y = r.nextInt(3) + 1;
            if (r.nextBoolean()) {
                y = -y;
            }

            mDx = x;
            mDy = y;
        }

        private void createScaledBitmap(Random r) {
            // TODO
            //  Set scaled bitmap size (mScaledBitmapSize) in range [2..4] * BITMAP_SIZE
            scaledBitmapSize = BITMAP_SIZE * (r.nextInt(3) + 2);

            // TODO
            //  Create the scaled bitmap (mScaledBitmap) using size set above
            mScaledBitmap = Bitmap.createScaledBitmap(mBitmap, scaledBitmapSize, scaledBitmapSize, true);
        }

        // Start moving the BubbleView & updating the display
        private void startMovement() {
            // Creates a WorkerThread
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

            // Execute the run() in Worker Thread every REFRESH_RATE
            // milliseconds
            // Save reference to this job in mMoverFuture
            mMoverFuture = executor.scheduleWithFixedDelay(() -> {
                // TODO
                //  Implement movement logic.
                //  Each time this method is run the BubbleView should
                //  move one step. (Use moveWhileOnScreen() to do this.)
                //  If the BubbleView exits the display, stop the BubbleView's
                //  Worker Thread. (Use stopMovement() to do this.) Otherwise,
                //  request that the BubbleView be redrawn.
                boolean stillOnScreen = moveWhileOnScreen();
                if (stillOnScreen) {
                    BubbleView.this.postInvalidate();
                } else {
                    stopMovement(false);
                }
            }, 0, REFRESH_RATE, TimeUnit.MILLISECONDS);
        }

        // Returns true if the BubbleView intersects position (x,y)
        private synchronized boolean intersects(float x, float y) {
            float centerX = mXPos + mRadius;
            float centerY = mYPos + mRadius;

            // TODO
            //  Return true if the BubbleView intersects position (x,y)
            return Math.sqrt(Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2)) <= mRadius;
        }

        // Cancel the Bubble's movement
        // Remove Bubble from mFrame
        // Play pop sound if the BubbleView was popped
        private void stopMovement(final boolean wasPopped) {
            if (null != mMoverFuture) {
                if (!mMoverFuture.isDone()) {
                    mMoverFuture.cancel(true);
                }

                // This work will be performed on the UI Thread
                mFrame.post(() -> {
                    // TODO
                    //  Remove the BubbleView from mFrame
                    mFrame.removeView(BubbleView.this);

                    // TODO
                    //  Update the TextView displaying the number of bubbles
                    updateNumBubblesTextView();

                    // TODO
                    //  If the bubble was popped by user play the popping sound
                    //  HINT: Use the mStreamVolume for left and right volume parameters
                    if (wasPopped) {
                        soundPool.play(soundID, streamVolume, streamVolume, 1, 0, 1.0f);
                    }
                });
            }
        }

        // Change the Bubble's speed and direction
        private synchronized void deflect(float velocityX, float velocityY) {
            mDx = velocityX / REFRESH_RATE;
            mDy = velocityY / REFRESH_RATE;
        }

        // Draw the Bubble at its current location
        @Override
        protected synchronized void onDraw(Canvas canvas) {
            // TODO
            //  Save the canvas
            canvas.save();

            // TODO
            //  Increase the rotation of the original image by mDRotate
            mRotate += mDRotate;

            // TODO
            //  Rotate the canvas by current rotation
            //  Hint - Rotate around the bubble's center, not its position
            canvas.rotate(mRotate, mXPos + mRadius, mYPos + mRadius);

            // TODO
            //  Draw the bitmap at it's new location
            canvas.drawBitmap(mScaledBitmap, mXPos, mYPos, mPainter);

            // TODO
            //  Restore the canvas
            canvas.restore();
        }

        // Returns true if the BubbleView is still on the screen after the move
        // operation
        private synchronized boolean moveWhileOnScreen() {
            // TODO
            //  Move the BubbleView
            mXPos += mDx;
            mYPos += mDy;

            return isInView();
        }

        // Return true if the BubbleView is still on the screen after the move
        // operation
        private boolean isInView() {
            // TODO
            //  Return true if the BubbleView is still on the screen after
            //  the move operation
            return mXPos <= mDisplayWidth &&
                    mXPos + mRadius * 2 >= 0 &&
                    mYPos <= mDisplayHeight &&
                    mYPos + mRadius * 2 >= 0;
        }
    }
}
