package logic.items;

import logic.WinChecker;
import logic.characters.Character;

public class EssentialItem implements Items {
	private int ID;
	private WinChecker wc;

	public EssentialItem(int id, WinChecker winch){
		wc = winch;
		ID = id;
	}

	public void use(Character actualch) {
		int sum = actualch.getBackPack().getEssentialItemNumber();
		wc.addEssentials(sum);
	}
	public boolean equip(Character ch) { return ch.putItemtoBackPack(this, PlayerActions.assemblingEssentials);}

	public int getID(){ return ID; }
}
