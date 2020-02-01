package mobiledev.unb.ca.lab4skeleton;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final String TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss";
    private static final int INTERVAL_SIXTY_SECONDS = 60 * 1000;

    private String currentPhotoPath;
    private AlarmManager alarmManager;
    private PendingIntent alarmReceiverIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the camera button intent action
        Button cameraButton = findViewById(R.id.button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        // Set the broadcast receiver alarm action
        initAlarmValues();
        startAlarm();

        // Set the battery filter intent
        setBatteryIntentFilter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the battery receiver to avoid memory leaks
        unregisterReceiver(batteryInfoReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to and ensure the request was successful
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
                galleryAddPic();
        }
    }

    // Battery Check Methods
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (Intent.ACTION_BATTERY_OKAY.equals(intentAction)) {
                Log.i(TAG, "Battery level good; starting the alarm");
                startAlarm();
            }

            if (Intent.ACTION_BATTERY_LOW.equals(intentAction)) {
                Log.i(TAG, "Battery level low; cancelling the alarm");
                cancelAlarm();
            }
        }
    };

    private void setBatteryIntentFilter() {
        IntentFilter batteryIntentFilter = new IntentFilter();
        batteryIntentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        batteryIntentFilter.addAction(Intent.ACTION_BATTERY_LOW);

        getApplicationContext().registerReceiver(batteryInfoReceiver, batteryIntentFilter);
    }

    // Alarm Methods
    private void initAlarmValues() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent setAlarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        alarmReceiverIntent = PendingIntent.getBroadcast(MainActivity.this,
                0,
                setAlarmIntent,
                0);
    }

    private void startAlarm() {
        /*AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent setAlarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this,
                0,
                setAlarmIntent,
                0);*/

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() + INTERVAL_SIXTY_SECONDS,
                INTERVAL_SIXTY_SECONDS,
                alarmReceiverIntent);

        Toast.makeText(MainActivity.this, "Alarm started", Toast.LENGTH_SHORT).show();
    }

    private void cancelAlarm() {
        /*AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent cancelAlarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this,
                0,
                cancelAlarmIntent,
                0);*/

        alarmManager.cancel(alarmReceiverIntent);
        Toast.makeText(MainActivity.this, "Alarm cancelled", Toast.LENGTH_LONG).show();
    }

    // Camera Methods
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there is a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Set the File object used to save the photo
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, "Exception found when creating the photo save file");
            }

            // Take the picture if the File object was created successfully
            if (null != photoFile) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "mobiledev.unb.ca.lab4skeleton.provider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Calling this method allows us to capture the return code
                startActivityForResult(takePictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat(TIME_STAMP_FORMAT).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",   // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}
