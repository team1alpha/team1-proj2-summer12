package mobile.group1.DB;

import java.util.HashMap;
import java.util.Vector;

import android.graphics.BitmapFactory;

import com.mobdb.android.GetFile;
import com.mobdb.android.GetRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class MobDBTable
{
	private static String APP_KEY = "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";
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
		Retrieve();
	}
	
	private class MobDBResponseListenerShort implements MobDBResponseListener
	{
		@Override public void mobDBSuccessResponse(){}
		@Override public void mobDBResponse(String jsonObj){}
		@Override public void mobDBFileResponse(String fileName, byte[] fileData){}
		@Override public void mobDBErrorResponse(Integer errValue, String errMsg){}
		@Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result){};
	}
	
	private void loadUsers()
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
				
				loadGames();
			}
		}); 		
	}
	
	private void loadGames()
	{
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
				
				loadItems();
			}
		});		
	}
	
	private void loadItems()
	{
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
				
				loadImages(imageNames);
			}
		});				
	}
	
	private void loadImages(Vector<String> imageUrls)
	{
		for(String imageName: imageUrls)
		{
			MobDB.getInstance().execute(APP_KEY, new GetFile(imageName), null, false, new MobDBResponseListenerShort()
			{
				@Override public void mobDBFileResponse(String fileName, byte[] fileData)
				{
					ItemRecord item = GetItemByName(fileName);
					
					if(item != null)
					{
						item.setImage(BitmapFactory.decodeByteArray(fileData, 0, fileData.length));
					}
				}
			});
		}	
	}
	
	void Retrieve()
	{
		loadUsers();
	}
	
	void Save()
	{
		
	}
	

//	@Override
//	protected InsertRowData Inserter()
//	{
//		InsertRowData insertRowData = new InsertRowData(TableName());
//		insertRowData.setValue("username",     getUsername());
//		insertRowData.setValue("password",     getPassword());
//		insertRowData.setValue("current_game", getCurrentGame());
//		insertRowData.setValue("score",        getScore());
//		insertRowData.setValue("found_items",  getFoundItems());
//		return insertRowData;
//		
//	}
	// tell the parent class what data to insert for an element of this type
//	@Override
//	protected InsertRowData Inserter()
//	{
//		InsertRowData insertRowData = new InsertRowData(TableName());
//		insertRowData.setValue("gamename",     getName());
//		insertRowData.setValue("started",      isStarted());
//		insertRowData.setValue("players", 	   getPlayersAsString());
//		insertRowData.setValue("items",        getItemsAsString());
//
//		return insertRowData;
//	}
//
//	@Override
//	protected InsertRowData Inserter()
//	{
//		InsertRowData insertRowData = new InsertRowData(TableName());
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
//		insertRowData.setValue("image", "picture.bmp", stream.toByteArray());
//		insertRowData.setValue("name", getName());
//		insertRowData.setValue("des", getDescription());
//		insertRowData.setValue("foundby", getFoundBy());
//		return insertRowData;
//	}	
}
