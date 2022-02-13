package mobiledev.unb.ca.threadingviewpostdelayed;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SimpleThreadingViewPostDelayedActivity extends Activity {

    private ImageView mImageView;
    private final int LOAD_DELAY = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.imageView);
    }

    public void onClickOtherButton(View v) {
        Toast.makeText(SimpleThreadingViewPostDelayedActivity.this, "I'm Working",
                Toast.LENGTH_SHORT).show();
    }

    public void onClickLoadButton(final View view) {
        view.setEnabled(false);
        new Thread(() -> {
            // Using View.postDelayed() to access the thread
            mImageView.postDelayed(() -> mImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.painter)), LOAD_DELAY);
        }).start();
    }
}
