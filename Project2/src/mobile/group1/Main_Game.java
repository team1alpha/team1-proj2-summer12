package mobile.group1;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdb.android.GetRowData;
import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class Main_Game extends Activity {

	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static Uri fileUri;
	Intent picIntent;
	Intent flagintent;
	ImageView mainimage;
	TextView itemname;
	TextView itemscore;
	TextView player1;
	TextView player2;
	TextView player3;
	TextView player4;
	TextView player5;
	boolean item1selected = false;
	boolean item2selected = false;
	boolean item3selected = false;
	boolean item4selected = false;
	boolean item5selected = false;
	TextView title;
	final String APP_KEY = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_game);
		
		player1 = (TextView) findViewById(R.id.player1);
		player2 = (TextView) findViewById(R.id.player2);
		player3 = (TextView) findViewById(R.id.player3);
		player4 = (TextView) findViewById(R.id.player4);
		player5 = (TextView) findViewById(R.id.player5);
		
		String gamename = getIntent().getStringExtra("name");
		title = (TextView) findViewById(R.id.gameTitle);
		
		
		GetRowData getRowData = new GetRowData("games");
		getRowData.getField("players");
		getRowData.whereEqualsTo("name", gamename);
		
		MobDB.getInstance().execute(APP_KEY, getRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	//Toast.makeText(getApplicationContext(), "got games data", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	try{
		    		
		    		//check to see if we got the data from the db
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		JSONObject object;
		    		
		    		if(status == 101)
		    		{
		    			//get JSONArray containing all rows of game data
		    			JSONArray array = response.getJSONArray("row");
		    			object = array.getJSONObject(0);
		    			
		    			String players = object.getString("players");
		    			
		    			String[] playerlist = players.split(";");
		    			
		    			for(int i = 0; i < playerlist.length; i++)
		    			{
		    				if(i == 0)
		    					player1.setText(playerlist[i]); 
		    				else if(i == 1)
		    					player2.setText(playerlist[i]);
		    				else if( i == 2)
		    					player3.setText(playerlist[i]);
		    				else if(i == 3)
		    					player4.setText(playerlist[i]);
		    				else if(i == 4)
		    					player5.setText(playerlist[i]);
		    			}
		    			
		    			
		    			
		    		}	
		    	}
		    	catch(JSONException e){
		    		
		    	}
		    	
			    
			   
			    
			    
		    	
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	
		    }
		});	
	      
		
		
		
		
		
		title.setText(gamename);
		
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

	public void playerClick(View v)
	{
		int id = v.getId();
		
		//TextView view = (TextView) findViewById(R.id.)
		
		
		Toast.makeText(getApplicationContext()," Click" ,Toast.LENGTH_LONG).show();
		
	}
	
	public void upload(View v) {
		// upload picture to the server
		
		InsertRowData insertRowData = new InsertRowData("items");
		
		
		
		
		
		
		
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
		// information of item is downloaded

	}

	public void imagebt2(View V) {
		itemname.setText("Item 2");
		itemscore.setText("20");
		item2selected = true;
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded

	}

	public void imagebt3(View V) {
		itemname.setText("Item 3");
		itemscore.setText("30");
		item3selected = true;
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded

	}

	public void imagebt4(View V) {
		itemname.setText("Item 4");
		itemscore.setText("40");
		item4selected = true;
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded

	}

	public void imagebt5(View V) {
		itemname.setText("Item 5");
		itemscore.setText("500");
		item5selected = true;
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded


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
