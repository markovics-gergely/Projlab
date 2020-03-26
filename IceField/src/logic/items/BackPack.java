package logic.items;

import java.util.ArrayList;
import java.util.HashMap;

public class BackPack {
	private HashMap<PlayerActions, ArrayList<Items>> obtainedItems = new HashMap<>();

	public Items hasItem(PlayerActions pa) {
		return obtainedItems.containsKey(pa) ? obtainedItems.get(pa).get(0) : null;
	}
	public boolean addItem(Items it, PlayerActions pa) {
		if(obtainedItems.containsKey(pa) && pa != PlayerActions.eating && pa != PlayerActions.assemblingEssentials)
			return false;

		ArrayList<Items> array = new ArrayList<>();
		if(obtainedItems.containsKey(pa))
			array.addAll(obtainedItems.get(pa));
		array.add(it);
		obtainedItems.put(pa, array);
		return true;
	}
	public Food useFood() {
		Food f = null;

		if(obtainedItems.containsKey(PlayerActions.eating) && obtainedItems.get(PlayerActions.eating).size() != 0){
			int lastIndex = obtainedItems.get(PlayerActions.eating).size()-1;
			f = (Food)obtainedItems.get(PlayerActions.eating).get(lastIndex);
			obtainedItems.get(PlayerActions.eating).remove(lastIndex);
		}

		return f;
	}
	public int getEssentialItemNumber() {
		return obtainedItems.containsKey(PlayerActions.assemblingEssentials) ? obtainedItems.get(PlayerActions.assemblingEssentials).size() : 0;
	}
	public int getNumber(PlayerActions pa){ return obtainedItems.containsKey(pa) ? obtainedItems.get(pa).size() : 0; } //CSAK TESZT
}
