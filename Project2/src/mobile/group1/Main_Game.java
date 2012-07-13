package mobile.group1;

import java.io.File;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Main_Game extends Activity {
	
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static Uri fileUri;
	Intent picIntent;
	ImageView mainimage;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_game);
	    
	    mainimage = (ImageView) findViewById(R.id.mainImage);
	    // create Intent to take a picture and return control to the calling application
	    picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	    picIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
	    
	}
public void takePicture(View v){
    	
    	Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
    	startActivityForResult(picIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    	
    	
    }
public static Uri getOutputMediaFileUri(int type){
    return Uri.fromFile(getOutputMediaFile(type));
}
public static File getOutputMediaFile(int type){
    // To be safe, you should check that the SDCard is mounted
    // using Environment.getExternalStorageState() before doing this.

    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
              Environment.DIRECTORY_PICTURES), "MyCameraApp");
    // This location works best if you want the created images to be shared
    // between applications and persist after your app has been uninstalled.

    // Create the storage directory if it does not exist
    if (! mediaStorageDir.exists()){
        if (! mediaStorageDir.mkdirs()){
            Log.d("MyCameraApp", "failed to create directory");
            return null;
        }
    }

    // Create a media file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
    File mediaFile;
    if (type == MEDIA_TYPE_IMAGE){
        mediaFile = new File(mediaStorageDir.getAbsolutePath()
        		+ File.separator +
        "IMG_"+ timeStamp + ".png");
    } else {
        return null;
    }

    return mediaFile;
}
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            // Image captured and saved to fileUri specified in the Intent
            Toast.makeText(this,fileUri.toString(), Toast.LENGTH_LONG).show();
            mainimage.setImageURI(fileUri);
        } else if (resultCode == RESULT_CANCELED) {
        	Toast.makeText(getApplicationContext(), "Picture taken canceled by user", Toast.LENGTH_LONG).show();
            // User cancelled the image capture
        } else {
        	Toast.makeText(getApplicationContext(), "Picture taken failed", Toast.LENGTH_LONG).show();
            // Image capture failed, advise user
        }
    }
}
@Override
public void onConfigurationChanged(Configuration newConfig) {
	// TODO Auto-generated method stub
	super.onConfigurationChanged(newConfig);
	if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
		//mainimage.setImageURI(fileUri);
		Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
		}
		else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
			//mainimage.setImageURI(fileUri);
		}
}
@Override
public Object onRetainNonConfigurationInstance() {
	// TODO Auto-generated method stub
	return super.onRetainNonConfigurationInstance();
}

}
