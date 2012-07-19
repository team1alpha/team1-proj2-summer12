package mobile.group1.DB;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Vector;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.mobdb.android.GetFile;
import com.mobdb.android.GetRowData;
import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;
import com.mobdb.android.UpdateRowData;

public class MobDBTable
{
	private static String 	   APP_KEY     = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
	private Vector<UserRecord> userRecords = new Vector<UserRecord>();
	private Vector<GameRecord> gameRecords = new Vector<GameRecord>();
	private Vector<ItemRecord> itemRecords = new Vector<ItemRecord>();
	
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
		public String msg;
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
					String game      = item.get("game")[0].toString();
					String score     = item.get("score")[0].toString();
					String found     = item.get("found")[0].toString();
					
					UserRecord user = new UserRecord(name, password, game, score, found);
					userRecords.add(user);
					Log.e("me", "loaded user ->" + user.toString());
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
					
					GameRecord record = new GameRecord(name, started, players, items);
					gameRecords.add(record);
					Log.e("me", "loaded game ->" + record.toString());
				}
			}
		});
		
		MobDB.getInstance().execute(APP_KEY, new GetRowData("items"), null, false, new MobDBResponseListenerShort()
		{
			@Override 
			public void mobDBResponse(Vector<HashMap<String, Object[]>> result)
			{
				for(int x = 0; x < result.size(); ++x)
				{
					HashMap<String, Object[]> item = result.get(x);
					final String name          = item.get("name")[0].toString().replace("'", "");
					final String finder        = item.get("finder")[0].toString().replace(",", ",");
					final String image         = item.get("image")[0].toString().replace(",", ",");
					
					Log.e("", "------>" + name + " ------->" + finder + "------->" + image);
				
					MobDB.getInstance().execute(APP_KEY, new GetFile(image), null, false, new MobDBResponseListenerShort()
					{
						public void mobDBFileResponse(String fileName, byte[] fileData)
						{
							ItemRecord record = new ItemRecord(name, finder, BitmapFactory.decodeByteArray(fileData, 0, fileData.length)); 
							itemRecords.add(record);
							Log.e("me", "loaded image " + fileName);
						}
					});
				}

			}
		});	
	}
	
	public void Save()
	{
		///////////////////////////////////////////////////////////////////////
		// save all new users update all existing
		///////////////////////////////////////////////////////////////////////		
		for(final UserRecord user: userRecords)
		{
			Log.e("me", "Saving user -> " + user.toString());
			UpdateRowData updateRowData = new UpdateRowData("users");
			updateRowData.setValue("name", user.getName());
			updateRowData.setValue("password",     user.getPassword());
			updateRowData.setValue("game", user.getCurrentGame());
			updateRowData.setValue("score",        user.getScore());
			updateRowData.setValue("found",  user.getFoundItems());
			updateRowData.whereEqualsTo("name", user.getName());
			
			MobDB.getInstance().execute(APP_KEY, updateRowData, null, false, new MobDBResponseListenerShort() 
			{
				@Override
				public void mobDBSuccessResponse() 
				{
					Log.e("me", "successfully updated user ->" + user.toString());
				}
				
				@Override
				public void mobDBErrorResponse(Integer errValue, String errMsg) 
				{
					InsertRowData insertRowData = new InsertRowData("users");
					insertRowData.setValue("name",     user.getName());
					insertRowData.setValue("password",     user.getPassword());
					insertRowData.setValue("game", user.getCurrentGame());
					insertRowData.setValue("score",        user.getScore());
					insertRowData.setValue("found",  user.getFoundItems());
	
					MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, new MobDBResponseListenerShort() 
					{
						@Override
						public void mobDBSuccessResponse() 
						{
							Log.e("me", "successfully saved new user ->" + user.toString());
						}
					});
				}
			});
		}

		///////////////////////////////////////////////////////////////////////
		// save all new games update all existing
		///////////////////////////////////////////////////////////////////////		

		for(final GameRecord game: gameRecords)
		{
			Log.e("me", "Saving game -> " + game.toString());
			
			UpdateRowData updateRowData = new UpdateRowData("games");
			updateRowData.setValue("name",         game.getName());
			updateRowData.setValue("started",      game.isStarted());
			updateRowData.setValue("players", 	   game.getPlayers());
			updateRowData.setValue("items",        game.getItems());
			updateRowData.whereEqualsTo("name",  game.getName());
			
			MobDB.getInstance().execute(APP_KEY, updateRowData, null, false, new MobDBResponseListenerShort()
			{
				@Override
				public void mobDBSuccessResponse() 
				{
					Log.e("me", "successfully updated game ->" + game.toString());
				}
				
				@Override
				public void mobDBErrorResponse(Integer errValue, String errMsg) 
				{
					InsertRowData insertRowData = new InsertRowData("games");
					insertRowData.setValue("name",         game.getName());
					insertRowData.setValue("started",      game.isStarted());
					insertRowData.setValue("players", 	   game.getPlayers());
					insertRowData.setValue("items",        game.getItems());
					
					MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, new MobDBResponseListenerShort() 
					{
						@Override
						public void mobDBSuccessResponse() 
						{
							Log.e("me", "successfully saved new game ->" + game.toString());
						}
					});
				}
			});
		}
		
		///////////////////////////////////////////////////////////////////////
		// save all new users update all existing
		///////////////////////////////////////////////////////////////////////		
		for(final ItemRecord item: itemRecords)
		{
			Log.e("me", "Saving item -> " + item.toString());
			
			UpdateRowData updateRowData = new UpdateRowData("items");
			updateRowData.setValue("name", item.getName());
			updateRowData.setValue("finder", item.getFoundBy());
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			item.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
			updateRowData.setValue("image", item.getName(), stream.toByteArray());
			updateRowData.whereEqualsTo("name", item.getName());
			
			//Log.e("me", updateRowData.getQueryString());
			//updateRowData = new UpdateRowData(updateRowData.getQueryString().replace("'", ""));
			//Log.e("me", updateRowData.getQueryString());
			
			MobDB.getInstance().execute(APP_KEY, updateRowData, null, false, new MobDBResponseListenerShort()
			{
				@Override
				public void mobDBSuccessResponse() 

				{
					Log.e("me", "item name ->" + item.getName());
					Log.e("me", "successfully updated item ->" + item.toString());
				}
				
				@Override
				public void mobDBErrorResponse(Integer errValue, String errMsg) 
				{
					InsertRowData insertRowData = new InsertRowData("items");

					insertRowData.setValue("name", item.getName());

					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					item.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
					insertRowData.setValue("image", item.getName(), stream.toByteArray());
					
					insertRowData.setValue("finder", item.getFoundBy());
					
					Log.e("", "------>" + item.getName() + " ------->" + item.getFoundBy() + "------->" + "<image>");
					
					MobDB.getInstance().execute(APP_KEY, insertRowData, null, false, new MobDBResponseListenerShort()
					{
						public void mobDBSuccessResponse() 
						{
							Log.e("me", "successfully saved new item ->" + item.toString());	
						}
					});
				}
			});
		}
	}
}