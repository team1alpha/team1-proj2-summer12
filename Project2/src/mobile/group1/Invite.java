package mobile.group1;

import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mobdb.android.GetRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;
import com.mobdb.android.UpdateRowData;

public class Invite extends ListActivity {

	 String[] gamelist;
	 final String APP_KEY = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	 String gamename;
	 String[] list;
	 ArrayAdapter<String>adapter;
	 CharSequence cs;
	 String name;
	 String players;
	 
	 
	 /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    // TODO Auto-generated method stub
	  
	    
	    gamename = getIntent().getStringExtra("gamename");
	    final ListView lv;
	    
	    
	    
		 GetRowData getRowData = new GetRowData("users");
		 getRowData.getField("name");
		 
		    MobDB.getInstance().execute(APP_KEY, getRowData, null, false, new MobDBResponseListener() {
			     
			    @Override public void mobDBSuccessResponse() {
			    //request successfully executed
			    	//Toast.makeText(getApplicationContext(), "got games data", Toast.LENGTH_LONG).show();
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
			    			list = new String[users.length()];
			    			for(int i = 0; i < users.length(); i++){
			    				
			    				list[i] = users.getJSONObject(i).getString("name");
			    				
			    				
			    				
			    			}
			    			
			    			adapter = new ArrayAdapter<String>(Invite.this, android.R.layout.simple_list_item_1, list);
						    setListAdapter(adapter);
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
		      
		    lv = getListView();
		    
		    lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

					name = lv.getItemAtPosition(arg2).toString();
					
					GetRowData getRowData = new GetRowData("games");
					getRowData.getField("players");
					getRowData.whereEqualsTo("name", gamename);
					
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
						    			
						    			JSONArray array = response.getJSONArray("row");
						    			JSONObject object = array.getJSONObject(0);
						    			
						    			
						    			players = object.getString("players");
						    			
						    			
						    			UpdateRowData updateRowData = new UpdateRowData("games");
										String value = players + name + ";";
										
										updateRowData.setValue("players", value);
									
										updateRowData.whereEqualsTo("name", gamename);
										
										
										MobDB.getInstance().execute(APP_KEY, updateRowData, null, false, new MobDBResponseListener() {
										     
										    @Override public void mobDBSuccessResponse() {
										    //request successfully executed
										    	Toast.makeText(getApplicationContext(), "Player has been added to game", Toast.LENGTH_LONG).show();
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
										    	Toast.makeText(getApplicationContext(), "Player could not be added to game", Toast.LENGTH_LONG).show();
										    }
										});	
										
						    			
						    			
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
		    		
			});
	}
	
}

