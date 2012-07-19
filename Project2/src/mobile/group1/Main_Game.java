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

	TextView title;
	final String APP_KEY = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	Button invite;
	String gamename;
	String items;
	String[] itemlist;

	// Camara pic is stored in the variable below after a picture is taken;
	// mainimage.setImageBitmap(pictureUploaded); // will update the picture to
	// the main game
	Bitmap pictureUploaded;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_game);

		invite = (Button) findViewById(R.id.invitebutton);
		player1 = (TextView) findViewById(R.id.player1);
		player2 = (TextView) findViewById(R.id.player2);
		player3 = (TextView) findViewById(R.id.player3);
		player4 = (TextView) findViewById(R.id.player4);
		player5 = (TextView) findViewById(R.id.player5);
		gamename = getIntent().getStringExtra("name");
		title = (TextView) findViewById(R.id.gameTitle);

		GetRowData getRowData = new GetRowData("games");
		getRowData.getField("players");
		getRowData.whereEqualsTo("name", gamename);

		MobDB.getInstance().execute(APP_KEY, getRowData, null, false,
				new MobDBResponseListener() {

					@Override
					public void mobDBSuccessResponse() {
						// request successfully executed
						// Toast.makeText(getApplicationContext(),
						// "got games data", Toast.LENGTH_LONG).show();
					}

					@Override
					public void mobDBResponse(
							Vector<HashMap<String, Object[]>> result) {
						// row list in Vector<HashMap<String, Object[]>> object
					}

					@Override
					public void mobDBResponse(String jsonStr) {
						// table row list in raw JSON string (for format
						// example: refer JSON REST API)
						try {

							// check to see if we got the data from the db
							JSONObject response = new JSONObject(jsonStr);
							int status = response.getInt("status");
							JSONObject object;

							if (status == 101) {
								// get JSONArray containing all rows of game
								// data
								JSONArray array = response.getJSONArray("row");
								object = array.getJSONObject(0);

								String players = object.getString("players");

								String[] playerlist = players.split(";");

								for (int i = 0; i < playerlist.length; i++) {
									if (i == 0)
										player1.setText(playerlist[i]);
									else if (i == 1)
										player2.setText(playerlist[i]);
									else if (i == 2)
										player3.setText(playerlist[i]);
									else if (i == 3)
										player4.setText(playerlist[i]);
									else if (i == 4)
										player5.setText(playerlist[i]);
								}

							}
						} catch (JSONException e) {

						}

					}

					@Override
					public void mobDBFileResponse(String fileName,
							byte[] fileData) {
						// get file name with extension and file byte array
					}

					@Override
					public void mobDBErrorResponse(Integer errValue,
							String errMsg) {
						// request failed

					}
				});

		GetRowData getRowData2 = new GetRowData("games");
		getRowData2.getField("items");
		getRowData2.whereEqualsTo("name", gamename);

		MobDB.getInstance().execute(APP_KEY, getRowData2, null, false,
				new MobDBResponseListener() {

					@Override
					public void mobDBSuccessResponse() {
						// request successfully executed
						// Toast.makeText(getApplicationContext(),
						// "got games data", Toast.LENGTH_LONG).show();
					}

					@Override
					public void mobDBResponse(
							Vector<HashMap<String, Object[]>> result) {
						// row list in Vector<HashMap<String, Object[]>> object
					}

					@Override
					public void mobDBResponse(String jsonStr) {
						// table row list in raw JSON string (for format
						// example: refer JSON REST API)
						try {

							// check to see if we got the data from the db
							JSONObject response = new JSONObject(jsonStr);
							int status = response.getInt("status");
							JSONObject object;

							if (status == 101) {
								// get JSONArray containing all rows of game
								// data
								JSONArray array = response.getJSONArray("row");
								object = array.getJSONObject(0);
								itemlist = new String[object.length()];

								items = object.getString("items");

								itemlist = items.split(";");

							}
						} catch (JSONException e) {

						}

					}

					@Override
					public void mobDBFileResponse(String fileName,
							byte[] fileData) {
						// get file name with extension and file byte array
					}

					@Override
					public void mobDBErrorResponse(Integer errValue,
							String errMsg) {
						// request failed

					}
				});

		title.setText(gamename);

		itemname = (TextView) findViewById(R.id.itemName);
		itemscore = (TextView) findViewById(R.id.pointsworth);
		mainimage = (ImageView) findViewById(R.id.mainImage);
		// create Intent to take a picture and return control to the calling
		// application
		picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	}

	@Override
	public void onResume() {

		super.onResume();

		GetRowData getRowData = new GetRowData("games");
		getRowData.getField("players");
		getRowData.whereEqualsTo("name", gamename);

		MobDB.getInstance().execute(APP_KEY, getRowData, null, false,
				new MobDBResponseListener() {

					@Override
					public void mobDBSuccessResponse() {
						// request successfully executed
						// Toast.makeText(getApplicationContext(),
						// "got games data", Toast.LENGTH_LONG).show();
					}

					@Override
					public void mobDBResponse(
							Vector<HashMap<String, Object[]>> result) {
						// row list in Vector<HashMap<String, Object[]>> object
					}

					@Override
					public void mobDBResponse(String jsonStr) {
						// table row list in raw JSON string (for format
						// example: refer JSON REST API)
						try {

							// check to see if we got the data from the db
							JSONObject response = new JSONObject(jsonStr);
							int status = response.getInt("status");
							JSONObject object;

							if (status == 101) {
								// get JSONArray containing all rows of game
								// data
								JSONArray array = response.getJSONArray("row");
								object = array.getJSONObject(0);

								String players = object.getString("players");

								String[] playerlist = players.split(";");

								for (int i = 0; i < playerlist.length; i++) {
									if (i == 0)
										player1.setText(playerlist[i]);
									else if (i == 1)
										player2.setText(playerlist[i]);
									else if (i == 2)
										player3.setText(playerlist[i]);
									else if (i == 3)
										player4.setText(playerlist[i]);
									else if (i == 4)
										player5.setText(playerlist[i]);
								}

							}
						} catch (JSONException e) {

						}

					}

					@Override
					public void mobDBFileResponse(String fileName,
							byte[] fileData) {
						// get file name with extension and file byte array
					}

					@Override
					public void mobDBErrorResponse(Integer errValue,
							String errMsg) {
						// request failed

					}
				});

	}

	public void takePicture(View v) {

		startActivityForResult(picIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	// Invite other players
	public void invite(View v) {

		if (gamename != null) {

			Intent intent = new Intent(this, Invite.class);
			intent.putExtra("gamename", gamename);
			startActivity(intent);
		}
	}

	// Show player progress
	public void playerClick(View v) {

		TextView text = (TextView) v.findViewById(v.getId());
		String user = text.getText().toString();
		Intent intent = new Intent(this, Main_Game.class);
		intent.putExtra("name", user);
		startActivity(intent);

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
		itemname.setText(itemlist[0]);
		itemscore.setText("1");
		// image is updated by button image
		
		// information of item is downloaded
		// test to see image is being updated when a camera pic is taken
		//mainimage.setImageBitmap(pictureUploaded);

	}

	public void imagebt2(View V) {
		itemname.setText(itemlist[1]);
		itemscore.setText("1");
		// image is updated by button image
		
		// information of item is downloaded

	}

	public void imagebt3(View V) {
		itemname.setText(itemlist[2]);
		itemscore.setText("1");
		// image is updated by button image
		
		// information of item is downloaded

	}

	public void imagebt4(View V) {
		itemname.setText(itemlist[3]);
		itemscore.setText("1");
		// image is updated by button image
		
		// information of item is downloaded

	}

	public void imagebt5(View V) {
		itemname.setText(itemlist[4]);
		itemscore.setText("1");
		// image is updated by button image
		
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

				pictureUploaded = (Bitmap) data.getExtras().get("data");

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

}
