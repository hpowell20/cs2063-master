package mobiledev.unb.ca.canvasdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrame = findViewById(R.id.frame);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // Set the bubble image from the drawable resource
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b64);

        // Start the animation with 5 balls
        for (int i = 0; i < 5; i++) {
            final BubbleView bubbleView = new BubbleView(getApplicationContext(),
                    bitmap,
                    mFrame.getWidth(),
                    mFrame.getHeight());
            mFrame.addView(bubbleView);
        }
    }
}
