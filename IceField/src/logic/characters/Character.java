package logic.characters;

import logic.icecells.IceCell;
import logic.items.EssentialItem;
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
	private Way facingWay;
	private IceCell ownCell;
	private BackPack backpack;

	Character(int mb, IceCell ic){
		maxBodyHeat = mb;
		bodyHeat = mb;
		ownCell = ic;
		backpack = new BackPack();
		facingWay = up;
	}

	public void move() {
		IceCell ic = ownCell.getNeighbour(facingWay);
		// random comment
		if(ic != null){
			ownCell.removeCharacter(this);
			ic.accept(this);
		}
	}
	public void dig(boolean withShovel) {
		ownCell.loseSnow(withShovel);
	}
	public void mine() { ownCell.mine(this); }
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
	public void useItem(PlayerActions pa) {
		Items i;
		if(pa == PlayerActions.eating)
			i = backpack.useFood();
		else
			i = backpack.hasItem(pa);
		if(i != null) i.use(this);
		else if(pa == PlayerActions.shovelling) dig(false);
	}
	public void useEssentials() {
		Items ei = backpack.hasItem(PlayerActions.assemblingEssentials);
		if(ei != null) ei.use(this);
	}
	public int getBodyHeat() { return bodyHeat; }
	public BackPack getBackPack(){ return backpack; }

	public abstract void ability();
}
