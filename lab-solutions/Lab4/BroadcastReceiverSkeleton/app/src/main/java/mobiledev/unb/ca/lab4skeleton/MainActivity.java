package mobiledev.unb.ca.lab4skeleton;

import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

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

        // Start the alarm
        startAlarm();

        // Set the battery filter intent
        setBatteryIntentFilters();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the battery receivers to avoid memory leaks
        removeBatteryIntentFilters();
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
                startAlarm();
                Toast.makeText(MainActivity.this,
                        "Battery level good; starting the alarm",
                        Toast.LENGTH_SHORT).show();
            }

            if (Intent.ACTION_BATTERY_LOW.equals(intentAction)) {
                cancelAlarm();
                Toast.makeText(MainActivity.this,
                        "Battery level low; cancelling the alarm",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    // Power Check Methods
    private BroadcastReceiver powerInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (Intent.ACTION_POWER_CONNECTED.equals(intentAction)) {
                startAlarm();
                Toast.makeText(MainActivity.this,
                        "Device plugged in; starting the alarm",
                        Toast.LENGTH_SHORT).show();
            }

            if (Intent.ACTION_POWER_DISCONNECTED.equals(intentAction)) {
                cancelAlarm();
                Toast.makeText(MainActivity.this,
                        "Device unplugged; cancelling the alarm",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setBatteryIntentFilters() {
        IntentFilter batteryIntentFilter = new IntentFilter();
        batteryIntentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        batteryIntentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        registerReceiver(batteryInfoReceiver, batteryIntentFilter);

        IntentFilter powerIntentFilter = new IntentFilter();
        powerIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        powerIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(powerInfoReceiver, powerIntentFilter);
    }

    private void removeBatteryIntentFilters() {
        unregisterReceiver(batteryInfoReceiver);
        unregisterReceiver(powerInfoReceiver);
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
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() + INTERVAL_SIXTY_SECONDS,
                INTERVAL_SIXTY_SECONDS,
                alarmReceiverIntent);

        Log.i(TAG, "Alarm Started");
    }

    private void cancelAlarm() {
        alarmManager.cancel(alarmReceiverIntent);
        Log.i(TAG, "Alarm Cancelled");
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
