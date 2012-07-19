package mobile.group1;

import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobdb.android.GetRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class MainActivity extends Activity {
	// /////////////////////////////////////////////////////////////////////////
	// debug support
	// /////////////////////////////////////////////////////////////////////////
	static public void debug(String msg) {
		Log.i("me", msg);
	}
	
	Button login;
	EditText userName;
	EditText password;
	final String APP_KEY = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	final String TABLE_NAME = "users";
	Intent intent;
	ImageView imageView;
	String user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passwordET);
        login = (Button) findViewById(R.id.loginButton);
         
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
    	
    	user = userName.getText().toString();
    	intent = new Intent(this, Profile.class);
    	GetRowData getRowData = new GetRowData("users");
		getRowData.getField("name");
		getRowData.getField("password");
		getRowData.whereEqualsTo("name", userName.getText().toString());
		
		
		
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
			    		JSONObject login = users.getJSONObject(0);
			    		String pass = login.getString("password");
			    		if(pass.equals(password.getText().toString())){
			    			
			    			intent.putExtra("username", user);
			    	    	startActivity(intent);	
			    			
			    		}
			    		else
			    		{
			    			Toast.makeText(getApplicationContext(), "Password is incorrect", Toast.LENGTH_LONG).show();
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
		    	Toast.makeText(getApplicationContext(), "Could not login", Toast.LENGTH_LONG).show();
		    }
		});	
    	
    	
    }
    
    
   

}
