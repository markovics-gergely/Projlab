package logic.items;

import java.util.ArrayList;
import java.util.HashMap;

public class BackPack {
	private HashMap<PlayerActions, ArrayList<Items>> obtainedItems;

	public Items hasItem(PlayerActions pa) {
		//Nem raktuk if contains be, mert ha nincs akkor null-t ad vissza
		return obtainedItems.get(pa).get(0);
	}
	public boolean addItem(Items it, PlayerActions pa) {
		if(obtainedItems.get(pa) != null){
			return false;
		}
		return true;
	}
	public Food useFood() {
		Food f = null;

		if(obtainedItems.get(PlayerActions.eating) != null)
			f = (Food)obtainedItems.get(PlayerActions.eating).get(obtainedItems.get(PlayerActions.eating).size()-1);
		obtainedItems.get(PlayerActions.eating).remove(obtainedItems.get(PlayerActions.eating).size()-1);

		return f;
	}
	public int getEssentialItemNumber() {
		return obtainedItems.get(PlayerActions.assemblingEssentials).size();
	}
}
