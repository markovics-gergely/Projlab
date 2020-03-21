package logic.items;

import logic.characters.Character;

public class Divingsuit implements Items {

	public boolean use(Character actualch) {
		return actualch.wearDivingSuit();
	}
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.wearingSuit); }

}
