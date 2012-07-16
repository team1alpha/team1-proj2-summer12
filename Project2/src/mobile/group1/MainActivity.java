package mobile.group1;

import mobile.group1.DB.GameRecord;
import mobile.group1.DB.ItemRecord;
import mobile.group1.DB.MobDBRecord.ResponseListener;
import mobile.group1.DB.MobDBTransaction;
import mobile.group1.DB.MobDBTransaction.QueryType;
import mobile.group1.DB.UserRecord;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// /////////////////////////////////////////////////////////////////////////
	// debug support
	// /////////////////////////////////////////////////////////////////////////
	static public void debug(String msg) {
		Log.i("me", msg);
	}
	
	ImageView imageView;
	EditText username;
	EditText password;
	String user;
	String pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passwordET);
        

		ResponseListener responseListener = new ResponseListener()
		{
			@Override
			public void onResponse(Bundle bundle)
			{
				if(bundle != null)
				{
					QueryType queryType = QueryType.values()[bundle.getInt(MobDBTransaction.QUERY_TYPE)]; 
					
					if(bundle.getBoolean(MobDBTransaction.RESULT_CODE) == true)
					{
						if(bundle.containsKey(UserRecord.USER_NAMES))
						{
							debug("----------------------------");
							debug("All users");
							for(String user: bundle.getStringArray(UserRecord.USER_NAMES))
							{
								debug("user:" + user);
							}
						}
						else
						{
							debug(queryType.toString() + " successfull for user ->" + bundle.getString(UserRecord.USER_INFO));
						}
					}
					else
					{
						debug(queryType.toString() + " failure for user ->" + bundle.getString(UserRecord.USER_INFO));
					}


				}
			}
		}; 
		
		ResponseListener gameListener = new ResponseListener()
		{

			@Override
			public void onResponse(Bundle bundle)
			{
				if(bundle != null)
				{
					QueryType queryType = QueryType.values()[bundle.getInt(MobDBTransaction.QUERY_TYPE)]; 
					
					if(bundle.getBoolean(MobDBTransaction.RESULT_CODE) == true)
					{
						if(bundle.containsKey(GameRecord.GAME_NAMES))
						{
							debug("----------------------------");
							debug("All games");
							for(String game: bundle.getStringArray(GameRecord.GAME_NAMES))
							{
								debug("games:" + game);
							}
						}
						else
						{
							debug(queryType.toString() + " successfull for game ->" + bundle.getString(GameRecord.GAME_INFO));
						}
					}
					else
					{
						debug(queryType.toString() + " failure for user ->" + bundle.getString(GameRecord.GAME_INFO));
					}


				}
			}
			
		};

//		UserRecord b = new UserRecord("b", responseListener);
//		UserRecord a = new UserRecord("a", responseListener);
//		b.Get();
//		b.GetALL();
//		String[] x = {"a", "b"};
//		String[] y = {"x", "y"};
//		GameRecord c = new GameRecord("c", gameListener);
//		c.Get();
//		c.GetALL();
		
		ItemRecord c = new ItemRecord("hello", new ResponseListener()
		{
			
			@Override
			public void onResponse(Bundle bundle)
			{
				if(bundle != null)
				{
					QueryType queryType = QueryType.values()[bundle.getInt(MobDBTransaction.QUERY_TYPE)];
					switch(queryType)
					{
					case Get:
					case Save:
					case Delete:
					case File:
					{
						Bitmap bitmap = bundle.getParcelable(ItemRecord.IMAGE);
						
						imageView = new ImageView(getApplicationContext());
						//imageView.setImageBitmap(bitmap);
						addContentView(imageView, new LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)));					}
					}
				}
			}
		});
		
		c.Get();
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
    	
    	user = username.getText().toString().trim();
    	pass = password.getText().toString().trim();
    	
    	if (user != null && pass != null){
			//Perform check if username is in used
			//insert into database
			Toast.makeText(getApplicationContext(), user +" "+ pass, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, Profile.class);
	    	startActivity(intent);
		}	
    	
    }
}
