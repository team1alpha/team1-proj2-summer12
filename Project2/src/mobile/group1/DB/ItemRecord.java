package mobile.group1.DB;

import android.graphics.Bitmap;
import android.util.Log;

public class ItemRecord
{
	private Bitmap image;
	private String foundBy;
	private String name;

	public Bitmap getImage      (                  ){return image;                  }
	public void   setImage      (Bitmap image      ){this.image = image;            }
	public String getFoundBy    (                  ){return foundBy;                }
	public void   setFoundBy    (String foundBy    ){this.foundBy = foundBy;        }
	public String getName       (                  ){return name;                   }
	public void   setName       (String name       ){this.name = name;              }
	
	public ItemRecord(String name, String finder, Bitmap bitmap)
	{
		setName(name);
		setFoundBy(finder);
		setImage(bitmap);
		Log.i("me", "loaded item " + toString());
	}
	
	public String toString()
	{
		return getName() + ":" + getFoundBy() + ":<image>";
	}
}
