package logic.items;

import logic.characters.Character;

public class Food implements Items {

	public boolean use(Character actualch) {
		return actualch.gainOneHeat();
	}
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.eating);}
}
