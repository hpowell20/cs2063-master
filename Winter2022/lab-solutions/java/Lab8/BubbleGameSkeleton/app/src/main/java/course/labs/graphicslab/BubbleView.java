//package course.labs.graphicslab;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.view.View;
//import android.widget.RelativeLayout;
//
//import java.util.Random;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.TimeUnit;
//
//// BubbleView is a View that displays a bubble.
//// This class handles animating, drawing, and popping amongst other actions.
//// A new BubbleView is created for each bubble on the display
//public class BubbleView extends View {
//    private static final int BITMAP_SIZE = 64;
//    private static final int REFRESH_RATE = 40;
//    private final Paint mPainter = new Paint();
//    private ScheduledFuture<?> mMoverFuture;
//    private int scaledBitmapSize;
//    private Bitmap scaledBitmap;
//
//    // location and direction of the bubble
//    private float xPos, yPos, radius;
//
//    // Speed of bubble
//    private float mDx, mDy;
//
//    // Rotation and speed of rotation of the bubble
//    private long mRotate, mDRotate;
//
//    private RelativeLayout mFrame;
//
//    BubbleView(Context context, float x, float y) {
//        super(context);
//
//        // Create a new random number generator to
//        // randomize size, rotation, speed and direction
//        Random r = new Random();
//
//        // Creates the bubble bitmap for this BubbleView
//        createScaledBitmap(r);
//
//        // Radius of the Bitmap
//        radius = scaledBitmapSize / 2;
//
//        // Adjust position to center the bubble under user's finger
//        xPos = x - radius;
//        yPos = y - radius;
//
//        // Set the BubbleView's speed and direction
//        setSpeedAndDirection(r);
//
//        // Set the BubbleView's rotation
//        setRotation(r);
//
//        mPainter.setAntiAlias(true);
//    }
//
//    private void setRotation(Random r) {
//        // TODO
//        //  Set rotation in range [1..5]
//        mDRotate = r.nextInt(5) + 1;
//    }
//
//    private void setSpeedAndDirection(Random r) {
//        // TODO
//        //  Set dx and dy to indicate movement direction and speed
//        //  Limit speed in the x and y direction to [-3..3] pixels per movement
//        int x = r.nextInt(3) + 1;
//        if (r.nextBoolean()) {
//            x = -x;
//        }
//
//        int y = r.nextInt(3) + 1;
//        if (r.nextBoolean()) {
//            y = -y;
//        }
//
//        mDx = x;
//        mDy = y;
//    }
//
//    private void createScaledBitmap(Random r) {
//        // TODO
//        //  Set scaled bitmap size (mScaledBitmapSize) in range [2..4] * BITMAP_SIZE
//        scaledBitmapSize = BITMAP_SIZE * (r.nextInt(3) + 2);
//
//        // TODO
//        //  Create the scaled bitmap (mScaledBitmap) using size set above
//        scaledBitmap = Bitmap.createScaledBitmap(mBitmap, scaledBitmapSize, scaledBitmapSize, true);
//    }
//
//    // Start moving the BubbleView & updating the display
//    private void startMovement() {
//        // Creates a WorkerThread
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//
//        // Execute the run() in Worker Thread every REFRESH_RATE
//        // milliseconds
//        // Save reference to this job in mMoverFuture
//        mMoverFuture = executor.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                // TODO
//                //  Implement movement logic.
//                //  Each time this method is run the BubbleView should
//                //  move one step. (Use moveWhileOnScreen() to do this.)
//                //  If the BubbleView exits the display, stop the BubbleView's
//                //  Worker Thread. (Use stopMovement() to do this.) Otherwise,
//                //  request that the BubbleView be redrawn.
//                boolean stillOnScreen = moveWhileOnScreen();
//                if (stillOnScreen) {
//                    BubbleView.this.postInvalidate();
//                } else {
//                    stopMovement(false);
//                }
//            }
//        }, 0, REFRESH_RATE, TimeUnit.MILLISECONDS);
//    }
//
//    // Returns true if the BubbleView intersects position (x,y)
//    private synchronized boolean intersects(float x, float y) {
//        float centerX = xPos + radius;
//        float centerY = yPos + radius;
//
//        // TODO
//        //  Return true if the BubbleView intersects position (x,y)
//        return Math.sqrt(Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2)) <= radius;
//
//        // Remove this when you're done the above todo
//        //return false;
//    }
//
//    // Cancel the Bubble's movement
//    // Remove Bubble from mmFrame
//    // Play pop sound if the BubbleView was popped
//    private void stopMovement(final boolean wasPopped) {
//        if (null != mMoverFuture) {
//            if (!mMoverFuture.isDone()) {
//                mMoverFuture.cancel(true);
//            }
//
//            // This work will be performed on the UI Thread
//            mFrame.post(() -> {
//                // TODO
//                //  Remove the BubbleView from mFrame
//                mFrame.removeView(BubbleView.this);
//
//                // TODO
//                //  Update the TextView displaying the number of bubbles
//                updateNumBubblesTextView();
//
//                // TODO
//                //  If the bubble was popped by user play the popping sound
//                //  HINT: Use the mStreamVolume for left and right volume parameters
//                if (wasPopped) {
//                    //mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
//                    soundPool.play(soundID, 1, 1, 1, 0, 1.0f);
//                }
//            });
//        }
//    }
//
//    // Change the Bubble's speed and direction
//    private synchronized void deflect(float velocityX, float velocityY) {
//        mDx = velocityX / REFRESH_RATE;
//        mDy = velocityY / REFRESH_RATE;
//    }
//
//    // Draw the Bubble at its current location
//    @Override
//    protected synchronized void onDraw(Canvas canvas) {
//        // TODO
//        //  Save the canvas
//        canvas.save();
//
//        // TODO
//        //  Increase the rotation of the original image by mDRotate
//        mRotate += mDRotate;
//
//        // TODO
//        //  Rotate the canvas by current rotation
//        //  Hint - Rotate around the bubble's center, not its position
//        canvas.rotate(mRotate, mXPos + mRadius, mYPos + mRadius);
//
//        // TODO
//        //  Draw the bitmap at it's new location
//        canvas.drawBitmap(mScaledBitmap, mXPos, mYPos, mPainter);
//
//        // TODO
//        //  Restore the canvas
//        canvas.restore();
//    }
//
//    // Returns true if the BubbleView is still on the screen after the move
//    // operation
//    private synchronized boolean moveWhileOnScreen() {
//        // TODO
//        //  Move the BubbleView
//        mXPos += mDx;
//        mYPos += mDy;
//
//        return isInView();
//    }
//
//    // Return true if the BubbleView is still on the screen after the move
//    // operation
//    private boolean isInView() {
//        // TODO
//        //  Return true if the BubbleView is still on the screen after
//        //  the move operation
//        return mXPos <= mDisplayWidth &&
//                mXPos + mRadius * 2 >= 0 &&
//                mYPos <= mDisplayHeight &&
//                mYPos + mRadius * 2 >= 0;
//
//        // Remove this when you're done the above TODO
//        // return false;
//    }
//}
