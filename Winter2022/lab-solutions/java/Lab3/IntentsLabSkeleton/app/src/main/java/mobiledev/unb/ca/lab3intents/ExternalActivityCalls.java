package mobiledev.unb.ca.lab3intents;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ExternalActivityCalls extends AppCompatActivity {
    private static final String TAG = "External Activity Calls";

    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final String TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss";

    private static final String[] RECIPIENTS = {"hpowell@unb.ca"};
    private static final String EMAIL_SUBJECT = "CS2063 Lab 3";
    private static final String EMAIL_BODY = "This is a test email!";

    // Attributes for storing the file photo path
    private String currentPhotoPath;
    private String imageFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.external_activity_calls);

        // Set the buttons for use
        Button cameraButton = findViewById(R.id.btnCamera);
        cameraButton.setOnClickListener(v -> dispatchTakePhotoIntent());

        Button emailButton = findViewById(R.id.btnEmail);
        emailButton.setOnClickListener(v -> dispatchSendEmailIntent());

        Button backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(v -> {
            // This will kill the activity on the backstack
            ExternalActivityCalls.this.finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to and make sure the request was successful
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            galleryAddPic();
        }
    }

    // Private helper functions
    private void dispatchTakePhotoIntent() {
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
                        "mobiledev.unb.ca.lab3intents.provider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Calling this method allows us to capture the return code
                startActivityForResult(takePictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat(TIME_STAMP_FORMAT, Locale.getDefault()).format(new Date());
        imageFileName = "IMG_" + timeStamp + "_";

        File storageDir =  getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
        Log.d(TAG, "Saving image to the gallery");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 and above
            mediaStoreAddPicToGallery();
        } else {
            // Pre Android 10
            mediaScannerAddPicToGallery();
        }
        Log.i(TAG, "Image saved!");
    }

    private void mediaStoreAddPicToGallery() {
        String name = imageFileName;
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name + ".jpg");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        ContentResolver resolver = getContentResolver();
        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        try (OutputStream fos = resolver.openOutputStream(Objects.requireNonNull(imageUri))) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            //Objects.requireNonNull(fos).close();
        } catch (IOException e){
            Log.e(TAG,"Error saving the file ", e);
        }
    }

    private void mediaScannerAddPicToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void dispatchSendEmailIntent() {
        // Create an implicit intent to send an email
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
        //NOTE: ACTION_SEND is used when including an attachment
        mailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        mailIntent.putExtra(Intent.EXTRA_EMAIL, ExternalActivityCalls.RECIPIENTS);
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, ExternalActivityCalls.EMAIL_SUBJECT);
        mailIntent.putExtra(Intent.EXTRA_TEXT, ExternalActivityCalls.EMAIL_BODY);

        // Ensure that there is an email activity to handle the intent
        if (mailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mailIntent);
            //TODO: This line forces an app chooser
            //startActivity(Intent.createChooser(mailIntent, "Choose an email client from..."));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_two, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
