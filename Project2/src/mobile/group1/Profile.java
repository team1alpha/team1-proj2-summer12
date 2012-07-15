package mobile.group1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Profile extends Activity {

	TextView profilename;
	Intent inprogressintent;
	Intent newgameintent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilepage);

		// TODO Auto-generated method stub
	}

	public void inprogress(View v) {

		inprogressintent = new Intent(this, Main_Game.class);
		startActivity(inprogressintent);
	}

	public void newgame(View v) {

	
	}

	public void exit1(View v) {

		finish();
	}

}
