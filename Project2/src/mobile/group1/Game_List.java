package mobile.group1;

import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
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

public class Game_List extends ListActivity {

	 String[] gamelist;
	 final String APP_KEY = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	 String username;
	 int flag;
	 int x;
	 String[] list;
	 ArrayAdapter<String>adapter;
	 CharSequence cs;
	 
	 
	 
	 /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    // TODO Auto-generated method stub
	   
	    //get username from login to check for games
	    //username = getIntent().getStringExtra("username");
	    
	    
	  
	    
	    username = getIntent().getStringExtra("username");
	    
	    //Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();
	    //max list of games
	    list = new String[5];
	    final ListView lv;
	    flag = 0;
	    x = 0;
	    
	    cs = (CharSequence) username;
	    
		 GetRowData getRowData = new GetRowData("games");
		    
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
			    		
			    		//check to see if we got the data from the db
			    		JSONObject response = new JSONObject(jsonStr);
			    		int status = response.getInt("status");
			    		JSONObject object;
			    		if(status == 101)
			    		{
			    			//get JSONArray containing all rows of game data
			    			JSONArray array = response.getJSONArray("row");
			    			
			    			for(int i = 0; i < array.length(); i++){
			    				
			    				//iterate through the games in the db
			    				object = array.getJSONObject(i);
			    				
			    				
			    				//see if the user is part of the game
			    				
			    				if(object.getString("players").contains(cs)){
			    					
			    					flag++;
			    					list[x] = object.getString("name");
			    					x++;
			    					
			    				}
			    			}	
			    		}
			    	}
			    	catch(JSONException e){
			    		
			    	}
			    	if(flag != 0){
				    	
			    		//create game list
				    	gamelist = new String[flag];
				    	for(int m = 0; m < flag; m++){
				    		
				    		//set the users game list to games they are a part of
				    		gamelist[m] = list[m];
				    	
				    	}
				    	
				    	//set the listview to display games that the user in a part of
				    	adapter = new ArrayAdapter<String>(Game_List.this, android.R.layout.simple_list_item_1, gamelist);
					    setListAdapter(adapter);
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
					
					Intent intent = new Intent(getApplicationContext(), Main_Game.class);
					String game = lv.getItemAtPosition(arg2).toString();
					intent.putExtra("name", game);
					startActivity(intent);
					
					
				}
		    	
		    	
			});
		    
	    
	}
	
	
	
	
}
