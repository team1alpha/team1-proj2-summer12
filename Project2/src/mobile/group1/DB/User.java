package mobile.group1.DB;

import java.util.HashMap;
import java.util.Vector;

import android.util.Log;

import com.mobdb.android.GetRowData;
import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class User
{
	///////////////////////////////////////////////////////////////////////////
	// debug support
	///////////////////////////////////////////////////////////////////////////
	static public void debug(String msg)
	{
		Log.i("me", msg);
	}
	
	///////////////////////////////////////////////////////////////////////////
	// constants
	///////////////////////////////////////////////////////////////////////////
	private static final String TABLE_NAME = "users";
	
	///////////////////////////////////////////////////////////////////////////
	// listeners definitions
	///////////////////////////////////////////////////////////////////////////
	public abstract static class UserListener
	{
		public abstract void onUserDBAction(User user);
	}
	
	public abstract static class UserErrorListener
	{
		public abstract void onUserDBError(Integer errValue, String errMsg);
	}

	///////////////////////////////////////////////////////////////////////////
	// data vars
	///////////////////////////////////////////////////////////////////////////
	private String username;
	private String password;
	private String currentGame;
	private String score;
	private String foundItems;
	
	///////////////////////////////////////////////////////////////////////////
	// data accessors
	///////////////////////////////////////////////////////////////////////////	
	public String getUsername()                    { return username;                }
	public void setUsername(String username)       { this.username = username;       }
	public String getPassword()                    { return password;                }
	public void setPassword(String password)       { this.password = password;       }
	public String getCurrentGame()                 { return currentGame;             }
	public void setCurrentGame(String currentGame) { this.currentGame = currentGame; }
	public String getScore()                       { return score;                   }
	public void setScore(String score)             { this.score = score;             }
	public String getFoundItems()                  { return foundItems;              }
	public void setFoundItems(String foundItems)   { this.foundItems = foundItems;   }

	///////////////////////////////////////////////////////////////////////////
	// instance methods
	///////////////////////////////////////////////////////////////////////////
	
	// create a user by providing each field
	public User(String username, String password, String current_game, String score, String found_items)
	{
		this.username    = username;
		this.password    = password;
		this.currentGame = current_game;
		this.score       = score;
		this.foundItems  = found_items;
	}
	
	// create a user with the minimal required data
	public User(String username, String password)
	{
		this.username    = username;
		this.password    = password;
		this.currentGame = "";
		this.score       = "";
		this.foundItems  = "";
	}	

	// get a printable version of
	public String toString()
	{
		return username + ":" + password + ":" + currentGame + ":" + score + ":" + foundItems.toString();
	}
	
	// save to database internal representation
	public void Save(final UserListener successListener, final UserErrorListener errorListener)
	{
		final User parent = this;
		
		// INSERT into TABLE_NAME VALUES(username, password, currentGame, score, foundItems)
		InsertRowData insertRowData = new InsertRowData(TABLE_NAME);
		insertRowData.setValue("username",     username);
		insertRowData.setValue("password",     password);
		insertRowData.setValue("current_game", currentGame);
		insertRowData.setValue("score",        score);
		insertRowData.setValue("found_items",  foundItems);
		
		MobDB.getInstance().execute(ScavengerDB.APP_KEY, insertRowData, null, false, 
				
				new MobDBResponseListener() 
				{
					@Override public void mobDBSuccessResponse()                             { debug("successfully queried mobdb");               }
					@Override public void mobDBResponse(String jsonObj)                      { debug("mobdb json response:" + jsonObj);           }
					@Override public void mobDBFileResponse(String fileName, byte[] fileData){ debug("mobdb file response filename:" + fileName); }
			
				    @Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result) 
				    {
				    	debug("mobdb db response count:" + String.valueOf(result.size()));

						// if a listener was provided 
						if(successListener != null)
						{
							// call it
							successListener.onUserDBAction(parent);
						}
				    }     
			     
				    @Override public void mobDBErrorResponse(Integer errValue, String errMsg) 
				    {
				    	debug("error reading mobdb errorcode:" + String.valueOf(errValue) + " errMsg:" + errMsg);
				    	
						// if a listener was provided 
						if(errorListener != null)
						{
							// call it
							errorListener.onUserDBError(errValue, errMsg);
						}
				    }
				});
	}

	///////////////////////////////////////////////////////////////////////////
	// class methods
	///////////////////////////////////////////////////////////////////////////
	public static void RequestUser(String username, final UserListener successListener, final UserErrorListener errorListener)
	{
		// SELECT * FROM TABLENAME where username=username
		GetRowData getRowData = new GetRowData(TABLE_NAME);
		getRowData.whereEqualsTo("username", username);

		// execute the query
		MobDB.getInstance().execute(ScavengerDB.APP_KEY, getRowData, null, false,
				
				new MobDBResponseListener()
				{
					// fillers
					@Override public void mobDBSuccessResponse()       { debug("successfully queried mobdb");    }
					@Override public void mobDBResponse(String jsonObj){ debug("mobdb json response:" + jsonObj);}
			
					 // One of the methods below will get called as a callback when the query is 
					 // complete. mobDBResponse on success or mobDBErrorResponse on failure.
					 // These call the passed in handlers.
					@Override
					public void mobDBResponse(Vector<HashMap<String, Object[]>> result)
					{
						debug("mobdb db response count:" + String.valueOf(result.size()));
						
						// get the first item. As we are only allowing unique usernames
						// getting here we should have strictly one records returned.
						HashMap<String, Object[]> item = result.get(0);
						
						// get the various fields from the map returned by query
						// I dont understand why each value of the key:value pair is an 
						// array of objects instead of just a single object.
						User user = new User(item.get("username")[0].toString(), 
											 item.get("password")[0].toString(), 
											 item.get("current_game")[0].toString(),
											 item.get("score")[0].toString(),
											 item.get("found_items")[0].toString());
						
						// if a listener was provided 
						if(successListener != null)
						{
							// call it
							successListener.onUserDBAction(user);
						}
					}
					
					@Override
					public void mobDBErrorResponse(Integer errValue, String errMsg)
					{
						debug("error reading mobdb errorcode:" + String.valueOf(errValue) + " errMsg:" + errMsg);
						
						// if a listener was provided 
						if(errorListener != null)
						{
							// call it
							errorListener.onUserDBError(errValue, errMsg);
						}
					}

				    @Override
				    public void mobDBFileResponse(String fileName, byte[] fileData)
				    {
				    	debug("mobdb file response filename:" + fileName);
				    	
						// if a listener was provided 
						if(errorListener != null)
						{
							// call it
							errorListener.onUserDBError(0, "received file response when map expected.");
						}
				    }
				});		
	}
}
