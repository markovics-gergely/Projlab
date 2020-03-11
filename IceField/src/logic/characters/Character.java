package logic.characters;

import logic.icecells.IceCell;
import logic.items.Items;
import logic.items.PlayerActions;
import logic.Way;
import logic.items.BackPack;

import java.util.HashMap;
import static logic.Way.up;

public abstract class Character {
	private int maxBodyHeat;
	private int bodyHeat;
	private int turnsInWater = 0;
	private boolean wearingDivingSuit = false;
	private Way facingWay = up;
	private IceCell ownCell;
	private BackPack backpack;

	public abstract void ability();

	public void move() { }
	public void dig(boolean withShovel) { }
	public void mine() { }
	public void gainOneHeat() {
		bodyHeat++;
		if(bodyHeat > maxBodyHeat) bodyHeat = maxBodyHeat;
	}
	public void loseOneHeat() {
		bodyHeat--;
		if(bodyHeat < 0) bodyHeat = 0;
	}
	public void setOwnCell(IceCell ic) { ownCell = ic;}
	public IceCell getOwnCell() { return ownCell;}
	public void addOneTurnInWater() { turnsInWater++;}
	public int getTurnsInWater() { return turnsInWater;}
	public void resetTurnsInWater() { turnsInWater = 0;}
	public boolean putItemtoBackPack(Items it, PlayerActions pa) { return backpack.addItem(it, pa); }
	public void wearDivingSuit() { wearingDivingSuit = true;}
	public boolean getDivingSuit() { return wearingDivingSuit;}
	public void setFacingWay(Way w) { facingWay = w;}
	public Way getFacingWay() { return facingWay;}
	public void useItem(PlayerActions ia) { }
	public void useEssentials() { }
	public int getBodyHeat() { return bodyHeat;}
}
