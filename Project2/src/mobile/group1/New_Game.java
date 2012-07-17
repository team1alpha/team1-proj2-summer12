package mobile.group1;

import mobile.group1.DB.ItemRecord;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
	
	ItemRecord itemrecord;

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
		//parsetheinfointointegers();

		// submit content to server
		
		
//		itemrecord.setName(name1);
//		itemrecord.setDescription("100");
//		
//		itemrecord.Save();
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

		points1 = Integer.parseInt(item1points.getText().toString());
		points2 = Integer.parseInt(item2points.getText().toString());
		points3 = Integer.parseInt(item3points.getText().toString());
		points4 = Integer.parseInt(item4points.getText().toString());
		points5 = Integer.parseInt(item5points.getText().toString());
	}

	public void invite() {

	}

}
