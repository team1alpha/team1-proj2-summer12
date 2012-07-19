package mobile.group1.DB;

public class UserRecord
{
	/*
	 * Private vars
	 */
	private String  name;
	private String  password;
	private String  currentGame;
	private String  score;
	private String  foundItems;

	/*
	 * Getters
	 */
	public String  getName()                         { return name;                   }
	public String  getPassword()                     { return password;               }
	public String  getCurrentGame()                  { return currentGame;            }
	public String  getScore()                        { return score;                  }
	public String  getFoundItems()                   { return foundItems;             }
	
	/*
	 * Setters
	 */
	public void   setScore(String score)             { this.score = score;            }
	public void   setFoundItems(String foundItems)   { this.foundItems = foundItems;  }
	public void   setName(String username)           { this.name = username;          }
	public void   setCurrentGame(String currentGame) { this.currentGame = currentGame;}
	public void   setPassword(String password)       { this.password = password;      }

	/*
	 * Constructor to call from database client code to create a new previously unsaved user
	 */
	public UserRecord(String username, String password, String currentGame, String score, String foundItems)
	{
		setName(username);
		setPassword(password);
		setCurrentGame(currentGame);
		setScore(score);
		setFoundItems(foundItems);
	}

	@Override
	public String toString()
	{
		return getName() + ":" + getPassword() + ":" + getCurrentGame() + ":" + getScore() + getFoundItems();
	}
}
