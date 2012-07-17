package mobile.group1.DB;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.mobdb.android.DeleteRowData;
import com.mobdb.android.GetFile;
import com.mobdb.android.GetRowData;
import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class MobDBTable
{
	private static String 	   APP_KEY     = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	private Vector<UserRecord> userRecords = new Vector<UserRecord>();
	private Vector<GameRecord> gameRecords = new Vector<GameRecord>();
	private Vector<ItemRecord> itemRecords = new Vector<ItemRecord>();
	private boolean            loaded;
	
	public boolean addUser(UserRecord newUser)
	{
		if(GetUserByName(newUser.getName()) == null)
		{
			userRecords.add(newUser);
			return true;
		}
		return false;
	}
	
	public boolean addGame(GameRecord newGame)
	{
		if(GetGameByName(newGame.getName()) == null)
		{
			gameRecords.add(newGame);
			return true;
		}
		return false;
	}
	
	public boolean addItem(ItemRecord newItem)
	{
		if(GetGameByName(newItem.getName()) == null)
		{
			itemRecords.add(newItem);
			return true;
		}
		return false;	
	}
	
	public UserRecord GetUserByName(String name)
	{
		for(UserRecord user : userRecords)
		{
			if(user.getName().equals(name))
			{
				return user;
			}
		}
		
		return null;
	}
	
	public GameRecord GetGameByName(String name)
	{
		for(int x = 0; x < gameRecords.size(); ++x)
		{
			if(gameRecords.get(x).getName().equals(name))
			{
				return gameRecords.get(x);
			}
		}
		
		return null;
	}
	
	public ItemRecord GetItemByName(String name)
	{
		for(int x = 0; x < itemRecords.size(); ++x)
		{
			if(itemRecords.get(x).getName().equals(name))
			{
				return itemRecords.get(x);
			}
		}
		
		return null;
	}
	
	public MobDBTable()
	{
		Load();
	}
	
	private class MobDBResponseListenerShort implements MobDBResponseListener
	{
		@Override public void mobDBSuccessResponse(){}
		@Override public void mobDBResponse(String jsonObj){}
		@Override public void mobDBFileResponse(String fileName, byte[] fileData){}
		@Override public void mobDBErrorResponse(Integer errValue, String errMsg){}
		@Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result){};
	}
	
	public void Load()
	{
		MobDB.getInstance().execute(APP_KEY, new GetRowData("users"), null, false, new MobDBResponseListenerShort()
		{
			@Override 
			public void mobDBResponse(Vector<HashMap<String, Object[]>> result)
			{
				for(int x = 0; x < result.size(); ++x)
				{
					HashMap<String, Object[]> item = result.get(x);
					String name      = item.get("name")[0].toString();
					String password  = item.get("password")[0].toString();
					String game      = item.get("current_game")[0].toString();
					String score     = item.get("score")[0].toString();
					String found     = item.get("found_items")[0].toString();
					
					userRecords.add(new UserRecord(name, password, game, score, found));
				}
			}
		}); 
		
		MobDB.getInstance().execute(APP_KEY, new GetRowData("games"), null, false, new MobDBResponseListenerShort()
		{
			@Override 
			public void mobDBResponse(Vector<HashMap<String, Object[]>> result)
			{
				for(int x = 0; x < result.size(); ++x)
				{
					HashMap<String, Object[]> item = result.get(x);
					String name      = item.get("name")[0].toString();
					String started   = item.get("started")[0].toString();
					String players   = item.get("players")[0].toString();
					String items     = item.get("items")[0].toString();
					
					gameRecords.add(new GameRecord(name, started, players, items));
				}
			}
		});
		
		MobDB.getInstance().execute(APP_KEY, new GetRowData("items"), null, false, new MobDBResponseListenerShort()
		{
			@Override 
			public void mobDBResponse(Vector<HashMap<String, Object[]>> result)
			{
				Vector<String> imageNames = new Vector<String>();
				
				for(int x = 0; x < result.size(); ++x)
				{
					HashMap<String, Object[]> item = result.get(x);
					String name          = item.get("name")[0].toString();
					String finder        = item.get("finder")[0].toString();
					imageNames.add(item.get("image")[0].toString());
					itemRecords.add(new ItemRecord(name, finder, null)); 
				}
				
				for(String name: imageNames)
				{
					MobDB.getInstance().execute(APP_KEY, new GetFile(name), null, false, new MobDBResponseListenerShort()
					{
						public void mobDBFileResponse(String fileName, byte[] fileData)
						{
							GetItemByName(fileName).setImage(BitmapFactory.decodeByteArray(fileData, 0, fileData.length));
							Log.i("me", "loaded image " + fileName);
						}
					});
				}
			}
		});	
	}
	
	public void Save()
	{
		MobDB.getInstance().execute(APP_KEY, new DeleteRowData("users"), null, false, new MobDBResponseListenerShort()
		{
			@Override
			public void mobDBSuccessResponse()
			{
				for(UserRecord user: userRecords)
				{
					InsertRowData insertRowData = new InsertRowData("users");
					insertRowData.setValue("username",     user.getName());
					insertRowData.setValue("password",     user.getPassword());
					insertRowData.setValue("current_game", user.getCurrentGame());
					insertRowData.setValue("score",        user.getScore());
					insertRowData.setValue("found_items",  user.getFoundItems());
					MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, null);
				}
			}
		});

		MobDB.getInstance().execute(APP_KEY, new DeleteRowData("games"), null, false, new MobDBResponseListenerShort()
		{
			@Override
			public void mobDBSuccessResponse()
			{
				for(GameRecord game: gameRecords)
				{
					InsertRowData insertRowData = new InsertRowData("games");
					insertRowData.setValue("name",         game.getName());
					insertRowData.setValue("started",      game.isStarted());
					insertRowData.setValue("players", 	   game.getPlayers());
					insertRowData.setValue("items",        game.getItems());
					
					MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, null);
				}
			}
		});
		
		MobDB.getInstance().execute(APP_KEY, new DeleteRowData("items"), null, false, new MobDBResponseListenerShort()
		{
			@Override
			public void mobDBSuccessResponse()
			{
				for(ItemRecord item: itemRecords)
				{
					InsertRowData insertRowData = new InsertRowData("items");
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					item.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
					insertRowData.setValue("image", "picture.bmp", stream.toByteArray());
					insertRowData.setValue("name", item.getName());
					insertRowData.setValue("finder", item.getFoundBy());
					
					MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, null);
				}
			}
		});
	}
}