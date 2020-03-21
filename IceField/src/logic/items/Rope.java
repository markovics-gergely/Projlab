package logic.items;

import logic.Way;
import logic.characters.Character;

public class Rope implements Items {

	public boolean use(Character actualch) {
		boolean used = false;
		for(Way w : Way.values()) {
			if(actualch.getOwnCell().getNeighbour(w).movePlayerOut(w.opposite()))
				used = true;
		}
		return used;
	}
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.savingWithRope);}
}
