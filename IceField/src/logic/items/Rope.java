package logic.items;

import logic.characters.Character;

public class Rope implements Items {

	public void use(Character actualch) { }
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.savingWithRope);}

}
