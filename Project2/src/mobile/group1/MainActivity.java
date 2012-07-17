package mobile.group1;

import mobile.group1.DB.GameRecord;
import mobile.group1.DB.ItemRecord;
import mobile.group1.DB.UserRecord;
import mobile.group1.DB.MobDBTable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity
{
	// /////////////////////////////////////////////////////////////////////////
	// debug support
	// /////////////////////////////////////////////////////////////////////////
	static public void debug(String msg)
	{
		Log.i("me", msg);
	}

	ImageView imageView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void createNewHandler(View v)
	{

		Intent intent = new Intent(this, CreateNewActivity.class);
		startActivity(intent);

	}

	public void login(View v)
	{

		Intent intent = new Intent(this, Profile.class);
		startActivity(intent);

	}
}
