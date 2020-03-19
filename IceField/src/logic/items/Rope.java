package logic.items;

import logic.Way;
import logic.characters.Character;

public class Rope implements Items {

	public void use(Character actualch) {
		for(Way w : Way.values()) {
			actualch.getOwnCell().getNeighbour(w).movePlayerOut(w.opposite());
		}
	}
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.savingWithRope);}
}
