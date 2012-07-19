package mobile.group1.DB;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ItemRecord
{
	private Bitmap image;
	private String foundBy;
	private String name;

	public Bitmap getImage      (                  ){return image;                  }
	public void   setImage      (Bitmap image      ){this.image = image;            }
	public String getFoundBy    (                  ){this.foundBy.replace("'", ""); return foundBy;                }
	public void   setFoundBy    (String foundBy    ){this.foundBy = foundBy;  this.foundBy.replace("'", "");      }
	public String getName       (                  ){this.name.replace("'", ""); return name;}
	
	public void   setName       (String name       ){this.name = name; this.name.replace("'", "");}
	
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
	
	public static Bitmap BitmapFromResouces(Resources res, int id)
	{
		return BitmapFactory.decodeResource(res, id);
	}
}
