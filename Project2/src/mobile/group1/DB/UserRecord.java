package mobile.group1.DB;

import android.util.Log;

public class UserRecord
{
	private String name;
	private String password;
	private String currentGame;
	private String score;
	private String foundItems;

	public String getName()                        { return name;                    }
	public void setName(String username)           { this.name = username;           }
	public String getPassword()                    { return password;                }
	public void setPassword(String password)       { this.password = password;       }
	public String getCurrentGame()                 { return currentGame;             }
	public void setCurrentGame(String currentGame) { this.currentGame = currentGame; }
	public String getScore()                       { return score;                   }
	public void setScore(String score)             { this.score = score;             }
	public String getFoundItems()                  { return foundItems;              }
	public void setFoundItems(String foundItems)   { this.foundItems = foundItems;   }
	
	public UserRecord(String username, String password, String currentGame, String score, String foundItems)
	{
		setName(username);
		setPassword(password);
		setCurrentGame(currentGame);
		setScore(score);
		setFoundItems(foundItems);
		Log.i("me", "loading user:" + toString());
	}

	@Override
	public String toString()
	{
		return getName() + ":" + getPassword() + ":" + getCurrentGame() + ":" + getScore() + getFoundItems();
	}
}
