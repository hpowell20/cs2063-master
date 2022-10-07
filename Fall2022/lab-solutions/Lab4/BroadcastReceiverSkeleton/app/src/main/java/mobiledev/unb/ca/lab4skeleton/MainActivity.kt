package mobiledev.unb.ca.lab4skeleton

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // Attributes for working with an alarm
    private var alarmManager: AlarmManager? = null
    private lateinit var alarmReceiverIntent: PendingIntent

    // Attributes for storing the file photo path
    private lateinit var currentPhotoPath: String
    private lateinit var imageFileName: String

    // Activity listeners
    private var cameraActivityResultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val cameraButton = findViewById<Button>(R.id.button)
        cameraButton.setOnClickListener { dispatchTakePhotoIntent() }

        // Register the activity listener
        setCameraActivityResultLauncher()

        // Create the notification channel
        createNotificationChannel(applicationContext, Constants.NOTIFICATION_CHANNEL_ID)

        // Set the broadcast receiver alarm values
        initAlarmValues()

        // Set the battery filter intents
        setBatteryIntentFilters()
   }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the battery receivers to avoid memory leaks
        removeBatteryIntentFilters()
    }

    // Notification methods
    private fun createNotificationChannel(context: Context, channelId: String) {
        // Only create the NotificationChannel if the using API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Set the user visible channel name
            val name: CharSequence = context.getString(R.string.channel_name)

            // Set the user visible channel description
            val description = context.getString(R.string.channel_description)

            // Set the channel importance
            val importance = NotificationManager.IMPORTANCE_HIGH

            // Create the NotificationChannel object
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.enableLights(true)

            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            channel.lightColor = Color.GREEN
            channel.enableVibration(true)

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Battery check methods
    private val batteryInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val intentAction = intent.action
            if (Intent.ACTION_BATTERY_OKAY == intentAction) {
                startAlarm()
                Toast.makeText(
                    this@MainActivity,
                    "Battery level good; starting the alarm",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (Intent.ACTION_BATTERY_LOW == intentAction) {
                cancelAlarm()
                Toast.makeText(
                    this@MainActivity,
                    "Battery level low; cancelling the alarm",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Power Check Methods
    private val powerInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val intentAction = intent.action
            if (Intent.ACTION_POWER_CONNECTED == intentAction) {
                startAlarm()
                Toast.makeText(
                    this@MainActivity,
                    "Device plugged in; starting the alarm",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (Intent.ACTION_POWER_DISCONNECTED == intentAction) {
                cancelAlarm()
                Toast.makeText(
                    this@MainActivity,
                    "Device unplugged; cancelling the alarm",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setBatteryIntentFilters() {
        val batteryIntentFilter = IntentFilter()
        batteryIntentFilter.addAction(Intent.ACTION_BATTERY_OKAY)
        batteryIntentFilter.addAction(Intent.ACTION_BATTERY_LOW)
        registerReceiver(batteryInfoReceiver, batteryIntentFilter)

        val powerIntentFilter = IntentFilter()
        powerIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        powerIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        registerReceiver(powerInfoReceiver, powerIntentFilter)
    }

    private fun removeBatteryIntentFilters() {
        unregisterReceiver(batteryInfoReceiver)
        unregisterReceiver(powerInfoReceiver)
    }

    // Alarm Methods
    private fun initAlarmValues() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmReceiverIntent = Intent(this@MainActivity, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                this@MainActivity,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        // Start the alarm
        startAlarm()
    }

    private fun startAlarm() {
        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            SystemClock.elapsedRealtime() + INTERVAL_SIXTY_SECONDS,
            INTERVAL_SIXTY_SECONDS.toLong(),
            alarmReceiverIntent
        )
        Log.i(TAG, "Alarm Started")
    }

    private fun cancelAlarm() {
        // ? is for safe, !! is a non null assertion against a nullable receiver
        alarmManager?.cancel(alarmReceiverIntent)
        Log.i(TAG, "Alarm Cancelled")
    }

    // Camera methods
    private fun setCameraActivityResultLauncher() {
        cameraActivityResultLauncher = registerForActivityResult(
            StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                galleryAddPic()
            }
        }
    }

    private fun dispatchTakePhotoIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there is a camera activity to handle the intent
            try {
                // Set the File object used to save the photo
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.e(TAG, "Exception found when creating the photo save file")
                }

                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                        this,
                        "mobiledev.unb.ca.lab4skeleton.provider",
                        photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                    // Calling this method allows us to capture the return code
                    cameraActivityResultLauncher!!.launch(takePictureIntent)
                }
            } catch (ex: ActivityNotFoundException) {
                Log.e(TAG, "Unable to load activity", ex)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat(TIME_STAMP_FORMAT, Locale.getDefault()).format(Date())
        imageFileName = "IMG_" + timeStamp + "_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName,  // prefix
            ".jpg",  // suffix
            storageDir // directory
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun galleryAddPic() {
        Log.d(TAG, "Saving image to the gallery")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 and above
            mediaStoreAddPicToGallery()
        } else {
            // Pre Android 10
            mediaScannerAddPicToGallery()
        }
        Log.i(TAG, "Image saved!")
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun mediaStoreAddPicToGallery() {
        val name = imageFileName
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath)

        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

        val resolver = contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        try {
            resolver.openOutputStream(imageUri!!).use { fos ->
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    fos
                )
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error saving the file ", e)
        }
    }

    private fun mediaScannerAddPicToGallery() {
        val file = File(currentPhotoPath)
        MediaScannerConnection.scanFile(this@MainActivity,
            arrayOf(file.toString()),
            arrayOf(file.name),
            null)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss"
        private const val INTERVAL_SIXTY_SECONDS = 60 * 1000
    }
}