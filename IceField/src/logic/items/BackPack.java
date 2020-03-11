package logic.items;

import java.util.HashMap;

public class BackPack {
	private HashMap<PlayerActions, Items> obtainedItems;

	public Items hasItem(PlayerActions pa) {
		//Kaja még kell.
		if(obtainedItems.containsKey(pa))
			return obtainedItems.get(pa);
		return null;
	}
	public boolean addItem(Items it, PlayerActions pa) {
		if(obtainedItems.get(pa) != null){
			return false;
		}
		return true;
	}
	public Food useFood() {
		Food f = new Food();

		if(obtainedItems.get(PlayerActions.eating) != null)
			f = (Food)obtainedItems.get(PlayerActions.eating);
		obtainedItems.remove(PlayerActions.eating);

		return f;
	}
	public int getEssentialItemNumber() {
		return 0;
	}
}
