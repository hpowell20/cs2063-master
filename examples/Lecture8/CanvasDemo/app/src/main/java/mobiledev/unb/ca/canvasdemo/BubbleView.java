package mobiledev.unb.ca.canvasdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class BubbleView extends View {
    private static final String TAG = "BubbleView";
    private static final int BITMAP_SIZE = 64;
    private static final int MAX_STEP = 10;

    // Display dimensions
    private int mDisplayWidth;
    private int mDisplayHeight;

    final private Bitmap mBitmap;

    private float mCurrX;
    private float mCurrY;
    private int mStepX;
    private int mStepY;
    private Handler h;

    final private Paint mPainter = new Paint();

    public BubbleView(Context context, Bitmap bitmap, int displayWidth, int displayHeight) {
        super(context);

        h = new Handler();
        this.mBitmap = Bitmap.createScaledBitmap(bitmap,
                BITMAP_SIZE, BITMAP_SIZE, false);

        mDisplayWidth = displayWidth;
        mDisplayHeight = displayHeight;

        // Start in roughly the centre
        mCurrX = mDisplayWidth / 2;
        mCurrY = mDisplayHeight / 2;

        Random r = new Random();
        // Pick a random x and y step
        mStepX = r.nextInt(MAX_STEP) + 1;
        mStepY = r.nextInt(MAX_STEP) + 1;
        // Pick a random x and y direction to step
        mStepX = r.nextBoolean() ? mStepX : -mStepX;
        mStepY = r.nextBoolean() ? mStepY : -mStepY;

        mPainter.setAntiAlias(true);
    }

    protected void onDraw(Canvas canvas) {
        move();
        canvas.drawBitmap(mBitmap, mCurrX, mCurrY, mPainter);
        h.postDelayed(r, 30);
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Log.i(TAG, "InterruptedException");
            }
        }
    };

    private boolean move() {
        mCurrX = mCurrX + mStepX;
        mCurrY = mCurrY + mStepY;

        // Return true if the BubbleView is on the screen
        return mCurrX <= mDisplayWidth &&
                mCurrX + BITMAP_SIZE >= 0 &&
                mCurrY <= mDisplayHeight &&
                mCurrY + BITMAP_SIZE >= 0;
    }
}
