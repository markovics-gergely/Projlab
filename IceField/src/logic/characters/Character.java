package logic.characters;

import logic.icecells.IceCell;
import logic.items.Items;
import logic.items.PlayerActions;
import logic.Way;
import logic.items.BackPack;

import static logic.Way.up;

public abstract class Character {
	private int maxBodyHeat;
	private int bodyHeat;
	private int turnsInWater = 0;
	private boolean wearingDivingSuit = false;
	private Way facingWay = Way.up;
	private IceCell ownCell;
	private BackPack backpack;
	private static int maxActions = 4;
	private int actionsLeft;

	Character(int mb, IceCell ic){
		maxBodyHeat = mb;
		bodyHeat = maxBodyHeat;
		ownCell = ic;
		backpack = new BackPack();
		actionsLeft = maxActions;
	}

	public void move() {
		IceCell ic = ownCell.getNeighbour(facingWay);
		if(ic != null){
			ownCell.removeCharacter(this);
			ic.accept(this);
			loseOneAction();
		}
	}
	public void dig(boolean withShovel) {
		if(ownCell.loseSnow(withShovel)) loseOneAction();
	}
	public void mine() { ownCell.mine(this); }
	public void gainOneHeat() {
		if(bodyHeat != maxBodyHeat){
			bodyHeat++;
			loseOneAction();
		}
	}
	public void loseOneHeat() {
		if(bodyHeat != 0) bodyHeat--;
	}
	public void setOwnCell(IceCell ic) { ownCell = ic;}
	public IceCell getOwnCell() { return ownCell;}
	public void addOneTurnInWater() { turnsInWater++;}
	public int getTurnsInWater() { return turnsInWater;}
	public void resetTurnsInWater() { turnsInWater = 0;}
	public boolean putItemtoBackPack(Items it, PlayerActions pa) { return backpack.addItem(it, pa); }
	public void wearDivingSuit() {
		if(!wearingDivingSuit){
			wearingDivingSuit = true;
			loseOneAction();
		}
	}
	public boolean getDivingSuit() { return wearingDivingSuit;}
	public void setFacingWay(Way w) { facingWay = w;}
	public Way getFacingWay() { return facingWay;}
	public void useItem(PlayerActions pa) {
		Items item;
		if(pa == PlayerActions.eating && bodyHeat != maxBodyHeat)
			item = backpack.useFood();
		else
			item = backpack.hasItem(pa);
		if(item != null){
			item.use(this);
		}
		else if(pa == PlayerActions.shoveling)
			dig(false);
	}
	public void useEssentials() {
		Items ei = backpack.hasItem(PlayerActions.assemblingEssentials);
		if(ei != null) ei.use(this);
	}
	public int getBodyHeat() { return bodyHeat; }
	public BackPack getBackPack(){ return backpack; }
	public int getActionsLeft(){ return actionsLeft; }
	public void resetActionsLeft(){ actionsLeft = maxActions; }
	public void loseOneAction(){ actionsLeft--; }

	public abstract void ability();
}
