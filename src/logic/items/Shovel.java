package logic.items;

import logic.characters.Character;

public class Shovel implements Items {

	public void use(Character actualch) {
		if(actualch.dig(true))
			actualch.loseOneAction();
	}
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.shoveling);}
}
