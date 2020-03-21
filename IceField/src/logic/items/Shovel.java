package logic.items;

import logic.characters.Character;

public class Shovel implements Items {

	public boolean use(Character actualch) {
		return actualch.dig(true);
	}
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.shoveling);}
}
