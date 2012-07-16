package mobile.group1.DB;

import java.util.HashMap;
import java.util.Vector;
import android.os.Bundle;
import com.mobdb.android.InsertRowData;
import com.mobdb.android.GetFile;

public class GameRecord extends MobDBRecord
{
	///////////////////////////////////////////////////////////////////////////
	// constants
	///////////////////////////////////////////////////////////////////////////

	// these tell client code where to find different data items in teh returned bundle
	public static String GAME_NAMES  = "gamenames";
	public static String GAME_INFO   = "gameinfo";
	
	///////////////////////////////////////////////////////////////////////////
	// data vars
	///////////////////////////////////////////////////////////////////////////
	private String   name;
	private int      started;
	private String[] players;
	private String[] items;
	
	///////////////////////////////////////////////////////////////////////////
	// data accessors
	///////////////////////////////////////////////////////////////////////////	
	public String   getName   (                ){return name;            }
	public void     setName   (String name     ){this.name = name;       }
	public int      isStarted (                ){return started;         }
	public void     setStarted(int started     ){this.started = started; }
	public String[] getPlayers(                ){ return players;        }
	public void     setPlayers(String[] players){ this.players = players;}
	public String[] getItems  (                ){ return items;          }
	public void     setItems  (String[] items  ){ this.items = items;    }
	
	public void setPlayers(String playerString)
	{
		setPlayers(playerString.split(";"));
	}
	
	public void setItems(String itemsString)
	{
		setItems(itemsString.split(";"));
	}
	
	public String getPlayersAsString()
	{
		String playerString = new String();
		for(String s: getPlayers())
		{
			playerString += s;
			playerString += ";";
		}
		
		return playerString;
	}
	
	public String getItemsAsString()
	{
		String itemsString = new String();
		for(String s: getItems())
		{
			itemsString += s;
			itemsString += ";";
		}
		return itemsString;
	}

	///////////////////////////////////////////////////////////////////////////	
	// instance functions
	///////////////////////////////////////////////////////////////////////////	
	public GameRecord(String name, ResponseListener listener)
	{
		super(listener);
		setName(name);
	}

	public GameRecord(String name, int isStarted, String[] players, String[] items, ResponseListener listener)
	{
		super(listener);
		setName(name);
		setStarted(isStarted);
		setPlayers(players);
		setItems(items);
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
		return "games";
	}
	
	// tell the parent class the name of the key field in the table
	@Override
	protected String KeyField()
	{
		return "gamename";
	}
	
	// tell the parent class what key to search for row specific queries
	@Override
	protected String KeyValue()
	{
		return getName();
	}

	// tell the parent class what data to insert for an element of this type
	@Override
	protected InsertRowData Inserter()
	{
		InsertRowData insertRowData = new InsertRowData(TableName());
		insertRowData.setValue("gamename",     getName());
		insertRowData.setValue("started",      isStarted());
		insertRowData.setValue("players", 	   getPlayersAsString());
		insertRowData.setValue("items",        getItemsAsString());

		return insertRowData;
	}
	
	// no files in this table ignore
	@Override
	protected GetFile FileGetter()
	{
		return null;
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
					
					setName(item.get("gamename")[0].toString());
					setStarted((Integer) item.get("started")[0]);
					setPlayers(item.get("players")[0].toString().split(";"));
					setItems(item.get("items")[0].toString().split(";"));
					bundle.putString(GAME_INFO, toString());
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
					usernames[x] = (String)result.get(x).get("gamename")[0];
				}
				
				bundle.putStringArray(GAME_NAMES, usernames);
			}
			break;
			
			///////////////////////////////////////////////////////////////////////
			// what work to do if the query was to save a record
			///////////////////////////////////////////////////////////////////////
			case Save:
			{
				bundle.putString(GAME_INFO, toString());
			}
			break;

			///////////////////////////////////////////////////////////////////////
			// what work to do if the query was to delete a record
			///////////////////////////////////////////////////////////////////////
			case Delete:
			{
				bundle.putString(GAME_INFO, toString());
			}
			break;
			
			///////////////////////////////////////////////////////////////////////
			// any other case ignore
			///////////////////////////////////////////////////////////////////////
			default:
				
		}
		
		return bundle;
	}
	@Override
	public String toString()
	{
		return getName() + ":" + String.valueOf(isStarted()) + ":" + getPlayersAsString() + ":" + getItemsAsString();
	}
}
