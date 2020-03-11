package logic.items;

import logic.WinChecker;
import logic.characters.Character;

public class EssentialItem implements Items {
	private int ID;
	private WinChecker wc;

	public void use(Character actualch) { }
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.assemblingEssentials);}

}
