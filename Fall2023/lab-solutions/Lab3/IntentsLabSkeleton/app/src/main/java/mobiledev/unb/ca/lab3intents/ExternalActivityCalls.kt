package mobiledev.unb.ca.lab3intents

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExternalActivityCalls : AppCompatActivity() {
    // Attributes for storing the file photo path
    private lateinit var currentPhotoPath: String
    private lateinit var imageFileName: String

    // Activity listeners
    private var cameraActivityResultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.external_activity_calls)

        // Define the buttons
        val cameraButton = findViewById<Button>(R.id.btnCamera)
        cameraButton.setOnClickListener {
            dispatchTakePhotoIntent()
        }

        val emailButton = findViewById<Button>(R.id.btnEmail)
        emailButton.setOnClickListener {
            dispatchSendEmailIntent(RECIPIENTS, EMAIL_SUBJECT, EMAIL_BODY)
        }

        val backButton = findViewById<Button>(R.id.btnBack)
        backButton.setOnClickListener {
            finish()
        }

        // Register the activity listener
        setCameraActivityResultLauncher()
    }

    private fun setCameraActivityResultLauncher() {
        // Handle the image capture result
        cameraActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                galleryAddPic()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_external_activity_calls, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    // Private helper functions
    private fun dispatchSendEmailIntent(recipients: Array<String>, subject: String, body: String) {
        val mailIntent = Intent(Intent.ACTION_SENDTO).apply {
            //NOTE: ACTION_SEND is used when including an attachment
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, recipients)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        try {
            // startActivity(Intent.createChooser(mailIntent, "Choose an email client from..."))
            startActivity(mailIntent)
            finish()
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "Unable to load mail activity", e)
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
                        "mobiledev.unb.ca.lab3intents.provider",
                        photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                    // Calling this method allows us to capture the return code
                    cameraActivityResultLauncher!!.launch(takePictureIntent)
                }
            } catch (ex: ActivityNotFoundException) {
                Log.e(TAG, "Unable to load photo activity", ex)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat(TIME_STAMP_FORMAT, Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        imageFileName = "IMG_" + timeStamp + "_"

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
            // Android 10 (API 29) and above
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

        val contentValues = getContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        contentValues.put(MediaStore.Images.Media.IS_PENDING, true)

        val resolver = contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (imageUri != null) {
            saveImageToStream(bitmap, resolver.openOutputStream(imageUri))
            contentValues.put(MediaStore.Images.Media.IS_PENDING, false)
            resolver.update(imageUri, contentValues, null, null)
        }
    }

    private fun getContentValues() : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        try {
            outputStream?.let {
                bitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    it
                )
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error saving the file ", e)
        }
    }

    private fun mediaScannerAddPicToGallery() {
        val file = File(currentPhotoPath)
        MediaScannerConnection.scanFile(this@ExternalActivityCalls,
            arrayOf(file.toString()),
            arrayOf(file.name),
            null)
    }

    companion object {
        // String for LogCat documentation
        private const val TAG = "External Activity Calls"

        private const val TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss"
        private val RECIPIENTS = arrayOf("hpowell@unb.ca")
        private const val EMAIL_SUBJECT = "CS2063 Lab 3"
        private const val EMAIL_BODY = "This is a test email!"
    }
}