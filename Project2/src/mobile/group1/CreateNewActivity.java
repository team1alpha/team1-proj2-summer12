package mobile.group1;

import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdb.android.GetRowData;
import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class CreateNewActivity extends Activity {

	Button createNew;
	EditText userName;
	EditText password;
	TextView userNameView;
	
	final String APP_KEY = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_new_account);
		// TODO Auto-generated method stub
	
	userName = (EditText) findViewById(R.id.createUsernameTextET);
	password = (EditText) findViewById(R.id.createPasswordTextET);
	createNew = (Button) findViewById(R.id.createNewButton);
	userNameView = (TextView) findViewById(R.id.createUsername);
	
	
	
	
	}

	public void createAccountHandler(View v) {

		// do what we need to to create and account and insert into the database
		
		GetRowData getRowData = new GetRowData("users");
		getRowData.getField("username");
		
		
		
		MobDB.getInstance().execute(APP_KEY, getRowData, null, false, new MobDBResponseListener() {
		     
		    @Override public void mobDBSuccessResponse() {
		    //request successfully executed
		    }          
		     
		    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    //row list in Vector<HashMap<String, Object[]>> object             
		    }          
		     
		    @Override public void mobDBResponse(String jsonStr) {
		    //table row list in raw JSON string (for format example: refer JSON REST API)
		    	try{
		    		JSONObject response = new JSONObject(jsonStr);
		    		int status = response.getInt("status");
		    		JSONArray usernames = response.getJSONArray("row");
		    		
		    		int flag = 0;
		    	
		    		for(int i = 0; i < usernames.length(); i++)
		    		{
		    			if(usernames.getJSONObject(i).getString("username").equals(userName.getText().toString()))
		    			{
		    				Toast.makeText(getApplicationContext(), "User name is in use, choose another name", Toast.LENGTH_LONG).show();
		    				userNameView.setTextColor(Color.rgb(255, 0, 0));
		    				flag++;
		    
		    			}
		    		
		    		}
		    		
		    		if(flag == 0)
		    		{
		    			
		    			InsertRowData insertRowData = new InsertRowData("users");
		    			insertRowData.setValue("username", userName.getText().toString());
		    			insertRowData.setValue("password", password.getText().toString());
		    			insertRowData.setValue("score", "0");
		    			insertRowData.setValue("current_game", "");
		    			insertRowData.setValue("found_items", "");
		    			
		    			 
		    			MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, new MobDBResponseListener() {
		    			     
		    			    @Override public void mobDBSuccessResponse() {
		    			    //request successfully executed
		    			    	Toast.makeText(getApplicationContext(), "User account has been created", Toast.LENGTH_LONG).show();
		    			    	userNameView.setTextColor(Color.rgb(255, 255, 255));
		    			    	finish();
		    			    }          
		    			     
		    			    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    			    //row list in Vector<HashMap<String, Object[]>> object             
		    			    }          
		    			     
		    			    @Override public void mobDBResponse(String jsonStr) {
		    			    //table row list in raw JSON string (refer JSON REST API)
		    			    }
		    			     
		    			    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    			    //get file name with extension and file byte array
		    			    }          
		    			     
		    			    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    			    //request failed
		    			    }
		    			});
		    		}
		    		
		    		
		    		
		    	}
		    	catch(JSONException e)
		    	{
		    		
		    	}
		    		
		    	
		    }
		     
		    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    //get file name with extension and file byte array
		    }          
		     
		    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    //request failed
		    }
		});
		
		
		
	
	}

}
