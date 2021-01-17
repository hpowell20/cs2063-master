/*
 * When "Load Icon" Button is pressed throws 
 * android.view.ViewRootImpl$CalledFromWrongThreadException: 
 * Only the original thread that created a view hierarchy can touch its views.
 */

package mobiledev.unb.ca.threadingsimple;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SimpleThreadingExample extends Activity {

    private static final String TAG = "SimpleThreadingExample";

    private ImageView mIView;
    private final int mDelay = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mIView = findViewById(R.id.imageView);
    }

    public void onClickLoadButton(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(mDelay);
                } catch (InterruptedException e) {
                    Log.e(TAG, e.toString());
                }

                // This doesn't work in Android
                mIView.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.painter));
            }
        }).start();
    }


    public void onClickOtherButton(View v) {
        Toast.makeText(SimpleThreadingExample.this, "I'm Working",
                Toast.LENGTH_SHORT).show();
    }
}