package logic.items;

import java.util.ArrayList;
import java.util.HashMap;

public class BackPack {
	private HashMap<PlayerActions, ArrayList<Items>> obtainedItems = new HashMap<>();

	public Items hasItem(PlayerActions pa) {
		return obtainedItems.containsKey(pa) ? obtainedItems.get(pa).get(0) : null;
	}
	public boolean addItem(Items it, PlayerActions pa) {
		if(obtainedItems.containsKey(pa) && pa != PlayerActions.eating && pa != PlayerActions.assemblingEssentials){
			return false;
		}
		ArrayList<Items> array = new ArrayList<>();
		array.addAll(obtainedItems.get(pa));
		array.add(it);
		obtainedItems.put(pa, array);
		return true;
	}
	public Food useFood() {
		Food f = null;

		if(obtainedItems.containsKey(PlayerActions.eating)){
			int lastIndex = obtainedItems.get(PlayerActions.eating).size()-1;
			f = (Food)obtainedItems.get(PlayerActions.eating).get(lastIndex);
			obtainedItems.get(PlayerActions.eating).remove(lastIndex);
		}

		return f;
	}
	public int getEssentialItemNumber() {
		return obtainedItems.get(PlayerActions.assemblingEssentials).size();
	}
}
