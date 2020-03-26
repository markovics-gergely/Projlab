package logic.items;

import logic.characters.Character;

public class Divingsuit implements Items {

	public void use(Character actualch) {
		actualch.wearDivingSuit();
	}
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.wearingSuit); }

}
