package mobiledev.unb.ca.threadinghandlermessages;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HandlerMessagesActivity extends Activity {
    final static int SET_PROGRESS_BAR_VISIBILITY = 0;
    final static int PROGRESS_UPDATE = 1;
    final static int SET_BITMAP = 2;

    private ImageView mImageView;
    private ProgressBar progressBar;
    private LoadIconTask loadIconTask;
    private final static String PROG_BAR_PROGRESS_KEY = "PROG_BAR_PROGRESS_KEY";
    private final static String PROG_BAR_VISIBLE_KEY = "PROG_BAR_VISIBLE_KEY";
    private final static String BITMAP_KEY = "BITMAP_KEY";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mImageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        if (null != savedInstanceState) {
            progressBar.setVisibility(savedInstanceState.getInt(PROG_BAR_VISIBLE_KEY));
            progressBar.setProgress(savedInstanceState.getInt(PROG_BAR_PROGRESS_KEY));
            mImageView.setImageBitmap((Bitmap) savedInstanceState.getParcelable(BITMAP_KEY));
            loadIconTask = ((LoadIconTask) getLastNonConfigurationInstance());
            if (null != loadIconTask) {
                loadIconTask.setProgressBar(progressBar)
                        .setImageView(mImageView);
            }
        }
    }

    public void onClickLoadButton(View v) {
        loadIconTask = new LoadIconTask(getApplicationContext())
                .setImageView(mImageView)
                .setProgressBar(progressBar);
        loadIconTask.start();
    }

    public void onClickOtherButton(View v) {
        Toast.makeText(HandlerMessagesActivity.this, "I'm Working",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PROG_BAR_VISIBLE_KEY, progressBar.getVisibility());
        outState.putInt(PROG_BAR_PROGRESS_KEY, progressBar.getProgress());

        if (null != mImageView.getDrawable()) {
            Bitmap bm = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
            outState.putParcelable(BITMAP_KEY, bm);
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return loadIconTask;
    }
}
