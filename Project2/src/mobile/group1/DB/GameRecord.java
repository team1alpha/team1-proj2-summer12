package mobile.group1.DB;

import android.util.Log;

public class GameRecord
{
	private String   name;
	private String   started;
	private String   players;
	private String   items;

	public String   getName   (                ){ return name;           }
	public void     setName   (String name     ){ this.name = name;      }
	public String   isStarted (                ){ return started;        }
	public void     setStarted(String started  ){ this.started = started;}
	public String   getPlayers(                ){ return players;        }
	public void     setPlayers(String players  ){ this.players = players;}
	public String   getItems  (                ){ return items;          }
	public void     setItems  (String items    ){ this.items = items;    }

	public GameRecord(String name, String isStarted, String players, String items)
	{
		setName(name);
		setStarted(isStarted);
		setPlayers(players);
		setItems(items);
		Log.i("me", "loading game:" + toString());
	}

	public String toString()
	{
		return getName() + ":" + String.valueOf(isStarted()) + ":" + getPlayers() + ":" + getItems();
	}
}
