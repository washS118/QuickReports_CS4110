package com.quickreports.Managers;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraManager {
    private static String LogTag = "QuickReports-CameraManager";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Fragment frag;
    private Context context;

    private String currentPhotoPath;


    /**
     * Initialize camera manager
     * @param fragment the fragment that is handling camera requests
     */
    public CameraManager(Fragment fragment){
        frag = fragment;
        context = frag.getContext();
    }

    /**
     * Creates take picture intent
     * @throws IOException
     */
    public void TakePicture() throws IOException{
        dispatchTakePictureIntent();
    }

    /**
     * Get the path to the last photo taken
     * @return path to an image
     */
    public String GetCurrentPhotoPath() {
        return currentPhotoPath;
    }

    /**
     * Get the request code of the intent
     * @return intent request code
     */
    public int GetRequestCode(){
        return REQUEST_IMAGE_CAPTURE;
    }

    private void dispatchTakePictureIntent() {
        Log.println(Log.DEBUG, LogTag, "Dispatch Take Picture");

        context = frag.getContext();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.quickreports",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                frag.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        Log.println(Log.DEBUG, LogTag, "Create Image File");
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * Add the picture to photo gallery
     */
    public void galleryAddPic() {
        Log.println(Log.DEBUG, LogTag, "Add To Gallery");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
