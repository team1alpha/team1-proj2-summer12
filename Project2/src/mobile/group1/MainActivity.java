package mobile.group1;

import mobile.group1.DB.ScavengerDB;
import mobile.group1.DB.User;
import mobile.group1.DB.User.UserErrorListener;
import mobile.group1.DB.User.UserListener;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity 
{
	///////////////////////////////////////////////////////////////////////////
	// debug support
	///////////////////////////////////////////////////////////////////////////
	static public void debug(String msg)
	{
		Log.i("me", msg);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        UserListener userListener = new UserListener()
		{
			@Override
			public void onUserDBAction(User user)
			{
				Log.i("me", "user listener:" + user.toString());
			}
		};
		
		UserErrorListener userErrorListner = new UserErrorListener()
		{
			@Override
			public void onUserDBError(Integer errValue, String errMsg)
			{
				Log.i("me", "user error listener:" + errMsg + ":" + String.valueOf(errValue));
			}
		};

		debug("reading users one by one.----------------------");
		User.RequestUser("a", userListener, userErrorListner);    
		User.RequestUser("b", userListener, userErrorListner);    
		User.RequestUser("c", userListener, userErrorListner);    
        
		debug("adding new user.-------------------------------");
		User d = new User("d", "d", "d", "d", ";");
		d.Save(userListener, userErrorListner);
		
		debug("requesting all users---------------------------");
		User.RequestAllUsers(userListener, userErrorListner);
		
		debug("deleting a user---------------------------");
		User.Delete("d", userListener, userErrorListner);
		
		debug("update a user---------------------------");
		d.setPassword("F");
		d.Save(userListener, userErrorListner);
		
		debug("requesting all users---------------------------");
		User.RequestAllUsers(userListener, userErrorListner);
		
		debug("requesting all users---------------------------");
		User.RequestAllUsers(userListener, userErrorListner);

        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
    public void createNewHandler(View v){
    	
    	Intent intent = new Intent(this, CreateNewActivity.class);
    	startActivity(intent);
    	
    	
    }
public void login(View v){
    	
    	Intent intent = new Intent(this, Main_Game.class);
    	startActivity(intent);
    	
    	
    }

    
}
