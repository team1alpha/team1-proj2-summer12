package mobile.group1.DB;

import java.util.HashMap;
import java.util.Vector;
import android.os.Bundle;
import com.mobdb.android.InsertRowData;

public class UserRecord extends MobDBRecord
{
	///////////////////////////////////////////////////////////////////////////
	// constants
	///////////////////////////////////////////////////////////////////////////

	// these tell client code where to find different data items in teh returned bundle
	public static String USER_NAMES  = "usernames";
	public static String USER_INFO   = "userinfo";
	
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
	
	public UserRecord(String username, ResponseListener listener)
	{
		super(listener);
		setUsername(username);
	}
	
	public UserRecord(String username, String password, String currentGame, String score, String foundItems, ResponseListener listener)
	{
		super(listener);
		setUsername(username);
		setPassword(password);
		setCurrentGame(currentGame);
		setScore(score);
		setFoundItems(foundItems);
	}

	@Override
	public String toString()
	{
		return username + ":" + password + ":" + currentGame + ":" + score + ":" + foundItems.toString();
	}
	
	///////////////////////////////////////////////////////////////////////////
	// implement interface of base class
	///////////////////////////////////////////////////////////////////////////	
	
	// tell parent class what our app key is
	@Override
	protected String AppKey()
	{
		return "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	}

	// tell parent class what table this item is in
	@Override
	protected String TableName()
	{
		return "users";
	}
	
	// tell the parent class the name of the key field in the table
	@Override
	protected String KeyField()
	{
		return "username";
	}
	
	// tell the parent class what key to search for row specific queries
	@Override
	protected String KeyValue()
	{
		return getUsername();
	}

	// tell the parent class what data to insert for an element of this type
	@Override
	protected InsertRowData Inserter()
	{
		InsertRowData insertRowData = new InsertRowData(TableName());
		insertRowData.setValue("username",     getUsername());
		insertRowData.setValue("password",     getPassword());
		insertRowData.setValue("current_game", getCurrentGame());
		insertRowData.setValue("score",        getScore());
		insertRowData.setValue("found_items",  getFoundItems());
		return insertRowData;
	}

	// tell the parent class what work to do prior to handing data back to the 
	// client code for this class.
	@Override
	protected Bundle PrepareResponse(RecordQueryType queryType, Bundle bundle)
	{
		switch(queryType)
		{
			///////////////////////////////////////////////////////////////////////
			// what work to do if the query was to get a single item
			///////////////////////////////////////////////////////////////////////
			case Get:
			{	
				@SuppressWarnings("unchecked")
				Vector<HashMap<String, Object[]>> result = 
						(Vector<HashMap<String, Object[]>>) bundle.getSerializable(MobDBTransaction.GET_RESULT);

				if(result.size() > 0)
				{
					HashMap<String, Object[]> item = result.get(0);
					
					setUsername(item.get("username")[0].toString());
					setPassword(item.get("password")[0].toString());
					setCurrentGame(item.get("current_game")[0].toString());
					setScore(item.get("score")[0].toString());
					setFoundItems(item.get("found_items")[0].toString());
					bundle.putString(USER_INFO, toString());
				}
			}
			break;	

			///////////////////////////////////////////////////////////////////////
			// what work to do if the query was to get all key names
			///////////////////////////////////////////////////////////////////////
			case GetAll:
			{
				@SuppressWarnings("unchecked")
				Vector<HashMap<String, Object[]>> result = 
						(Vector<HashMap<String, Object[]>>) bundle.getSerializable(MobDBTransaction.GET_RESULT);
				
				String[] usernames = new String[result.size()];
				
				for(int x = 0; x < result.size(); ++x)
				{
					usernames[x] = (String)result.get(x).get("username")[0];
				}
				
				bundle.putStringArray(USER_NAMES, usernames);
			}
			break;
			
			///////////////////////////////////////////////////////////////////////
			// what work to do if the query was to save a record
			///////////////////////////////////////////////////////////////////////
			case Save:
			{
				bundle.putString(USER_INFO, toString());
			}
			break;

			///////////////////////////////////////////////////////////////////////
			// what work to do if the query was to delete a record
			///////////////////////////////////////////////////////////////////////
			case Delete:
			{
				bundle.putString(USER_INFO, toString());
			}
			break;
		}
		
		return bundle;
	}

}
