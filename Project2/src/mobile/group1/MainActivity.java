package mobile.group1;


import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import mobile.group1.DB.MobDBRecord.ResponseListener;
import mobile.group1.DB.MobDBTransaction;
import mobile.group1.DB.MobDBTransaction.QueryType;
import mobile.group1.DB.UserRecord;

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

		UserRecord b = new UserRecord("b", responseListener);
		UserRecord a = new UserRecord("a", responseListener);
		b.Get();
		b.GetALL();
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
