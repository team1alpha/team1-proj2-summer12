package mobile.group1.DB;

public class Game 
{
	private String   name;
	private boolean  started;
	private String[] players;
	private int[]    items;
	private String   placeholder1;
	private String   placeholder2;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	public String[] getPlayers() {
		return players;
	}
	public void setPlayers(String[] players) {
		this.players = players;
	}
	public int[] getItems() {
		return items;
	}
	public void setItems(int[] items) {
		this.items = items;
	}
	public String getPlaceholder1() {
		return placeholder1;
	}
	public void setPlaceholder1(String placeholder1) {
		this.placeholder1 = placeholder1;
	}
	public String getPlaceholder2() {
		return placeholder2;
	}
	public void setPlaceholder2(String placeholder2) {
		this.placeholder2 = placeholder2;
	}
}
