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

public class MainActivity extends Activity
{
	
	/*
	 * Do this early so that the query can go on in the background.
	 * once this completes the whole database is loaded.
	 */
	private static final MobDBTable table = new MobDBTable();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onStart()
	{
//			while(table.GetUserByName("test") == null)
//			{
//				try
//				{
//					Thread.sleep(500);
//				} catch (InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//			}
//			Log.i("me", "loaded");
			
			/*
			 * To get items whose name is known. If the database has not been loaded yet these might return null.
			 * I suggest when we start the app we show some kind of splash screen.
			 */
			UserRecord user   = table.GetUserByName("test");
			GameRecord game   = table.GetGameByName("c");
			ItemRecord item   = table.GetItemByName("google.bmp");

			/*
			 * Thats if now you have records for known items. To check to see if an item is in the DB
			 * do. 
			 */
			if(user != null)
			{
				Log.i("me", user.toString());
			}
			
			if(game != null)
			{
				Log.i("me", game.toString());
			}
			
			if(item != null)
			{
				Log.i("me", item.toString());
			}
			
			/*
			 * Once you have a reference to an object you can access it just like a normal object
			 * and all state changes can be committed with a database save. Of course the update
			 * takes some time so don reload your table right away.
			 */
			if(user != null)
			{
				user.setPassword("hello");
				table.Save();
			}
			
			// wait some time and you cannot block or busy wait.
			table.Load();
			
			//wait some time and you cannot block or busy wait.
			user = table.GetUserByName("test");
			
			if(user != null)
			{
				Log.i("me", user.toString()); // if enough time waited password should be updated
			}
			
			/*
			 * To add an object to the database while at the same time making sure no object
			 * of that same name is in there. 
			 */
			if(table.addUser(new UserRecord("sam", "soon", "swan", "sweep", "swoon")) == false)
			{
				// item already exhists make user choose different name for it.
			}

			//if(table.addItem(new ItemRecord("this", "that", ItemRecord.BitmapFromResouces(getResources(), R.drawable.five))) == true)
			{
				// item added
			}


		super.onStart();
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
