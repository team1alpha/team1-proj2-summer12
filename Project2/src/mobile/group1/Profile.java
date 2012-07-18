package mobile.group1;

import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdb.android.GetRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class Profile extends Activity {

	TextView profilename;
	Intent inprogressintent;
	Intent newgameintent;
	String user;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilepage);

		// TODO Auto-generated method stub
		user = getIntent().getStringExtra("username");
		
		profilename = (TextView) findViewById(R.id.profile_name);
		
		profilename.setText(user);
		
		
		
	}

	public void inprogress(View v) {

		inprogressintent = new Intent(this, Game_List.class);
		
		inprogressintent.putExtra("username", user);
	
		startActivity(inprogressintent);
	}

	public void newgame(View v) {

		newgameintent = new Intent(this, New_Game.class);
		startActivity(newgameintent);
	}

	public void exit1(View v) {

		finish();
	}

}
