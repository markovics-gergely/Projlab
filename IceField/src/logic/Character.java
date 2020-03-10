package logic;

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
	public void gainOneHeat() { }
	public void loseOneHeat() { }
	public void setOwnCell(IceCell ic) { }
	public IceCell getOwnCell() { }
	public void addOneTurnInWater() { }
	public int getTurnsInWater() { }
	public void resetTurnsInWater() { }
	public boolean putItemtoBackPack(Items it, PlayerActions ia) { }
	public void wearDivingSuit() { }
	public boolean getDivingSuit() { }
	public void setFacingWay(Way w) { }
	public Way getFacingWay() { }
	public void useItem(PlayerActions ia) { }
	public void useEssentials() { }
	public int getBodyHeat() { }
}
