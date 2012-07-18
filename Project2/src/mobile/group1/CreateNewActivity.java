package mobile.group1;

import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
	int flag = 0;
	final String APP_KEY = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	final String TABLE_NAME = "users";
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.create_new_account);
	    // TODO Auto-generated method stub
	    
	    createNew = (Button) findViewById(R.id.createNewButton);
	    userName = (EditText) findViewById(R.id.createUsernameTextET);
	    password = (EditText) findViewById(R.id.createPasswordTextET);
	    
	    userNameView = (TextView) findViewById(R.id.createUsername);
	    
	    
	}
	
	
	
	public void createAccountHandler(View v){
		
		//do what we need to to create and account and insert into the database
		
		
		//need to check against current user names
		//--------------------------------------------
		
		GetRowData getRowData = new GetRowData("users");
		getRowData.getField("name");
		
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
		    		
		    		
		    		if(status == 101){
		    			
		    			
		    			JSONArray users = response.getJSONArray("row");
			    		
		    			for(int i = 0; i < users.length(); i++){
		    				
		    				JSONObject object = users.getJSONObject(i);
		    				String name = object.getString("name");
		    				if(userName.getText().toString().equals(name))
		    				{	
		    					flag++;
		    				
		    				}
		    			}
			    		
		    			if(flag == 0)
		    			{
		    				//insert new user
		    				InsertRowData insertRowData = new InsertRowData("users");
		    				insertRowData.setValue("name", userName.getText().toString());
		    				insertRowData.setValue("password", password.getText().toString());
		    				insertRowData.setValue("game", "");
		    				insertRowData.setValue("found", "");
		    				insertRowData.setValue("score", "0");
		    				
		    				
		    				MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, new MobDBResponseListener() {
		    				     
		    				    @Override public void mobDBSuccessResponse() {
		    				    //request successfully executed
		    				    	
		    				    	Toast.makeText(getApplicationContext(), "user has been created", Toast.LENGTH_LONG).show();
		    				    	
		    				    	finish();
		    				    	
		    				    }          
		    				     
		    				    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) {
		    				    //row list in Vector<HashMap<String, Object[]>> object             
		    				    }          
		    				     
		    				    @Override public void mobDBResponse(String jsonStr) {
		    				    //table row list in raw JSON string (for format example: refer JSON REST API)
		    				    		
		    				    	
		    				    }
		    				     
		    				    @Override public void mobDBFileResponse(String fileName, byte[] fileData) {
		    				    //get file name with extension and file byte array
		    				    }          
		    				     
		    				    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) {
		    				    //request failed
		    				    	//Toast.makeText(getApplicationContext(), "user could not be created created", Toast.LENGTH_LONG).show();
		    				    }
		    				});	
		    				
		    			}
		    			else
				    	{
				    			Toast.makeText(getApplicationContext(), "username is in use", Toast.LENGTH_LONG).show();
				    			flag = 0;
				    	}
			    		
			    			
			    	}
		    	}
		    	catch(JSONException e){
		    		
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
