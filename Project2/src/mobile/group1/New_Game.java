package mobile.group1;

import java.util.HashMap;
import java.util.Vector;

import mobile.group1.DB.ItemRecord;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class New_Game extends Activity {

	final String APP_KEY = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	EditText newgametitle;
	EditText item1name;
	EditText item1points;
	EditText item2name;
	EditText item2points;
	EditText item3name;
	EditText item3points;
	EditText item4name;
	EditText item4points;
	EditText item5name;
	EditText item5points;
	Button submit;
	String gamename;
	String name1;
	String name2;
	String name3;
	String name4;
	String name5;
	String user;
//	int points1;
//	int points2;
//	int points3;
//	int points4;
//	int points5;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_game);
		newgametitle = (EditText) findViewById(R.id.newgameET);
		item1name = (EditText) findViewById(R.id.item1ET);
		//item1points = (EditText) findViewById(R.id.item1PTS);
		item2name = (EditText) findViewById(R.id.item2ET);
		//item2points = (EditText) findViewById(R.id.item2PTS);
		item3name = (EditText) findViewById(R.id.item3ET);
		//item3points = (EditText) findViewById(R.id.item3PTS);
		item4name = (EditText) findViewById(R.id.item4ET);
		//item4points = (EditText) findViewById(R.id.item4PTS);
		item5name = (EditText) findViewById(R.id.item5ET);
		//item5points = (EditText) findViewById(R.id.item5PTS);
		submit = (Button) findViewById(R.id.button2);
		user = getIntent().getStringExtra("username");
	
	}

	public void submit(View v) {
		parsetheinfotostrings();
		//parsetheinfointointegers();

		//submit to server
		
		String values = name1 + ";" + name2 + ";" + name3 + ";" + name4 + ";" + name5 + ";";
		
		InsertRowData insertRowData = new InsertRowData("games");
		insertRowData.setValue("name", gamename);
		insertRowData.setValue("started", "yes");
		insertRowData.setValue("players", user + ";");
		insertRowData.setValue("items", values);
		
		MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    	
		    	Toast.makeText(getApplicationContext(), "Game has been created", Toast.LENGTH_LONG).show();
		    	
		    	
		    	
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    		
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    	Toast.makeText(getApplicationContext(), "Game could not be created created", Toast.LENGTH_LONG).show();
		    }
		});	
		
		

	}

	public void cancel(View v) {

		finish();
	}

	public void parsetheinfotostrings() {

		gamename = newgametitle.getText().toString();
		name1 = item1name.getText().toString();
		name2 = item2name.getText().toString();
		name3 = item3name.getText().toString();
		name4 = item4name.getText().toString();
		name5 = item5name.getText().toString();

	}

	public void parsetheinfointointegers() {

//		points1 = Integer.parseInt(item1points.getText().toString());
//		points2 = Integer.parseInt(item2points.getText().toString());
//		points3 = Integer.parseInt(item3points.getText().toString());
//		points4 = Integer.parseInt(item4points.getText().toString());
//		points5 = Integer.parseInt(item5points.getText().toString());
	}

	public void invite(View v) {
		
		
		if(gamename != null){
		
			Intent intent = new Intent(this, Invite.class);
			intent.putExtra("gamename", gamename);
		
			startActivity(intent);
		}
		

	}

}
