package logic.characters;

import logic.IceField;
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
	private Way facingWay;
	private IceCell ownCell;
	private BackPack backpack;
	protected IceField ownField;

	Character(int mb, IceCell ic){
		maxBodyHeat = mb;
		bodyHeat = mb;
		ownCell = ic;
		backpack = new BackPack();
		facingWay = up;
	}

	public boolean gainOneHeat() {
		bodyHeat++;
		if(bodyHeat > maxBodyHeat) {
			bodyHeat = maxBodyHeat;
			return false;
		}
		return true;
	}
	public boolean dig(boolean withShovel) {
		if(!withShovel) ownField.actionHandler(false);
		return ownCell.loseSnow(withShovel);
	}
	public boolean wearDivingSuit() {
		if(wearingDivingSuit) return false;
		else {
			wearingDivingSuit = true;
			return true;
		}
	}
	public void useEssentials() {
		Items ei = backpack.hasItem(PlayerActions.assemblingEssentials);
		if(ei != null) ei.use(this);
	}
	public void useItem(PlayerActions pa) {
		Items i;
		if(pa == PlayerActions.eating)
			i = backpack.useFood();
		else
			i = backpack.hasItem(pa);
		if(i != null){
			if(!i.use(this)) ownField.actionHandler(false);
		}
		else if(pa == PlayerActions.shoveling) dig(false);
			//Nincs nála az adott tárgy és nem is lesz a dig miatt kétszer meghivva az actionHandler
		else ownField.actionHandler(false);
	}

	public void move() {
		IceCell ic = ownCell.getNeighbour(facingWay);
		if(ic != null){
			ownCell.removeCharacter(this);
			ic.accept(this);
		}
		if(ic == null) ownField.actionHandler(false);
	}
	public void mine() { ownCell.mine(this); }
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
	public boolean getDivingSuit() { return wearingDivingSuit;}
	public void setFacingWay(Way w) { facingWay = w;}
	public Way getFacingWay() { return facingWay;}
	public int getBodyHeat() { return bodyHeat; }
	public BackPack getBackPack(){ return backpack; }
	public void setOwnField(IceField of){ ownField = of;}

	public abstract void ability();
}
