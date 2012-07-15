package mobile.group1;

import java.io.File;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Game extends Activity {

	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static Uri fileUri;
	Intent picIntent;
	Intent flagintent;
	ImageView mainimage;
	TextView itemname;
	TextView itemscore;
	boolean item1selected = false;
	boolean item2selected = false;
	boolean item3selected = false;
	boolean item4selected = false;
	boolean item5selected = false;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_game);

		itemname = (TextView) findViewById(R.id.itemName);
		itemscore = (TextView) findViewById(R.id.pointsworth);
		mainimage = (ImageView) findViewById(R.id.mainImage);
		// create Intent to take a picture and return control to the calling
		// application
		picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to
															// save the image
		picIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image
																// file name

	}

	public void takePicture(View v) {
	
		startActivityForResult(picIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	public void upload(View v) {
		// upload picture to the server
	}

	public void moreinfo(View v) {
		// activity to see comments and flags
		flagintent = new Intent(this, Flag_System.class);
		startActivity(flagintent);
		
	}

	public void imagebt1(View V) {
		itemname.setText("Item 1");
		itemscore.setText("10");
		item1selected = true;
		// image is updated by button image
		// mainimage.setImageURI(fileUri);

	}

	public void imagebt2(View V) {
		itemname.setText("Item 2");
		itemscore.setText("20");
		item2selected = true;
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
	}

	public void imagebt3(View V) {
		itemname.setText("Item 3");
		itemscore.setText("30");
		item3selected = true;
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
	}

	public void imagebt4(View V) {
		itemname.setText("Item 4");
		itemscore.setText("40");
		item4selected = true;
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
	}

	public void imagebt5(View V) {
		itemname.setText("Item 5");
		itemscore.setText("500");
		item5selected = true;
		// image is updated by button image
		// mainimage.setImageURI(fileUri);

	}

	public static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	public static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new java.util.Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getAbsolutePath()
					+ File.separator + "IMG_" + timeStamp + ".png");
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
				Toast.makeText(this, fileUri.toString(), Toast.LENGTH_LONG)
						.show();
				mainimage.setImageURI(fileUri);
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),
						"Picture taken canceled by user", Toast.LENGTH_LONG)
						.show();
				// User cancelled the image capture
			} else {
				Toast.makeText(getApplicationContext(), "Picture taken failed",
						Toast.LENGTH_LONG).show();
				// Image capture failed, advise user
			}
		}
	}
	public void checksitemselected(){
		if (item1selected){
			item1selected = true;
		}
		else if (item2selected){
			item2selected = true;
		}
		else if (item3selected){
			item3selected = true;
		}
		else if (item4selected){
			item4selected = true;
		}
		else if (item5selected){
			item5selected = true;
		}
		
	}

}
