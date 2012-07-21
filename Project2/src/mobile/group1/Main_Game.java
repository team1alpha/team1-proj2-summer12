package mobile.group1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.mobdb.android.GetRowData;
import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;
import com.mobdb.android.UpdateRowData;

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
	Button invite;
	String gamename;
	String items;
	static String[] itemlist;
	static String user;
	static String item;
	static String game;
	static String filename;
	static String delete;
	Button upload;
	int[] score;
	static String[] userlist;
	static int flag;
	static String playerscore;
	static int oldscore;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_game);
		item1selected = true;
		upload = (Button) findViewById(R.id.uploadBT);
		invite = (Button) findViewById(R.id.invitebutton);
		player1 = (TextView) findViewById(R.id.player1);
		player2 = (TextView) findViewById(R.id.player2);
		player3 = (TextView) findViewById(R.id.player3);
		player4 = (TextView) findViewById(R.id.player4);
		player5 = (TextView) findViewById(R.id.player5);
		gamename = getIntent().getStringExtra("name");
		title = (TextView) findViewById(R.id.gameTitle);
		user = getIntent().getStringExtra("username");
		game = gamename;
		
		flag = 0;
		
		
		
		
		
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
		    				if(i == 0){
		    					
		    					player1.setText(playerlist[i]);
		    					
		    				}
		    					 
		    				else if(i == 1){
		    					
		    					player2.setText(playerlist[i]);
		    					
		    				}
		    				else if( i == 2){
		    					player3.setText(playerlist[i]);
		    				}
		    				else if(i == 3){
		    					player4.setText(playerlist[i]);
		    				}
		    				else if(i == 4){
		    					player5.setText(playerlist[i]);
		    				}
		    			}

		    		}
		    		
		    		if(user.equals(player1.getText().toString())){
		    			playerscore = "player1score";
		    		}
		    		else if(user.equals(player2.getText().toString())){
		    			playerscore = "player2score";
		    		}
		    		else if(user.equals(player3.getText().toString())){
		    			playerscore = "player3score";
		    		}
		    		else if(user.equals(player3.getText().toString())){
		    			playerscore = "player1score";
		    		}
		    		else if(user.equals(player5.getText().toString())){
		    			playerscore = "player5score";
		    		}
		    		else{
		    			playerscore = "player1score";
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
		    			item = itemlist[0];
		    			
		    			fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		    			picIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		    		}	
		    	}
		    	catch(JSONException e){
		    		Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_LONG).show();
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
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		
	}
	
	
	//end of onCreate
	////////////////////////////////////////////////////////////////////////////////////
	
	
	@Override
	public void onResume(){
		
		super.onResume();
		
		
		//fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		
		
		
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
	    
	}

	
	//end of  onResume
	/////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void takePicture(View v) {
	
		startActivityForResult(picIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			
		
	}

	////////////////////////////////////////////////////////////////////////////////
	
	
	//Invite other players
	public void invite(View v){
		
		
		if(gamename != null){
			
			Intent intent = new Intent(this, Invite.class);
			intent.putExtra("gamename", gamename);
			startActivity(intent);
		}	
		
	}
	
	
	///////////////////////////////////////////////////////////////////////////////
	
	
	//Show player progress
	public void playerClick(View v)
	{
		String holder = playerscore;
		
		if(v == player1){
			playerscore = "player1score";
		}
		else if(v == player2){
			playerscore = "player2score";
		}
		else if(v == player3){
			playerscore = "player3score";
		}
		else if(v == player4){
			playerscore = "player1score";
		}
		else if(v == player5){
			playerscore = "player5score";
		}
		
		
		TextView text = (TextView) v.findViewById(v.getId());
		String user = text.getText().toString();
		Intent intent = new Intent(this, Player_Progress.class);
		intent.putExtra("user", user);
		intent.putExtra("playerscore", playerscore);
		playerscore = holder;
		intent.putExtra("name", gamename);
		startActivity(intent);
		
		
		
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////
	
	
	public void upload(View v) {
		// upload picture to the server
		
		String filename = fileUri.toString();
		String finder = user + ";" + game + ";" + item + ";";
		//Bitmap bitmap = BitmapFactory.decodeFile(fileUri.toString());
		
		Bitmap bitmap = Bitmap.createBitmap(mainimage.getWidth(), mainimage.getHeight(),Bitmap.Config.ARGB_8888);

		if(bitmap != null){
		ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
		byte[] b = byteArrayBitmapStream.toByteArray();
		
		InsertRowData insertRowData = new InsertRowData("items");
		insertRowData.setValue("name", filename);
		insertRowData.setValue("finder", filename);
		insertRowData.setValue("image", filename, b);
		 
		MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	
		    	Toast.makeText(getApplicationContext(), "inserted", Toast.LENGTH_LONG).show();
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (refer JSON REST API)
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG).show();
		    }
		});
		
		
		
		}
		
		
		
		
		
		
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
		    			oldscore = object.getInt(playerscore);
		    			Toast.makeText(getApplicationContext(), "" + oldscore, Toast.LENGTH_LONG).show();
		    			

		    			UpdateRowData updateRowData = new UpdateRowData("games");
		    			updateRowData.setValue(playerscore,oldscore + flag);
		    			updateRowData.whereEqualsTo("name", game);
		    			 
		    			MobDB.getInstance().execute(APP_KEY, updateRowData, null, false, new MobDBResponseListener() {
		    			     
		    			    @Override public void mobDBSuccessResponse() {
		    			    //request successfully executed
		    			    	
		    			    	//Toast.makeText(getApplicationContext(), "inserted", Toast.LENGTH_LONG).show();
		    			    }          
		    			     
		    			    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    			    //row list in Vector<HashMap<String, Object[]>> object             
		    			    }          
		    			     
		    			    @Override public void mobDBResponse(String jsonStr) {
		    			    //table row list in raw JSON string (refer JSON REST API)
		    			    }
		    			     
		    			    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    			    //get file name with extension and file byte array
		    			    }          
		    			     
		    			    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    			    //request failed
		    			    	Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG).show();
		    			    }
		    			});
		    			
		    			
		    			
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
		    	Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
		    }
		});	
		
		
		
		
		
	}

	////////////////////////////////////////////////////////////////////////////
	
	public void delete(View v) {
		// activity to see comments and flags
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
		delete = mediaStorageDir.getAbsolutePath() + File.separator + "IMG_" + user + "_" +  game + "_" + item + ".png";
		File file = new File(delete);
		boolean deleted = file.delete();
		if(deleted){
			Toast.makeText(getApplicationContext(), "picture deleted", Toast.LENGTH_LONG).show();
			
			mainimage.setImageURI(null);
		}
		//flagintent = new Intent(this, Flag_System.class);
		//startActivity(flagintent);
		
	}

	///////////////////////////////////////////////////////////////////////////
	
	public void imagebt1(View V) {
		itemname.setText(itemlist[0]);
		itemscore.setText(itemlist[1]);
		item1selected = true;
		game = gamename;
		item = itemlist[0];
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		picIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		mainimage.setImageURI(fileUri);
		flag = Integer.parseInt(itemlist[1]);
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
		picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		picIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		mainimage.setImageURI(fileUri);
		flag = Integer.parseInt(itemlist[3]);
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
		picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		picIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		mainimage.setImageURI(fileUri);
		flag = Integer.parseInt(itemlist[5]);
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
		picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		picIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		mainimage.setImageURI(fileUri);
		flag = Integer.parseInt(itemlist[7]);
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
		picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		picIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		mainimage.setImageURI(fileUri);
		flag = Integer.parseInt(itemlist[9]);
		// image is updated by button image
		// mainimage.setImageURI(fileUri);
		// information of item is downloaded


	}

	//////////////////////////////////////////////////////////////////////////
	
	public static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	
	////////////////////////////////////////////////////////////////////////
	
	
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
	
		filename = mediaStorageDir.getAbsolutePath() + File.separator + "IMG_" + user + "_" +  game + "_" + item + ".png";
		
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(filename);
		} else {
			return null;
		}

		return mediaFile;
	}


	//////////////////////////////////////////////////////////////////////////////////
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			if (resultCode == RESULT_OK) {
				
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				// Image captured and saved to fileUri specified in the Intent
				
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
	
	///////////////////////////////////////////////////////////////////////////
	
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
