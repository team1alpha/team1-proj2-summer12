package mobile.group1;

import mobile.group1.DB.ItemRecord;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class New_Game extends Activity {

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

	String gamename;
	String name1;
	String name2;
	String name3;
	String name4;
	String name5;

	int points1;
	int points2;
	int points3;
	int points4;
	int points5;
	public static int TOTAL_SCORE = 0;
	
	ItemRecord itemrecord1;
	ItemRecord itemrecord2;
	ItemRecord itemrecord3;
	ItemRecord itemrecord4;
	ItemRecord itemrecord5;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_game);
		newgametitle = (EditText) findViewById(R.id.newgameET);
		item1name = (EditText) findViewById(R.id.item1ET);
		item1points = (EditText) findViewById(R.id.item1PTS);
		item2name = (EditText) findViewById(R.id.item2ET);
		item2points = (EditText) findViewById(R.id.item2PTS);
		item3name = (EditText) findViewById(R.id.item3ET);
		item3points = (EditText) findViewById(R.id.item3PTS);
		item4name = (EditText) findViewById(R.id.item4ET);
		item4points = (EditText) findViewById(R.id.item4PTS);
		item5name = (EditText) findViewById(R.id.item5ET);
		item5points = (EditText) findViewById(R.id.item5PTS);
	}

	public void submit(View v) {
		parsetheinfotostrings();
		parsetheinfointointegers();

		// submit content to server
//		itemrecord1.setName(name1);
//		itemrecord1.setScore(points1);
//		itemrecord2.setName(name2);
//		itemrecord2.setScore(points2);
//		itemrecord3.setName(name3);
//		itemrecord3.setScore(points3);
//		itemrecord4.setName(name4);
//		itemrecord4.setScore(points4);
//		itemrecord5.setName(name5);
//		itemrecord5.setScore(points5);
//		
//		itemrecord1.Save();
//		itemrecord2.Save();
//		itemrecord3.Save();
//		itemrecord4.Save();
//		itemrecord5.Save();
		Intent myintent = new Intent(this, Main_Game.class);
		if (TOTAL_SCORE != 0) {
			myintent.putExtra("totalscore", TOTAL_SCORE);
		}
		if (gamename != null){
			myintent.putExtra("nameofgame", gamename);
		}
		startActivity(myintent);

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
		try{
		points1 = Integer.parseInt(item1points.getText().toString());
		points2 = Integer.parseInt(item2points.getText().toString());
		points3 = Integer.parseInt(item3points.getText().toString());
		points4 = Integer.parseInt(item4points.getText().toString());
		points5 = Integer.parseInt(item5points.getText().toString());
		TOTAL_SCORE = points1+points2+points3+points4+points5;
		
		String total = (String) String.valueOf(TOTAL_SCORE);
		Toast.makeText(this,total, Toast.LENGTH_SHORT).show();
		} catch(Exception exception){	
			Toast.makeText(this,"Missing Numbers, Complete each section", Toast.LENGTH_SHORT).show();
		}
		
	}

	public void invite() {

	}

}
