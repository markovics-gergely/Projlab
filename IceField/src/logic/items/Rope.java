package logic.items;

import logic.Way;
import logic.characters.Character;

public class Rope implements Items {

	public void use(Character actualch) {
		for(Way w : Way.values()) {
			//Ez hogy minden irányban végigpróbáljuk és szélen mentünk ki, akkor nem fog null-t dobni ha olyan irányban nézi ami nem létezik, beraktam, majd ki kell próbálni.
			if(actualch.getOwnCell().getNeighbour(w) != null) {
				if (actualch.getOwnCell().getNeighbour(w).movePlayerOut(w.opposite())) {
					actualch.loseOneAction();
					break;
				}
			}
		}
	}
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.savingWithRope);}
}
