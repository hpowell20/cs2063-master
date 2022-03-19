package mobiledev.unb.ca.simplegestureviewdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GestureDetectorCompat mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        CustomTextView textView = findViewById(R.id.textView);
        textView.setDetectorObject(mDetector);
    }
}

