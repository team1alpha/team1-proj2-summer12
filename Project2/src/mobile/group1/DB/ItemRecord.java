package mobile.group1.DB;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import com.mobdb.android.GetFile;
import com.mobdb.android.InsertRowData;

public class ItemRecord extends MobDBRecord
{
	///////////////////////////////////////////////////////////////////////////
	// constants
	///////////////////////////////////////////////////////////////////////////

	// these tell client code where to find different data items in teh returned bundle
	public static String ITEM_NAMES  = "itemnames";
	public static String ITEM_INFO   = "iteminfo";
	public static String IMAGE       = "image";
	
	///////////////////////////////////////////////////////////////////////////
	// data vars
	///////////////////////////////////////////////////////////////////////////
	private Bitmap image;
	private String description;
	private String foundBy;
	private String name;
	private String imageName;

	///////////////////////////////////////////////////////////////////////////
	// data accessors
	///////////////////////////////////////////////////////////////////////////	
	public Bitmap getImage      (                  ){ return image;                 }
	public void   setImage      (Bitmap image      ){this.image = image;            }
	public String getDescription(                  ){return description;            }
	public void   setDescription(String description){this.description = description;}
	public String getFoundBy    (                  ){return foundBy;                }
	public void   setFoundBy    (String foundBy    ){this.foundBy = foundBy;        }
	public String getName       (                  ){return name;                   }
	public void   setName       (String name       ){this.name = name;              }
	public String getImageName  (                  ){return imageName;              }
	public void   setImageName  (String imageName  ){this.imageName = imageName;    }
	
	public ItemRecord(String name, ResponseListener listener)
	{
		super(listener);
		setName(name);
	}

	@Override
	protected String AppKey()
	{
		return "MIRoAA-5T3-uym202kOKKkKuIiZxZErELos-popgfD77YeatrtTsp6WoBmmM";		
	}

	@Override
	protected String TableName()
	{
		return "items";
	}

	@Override
	public String toString()
	{
		return getName() + ":" + getDescription() + ":" + getFoundBy() + " <image>";
	}

	@Override
	protected String KeyField()
	{
		return "name";
	}

	@Override
	protected String KeyValue()
	{
		return getName();
	}
	
	@Override
	protected InsertRowData Inserter()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected GetFile FileGetter()
	{
		return new GetFile(getImageName());
	}

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
					setName(item.get("name")[0].toString());
					setDescription(item.get("des")[0].toString());
					setImageName(item.get("image")[0].toString());
					setFoundBy(item.get("foundby")[0].toString());
					
					bundle.putString(ITEM_INFO, toString());
					
					GetFile();
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
					usernames[x] = (String)result.get(x).get("itemname")[0];
				}
				
				bundle.putStringArray(ITEM_NAMES, usernames);
			}
			break;
			
			///////////////////////////////////////////////////////////////////////
			// what work to do if the query was to save a record
			///////////////////////////////////////////////////////////////////////
			case Save:
			{
				bundle.putString(ITEM_INFO, toString());
			}
			break;

			///////////////////////////////////////////////////////////////////////
			// what work to do if the query was to delete a record
			///////////////////////////////////////////////////////////////////////
			case Delete:
			{
				bundle.putString(ITEM_INFO, toString());
			}
			break;
			
			case File:
			{
				byte[] data = bundle.getByteArray(MobDBTransaction.FILE_DATA);
				setImage(BitmapFactory.decodeByteArray(data, 0, data.length));
				bundle.putParcelable(IMAGE, getImage());
			}
			break;
		}
		
		return bundle;
	}

}
