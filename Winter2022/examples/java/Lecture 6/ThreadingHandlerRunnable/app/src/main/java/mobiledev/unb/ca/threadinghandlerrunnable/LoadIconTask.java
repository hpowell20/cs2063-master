package mobiledev.unb.ca.threadinghandlerrunnable;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class LoadIconTask extends Thread {
    private final int mBitmapResID = R.drawable.painter;
    private final Handler mHandler;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private final Context mAppContext;

    LoadIconTask(Context context) {
        mAppContext = context;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public LoadIconTask setImageView(ImageView mImageView) {
        this.mImageView = mImageView;
        return this;
    }

    public LoadIconTask setProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
        return this;
    }

    public void run() {
        mHandler.post(() -> mProgressBar.setVisibility(ProgressBar.VISIBLE));

        // Simulating long-running operation
        for (int i = 1; i < 11; i++) {
            sleep();
            final int step = i;
            mHandler.post(() -> mProgressBar.setProgress(step * 10));
        }

        mHandler.post(() -> mImageView.setImageBitmap(
                BitmapFactory.decodeResource(mAppContext.getResources(), mBitmapResID)));

        mHandler.post(() -> mProgressBar.setVisibility(ProgressBar.INVISIBLE));
    }

    private void sleep() {
        try {
            int mDelay = 500;
            Thread.sleep(mDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
