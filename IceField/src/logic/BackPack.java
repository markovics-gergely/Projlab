package logic;
import java.util.HashMap;

public class BackPack {
	private HashMap<PlayerActions, Items> obtainedItems;

	public Items hasItem(PlayerActions it) { }
	public boolean addItem(Items it, PlayerActions ia) { }
	public Food useFood() { }
	public int getEssentialItemNumber() { }
}
