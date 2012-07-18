package mobile.group1;

import java.io.File;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
	TextView gamestarted;
	TextView flagpic;
	TextView gametitle;
	TextView totalscoreTV;
	TextView currentscoreTV;
	int selection = 0;
	int currentscore = 0;
	Bitmap pictureUploaded;
	String nameofgame;
	int TotalnewScore = 0;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_game);
		
		gametitle = (TextView) findViewById(R.id.gameTitle);
		itemname = (TextView) findViewById(R.id.itemName);
		itemscore = (TextView) findViewById(R.id.pointsworth);
		gamestarted = (TextView) findViewById(R.id.startedboolean);
		mainimage = (ImageView) findViewById(R.id.mainImage);
		flagpic = (TextView) findViewById(R.id.flagpic);
		totalscoreTV = (TextView) findViewById(R.id.totalscore);
		currentscoreTV = (TextView) findViewById(R.id.currentpoints);
		// create Intent to take a picture and return control to the calling
		// application
		picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
		// picIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Intent mainintent = getIntent();
		Bundle mainbundle = mainintent.getExtras();
		if (mainbundle != null){
			nameofgame = mainbundle.getString("nameofgame");
			TotalnewScore = mainbundle.getInt("totalscore");
			
			String total = (String) String.valueOf(TotalnewScore);
			totalscoreTV.setText(total);
			gametitle.setText(nameofgame);
		}

	}

	public void takePicture(View v) {

		startActivityForResult(picIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	public void upload(View v) {
		checksitemselected();
		// upload picture to the server
		if (pictureUploaded != null) {
			mainimage.setImageBitmap(pictureUploaded);
		} else {
			Toast.makeText(this, "Pic not there", Toast.LENGTH_SHORT).show();
		}
		
	}
	public void friendsprogress(View v) {
		Toast.makeText(this, "Friend list", Toast.LENGTH_SHORT).show();
		

	}

	public void imagebt1(View V) {
		selection = 1;
		itemname.setText("Item 1");
		itemscore.setText("10");
		currentscoreTV.setText(String.valueOf(currentscore));
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded
		//if picture is updated update score
		

	}

	public void imagebt2(View V) {
		selection = 2;
		itemname.setText("Item 2");
		itemscore.setText("20");
		currentscoreTV.setText(String.valueOf(currentscore));

		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded

	}

	public void imagebt3(View V) {
		selection = 3;
		itemname.setText("Item 3");
		itemscore.setText("30");
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded
		
			if (pictureUploaded != null) {
			mainimage.setImageBitmap(pictureUploaded);
			currentscore = 20;
			Toast.makeText(this, String.valueOf(currentscore), Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Pic not there", Toast.LENGTH_SHORT).show();
		}
		currentscoreTV.setText(String.valueOf(currentscore));

	}

	public void imagebt4(View V) {
		selection = 4;
		itemname.setText("Item 4");
		itemscore.setText("40");
		
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded
		currentscoreTV.setText(String.valueOf(currentscore));


	}

	public void imagebt5(View V) {
		selection = 5;
		itemname.setText("Item 5");
		itemscore.setText("500");
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded
		whatsthescore(500);
		Toast.makeText(this, String.valueOf(whatsthescore(0)), Toast.LENGTH_LONG).show();
		currentscoreTV.setText(String.valueOf(currentscore));

	}

	public static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
		// Uri to get the image of the sd card after pic is taken

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
				// /Toast.makeText(this, fileUri.toString(), Toast.LENGTH_LONG)
				// .show();
				// mainimage.setImageURI(fileUri);
				// image set from uri
				pictureUploaded = (Bitmap) data.getExtras().get("data");
//				if (pictureUploaded != null) {
//					mainimage.setImageBitmap(pictureUploaded);
//				} else {
//					Toast.makeText(this, "Pic not there", Toast.LENGTH_SHORT).show();
//				}

				// if (pictureUploaded != null){
				// mainimage.setImageBitmap(pictureUploaded);
				// }
				// else {
				// Toast.makeText(this, "Pic not there", Toast.LENGTH_LONG)
				// .show();
				// }
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),
						"Picture taken canceled by user", Toast.LENGTH_SHORT)
						.show();
				// User cancelled the image capture
			} else {
				Toast.makeText(getApplicationContext(), "Picture taken failed",
						Toast.LENGTH_SHORT).show();
				// Image capture failed, advise user
			}
		}
	}

	public void checksitemselected() {
		switch (selection){
		case 1:
			Toast.makeText(getApplicationContext(), "item 1",
					Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(getApplicationContext(), "item 2",
					Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(getApplicationContext(), "item 3",
					Toast.LENGTH_SHORT).show();
			
			break;
		case 4: 
			Toast.makeText(getApplicationContext(), "item 4",
					Toast.LENGTH_SHORT).show();
			break;
		case 5: 
			Toast.makeText(getApplicationContext(), "item 5",
					Toast.LENGTH_SHORT).show();
			break;
			default:
				Toast.makeText(getApplicationContext(), "No item Selected",
						Toast.LENGTH_SHORT).show();
		}

	}
	public void gamewinner(){
		// player has reached total score
		if (currentscore == TotalnewScore){
		Toast.makeText(getApplicationContext(), "Game Won! Congrats",
				Toast.LENGTH_SHORT).show();
		}
		}
	public int whatsthescore(int goal){
		currentscore += goal;
		return currentscore;
		
	}

}
