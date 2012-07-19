package mobile.group1.DB;

public class GameRecord
{
	/*
	 * Private vars
	 */
	private String   name;
	private String   started;
	private String   players;
	private String   items;

	/*
	 * getters
	 */
	public String   getName   (                ){ return name;           }
	public String   isStarted (                ){ return started;        }
	public String   getPlayers(                ){ return players;        }
	public String   getItems  (                ){ return items;          }

	/*
	 * setters
	 */
	public void     setName   (String name     ){ this.name = name;      }
	public void     setPlayers(String players  ){ this.players = players;}
	public void     setStarted(String started  ){ this.started = started;}
	public void     setItems  (String items    ){ this.items = items;    }

	/*
	 * Constructor
	 */
	public GameRecord(String name, String isStarted, String players, String items)
	{
		setName(name);
		setStarted(isStarted);
		setPlayers(players);
		setItems(items);
	}

	/*
	 * To String
	 */
	public String toString()
	{
		return getName() + ":" + String.valueOf(isStarted()) + ":" + getPlayers() + ":" + getItems();
	}
}
