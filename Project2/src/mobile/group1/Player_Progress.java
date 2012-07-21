package mobile.group1;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import mobile.group1.DB.MobDBRecord;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdb.android.GetFile;
import com.mobdb.android.GetRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class Player_Progress extends Activity {

	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static Uri fileUri;
	Intent picIntent;
	Intent flagintent;
	ImageView mainimage;
	TextView itemname;
	TextView itemscore;
	TextView player1;
	
	boolean item1selected = false;
	boolean item2selected = false;
	boolean item3selected = false;
	boolean item4selected = false;
	boolean item5selected = false;
	TextView title;
	final String APP_KEY = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	Button invite;
	String gamename;
	String items;
	String[] itemlist;
	String player;
	static String user;
	static String game;
	static String item;
	static int score;
	static String playerscore;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_progress);
				
		player = getIntent().getStringExtra("user");
		title = (TextView) findViewById(R.id.gameTitle);
		gamename = getIntent().getStringExtra("name");
		player1 = (TextView) findViewById(R.id.player0);
		
		score = 0;
		game = gamename;
		user = player;
		playerscore = getIntent().getStringExtra("playerscore");
		
		
		
		GetRowData getRowData2 = new GetRowData("games");
		getRowData2.getField(playerscore);
		getRowData2.whereEqualsTo("name", game);
		
		
		MobDB.getInstance().execute(APP_KEY, getRowData2, null, false, new MobDBResponseListener() {
		     
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
		    		
		    		if(status == 101)
		    		{
		    			//get JSONArray containing all rows of user data
		    			
		    			JSONArray array = response.getJSONArray("row");
		    			JSONObject object = array.getJSONObject(0);
		    			score = object.getInt(playerscore);
		    			//Toast.makeText(getApplicationContext(), user + score, Toast.LENGTH_LONG).show();
		    			player1.setText(player + "  -  " + score);
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
		
		
		
		GetRowData getRowData3 = new GetRowData("games");
		getRowData3.getField("items");
		getRowData3.whereEqualsTo("name", gamename);
		
		MobDB.getInstance().execute(APP_KEY, getRowData3, null, false, new MobDBResponseListener() {
		     
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
		    			itemlist = new String[object.length()];
		    			
		    			items = object.getString("items");
		    			
		    			itemlist = items.split(";");
		    			
		    			

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
		player1.setText(player + "  -  " + score);
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
	
	

	

	public void imagebt1(View V) {
		itemname.setText(itemlist[0]);
		itemscore.setText(itemlist[1]);
		item2selected = true;
		item = itemlist[0];
		
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); 
		
		//Toast.makeText(getApplicationContext(), fileUri.toString(), Toast.LENGTH_LONG).show();
		
		
		
		
		
		
		GetFile getFile = new GetFile("GET file=17");
		
		 
		MobDB.getInstance().execute(APP_KEY, getFile, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	
		    	
		    	Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    	
		    	try{
		    		
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		
		    		if(status != 101){
		    			
		    			Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    			JSONArray array = response.getJSONArray("row");
		    			JSONObject object = array.getJSONObject(0);
		    			
		    			String file = object.getString("image");
		    			
		    			mainimage.setImageURI(fileUri);
		    			
		    		}
		    		
		    	}catch(JSONException e){
		    		
		    	}
		    	
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	
		    	Toast.makeText(getApplicationContext(), "didnt get it", Toast.LENGTH_LONG).show();
		    }
		});
		
		
		
		//mainimage.setImageURI(fileUri);
		
		
		
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded

	}
	
	
	public void imagebt2(View V) {
		itemname.setText(itemlist[2]);
		itemscore.setText(itemlist[3]);
		item2selected = true;
		
		item = itemlist[2];
		
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); 
		
		//Toast.makeText(getApplicationContext(), fileUri.toString(), Toast.LENGTH_LONG).show();
		
		GetFile getFile = new GetFile("GET file=" + fileUri.toString());
		
		 
		MobDB.getInstance().execute(APP_KEY, getFile, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	
		    	
		    	Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    	
		    	try{
		    		
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		
		    		if(status != 101){
		    			
		    			Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    			
		    			
		    			
		    		}
		    		
		    	}catch(JSONException e){
		    		
		    	}
		    	
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	
		    	Toast.makeText(getApplicationContext(), "didnt get it", Toast.LENGTH_LONG).show();
		    }
		});
		
		
		
		mainimage.setImageURI(fileUri);
	
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded

	}

	public void imagebt3(View V) {
		itemname.setText(itemlist[4]);
		itemscore.setText(itemlist[5]);
		item3selected = true;
		item = itemlist[4];
		
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); 
		
		//Toast.makeText(getApplicationContext(), fileUri.toString(), Toast.LENGTH_LONG).show();
		
		GetFile getFile = new GetFile("GET file=" + fileUri.toString());
		
		 
		MobDB.getInstance().execute(APP_KEY, getFile, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	
		    	
		    	Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    	
		    	try{
		    		
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		
		    		if(status != 101){
		    			
		    			Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    			
		    			
		    			
		    		}
		    		
		    	}catch(JSONException e){
		    		
		    	}
		    	
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	
		    	Toast.makeText(getApplicationContext(), "didnt get it", Toast.LENGTH_LONG).show();
		    }
		});
		
		
		
		mainimage.setImageURI(fileUri);
		
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded

	}

	public void imagebt4(View V) {
		itemname.setText(itemlist[6]);
		itemscore.setText(itemlist[7]);
		item4selected = true;
		item = itemlist[6];
		
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); 
		
		//Toast.makeText(getApplicationContext(), fileUri.toString(), Toast.LENGTH_LONG).show();
		
		GetFile getFile = new GetFile("GET file=" + fileUri.toString());
		
		 
		MobDB.getInstance().execute(APP_KEY, getFile, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	
		    	
		    	Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    	
		    	try{
		    		
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		
		    		if(status != 101){
		    			
		    			Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    			
		    			
		    			
		    		}
		    		
		    	}catch(JSONException e){
		    		
		    	}
		    	
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	
		    	Toast.makeText(getApplicationContext(), "didnt get it", Toast.LENGTH_LONG).show();
		    }
		});
		
		
		
		mainimage.setImageURI(fileUri);
		
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded

	}

	public void imagebt5(View V) {
		itemname.setText(itemlist[8]);
		itemscore.setText(itemlist[9]);
		item5selected = true;
		item = itemlist[8];
		
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); 
		
		//Toast.makeText(getApplicationContext(), fileUri.toString(), Toast.LENGTH_LONG).show();
		
		GetFile getFile = new GetFile("GET file=" + fileUri.toString());
		
		 
		MobDB.getInstance().execute(APP_KEY, getFile, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	
		    	
		    	Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    	
		    	try{
		    		
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		
		    		if(status != 101){
		    			
		    			Toast.makeText(getApplicationContext(), "got it", Toast.LENGTH_LONG).show();
		    			
		    			
		    			
		    		}
		    		
		    	}catch(JSONException e){
		    		
		    	}
		    	
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	
		    	Toast.makeText(getApplicationContext(), "didnt get it", Toast.LENGTH_LONG).show();
		    }
		});
		
		
		
		mainimage.setImageURI(fileUri);
		
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

		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
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
		File mediaFile;
	
		String filename = mediaStorageDir.getAbsolutePath() + File.separator + "IMG_" + user + "_" +  game + "_" + item + ".png";
		
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(filename);
		} else {
			return null;
		}

		return mediaFile;
	}


	

}
