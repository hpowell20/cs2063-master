package mobiledev.unb.ca.lab3intents;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExternalActivityCalls extends Activity {
    private static final String TAG = "External Activity Calls";

    private static final int REQUEST_CAPTURE_IMAGE = 100;

    private static final String[] RECIPIENTS = {"hpowell@unb.ca"};
    private static final String EMAIL_SUBJECT = "CS2063 Lab 3";
    private static final String EMAIL_BODY = "This is a test email!";

    private Button backButton;
    private Button cameraButton;
    private Button emailButton;

    // Attribute for storing the file photo path
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.external_activity_calls);

        // Setup the buttons
        backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent backIntent = new Intent(ExternalActivityCalls.this, MainActivity.class);
                if (backIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(backIntent);
                }*/

                // This will kill the activity on the backstack
                ExternalActivityCalls.this.finish();
            }
        });

        cameraButton = findViewById(R.id.btnCamera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePhotoIntent();
            }
        });

        emailButton = findViewById(R.id.btnEmail);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchSendEmailIntent(RECIPIENTS, EMAIL_SUBJECT, EMAIL_BODY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to and make sure the request was successful
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            galleryAddPic();
        }
    }

    // Private Helper Methods
    // Additional information can be found at
    // https://android.jlelse.eu/androids-new-image-capture-from-a-camera-using-file-provider-dd178519a954
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

    /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            ContentResolver resolver = getContext().getContentResolver();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "temp");
                            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "TEMP");
                            Uri imageUri123 = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    imageUri123);
                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);


                        }else {
                            File photo = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Build.VERSION.SDK_INT >= Build.VERSION_CODES.M?getOutputMediaFileUri(getActivity(), photo):Uri.fromFile(photo));
                            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                        }*/

    // This follows the latest version on the documentation; an alternate implementation
    // can be found at https://web.archive.org/web/20150207090211/ or
    // https://developer.android.com/training/camera/photobasics.html
    // if needed.  Not recommended however.
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";

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

    /*private Uri saveImage(Context context, Bitmap bitmap, @NonNull String folderName, @NonNull String fileName) throws IOException
    {
        OutputStream fos;
        File imageFile = null;
        Uri imageUri = null;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + File.separator + folderName);
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(imageUri);
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString() + File.separator + folderName;
            imageFile = new File(imagesDir);
            if (!imageFile.exists()) {
                imageFile.mkdir();
            }
            imageFile = new File(imagesDir, fileName + ".png");
            fos = new FileOutputStream(imageFile);
        }

        boolean saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();

        if (imageFile != null)  // pre Q
        {
            MediaScannerConnection.scanFile(context, new String[]{imageFile.toString()}, null, null);
            imageUri = Uri.fromFile(imageFile);
        }

        return imageUri;
    }*/

    private void galleryAddPic() {
        // Deprecated - Pre API level 29
        /*Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);*/

        // Post API level 29
        File file = new File(currentPhotoPath);
        MediaScannerConnection.scanFile(this,
                new String[]{file.toString()},
                new String[]{file.getName()},null);
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

    private void dispatchSendEmailIntent(String[] recipients, String subject, String body) {
        // Create an implicit intent to send an email
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
        //NOTE: ACTION_SEND is used when including an attachment
        mailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        mailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT, body);

        // Ensure that there is an email activity to handle the intent
        if (mailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mailIntent);
            //TODO: This line forces an app chooser
            //startActivity(Intent.createChooser(mailIntent, "Choose an email client from..."));
        }
    }
}
