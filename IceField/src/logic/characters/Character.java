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

	Character() {

	}

	public void move() {

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
	public boolean putItemtoBackPack() {}
	public void wearDivingSuit() {

	}
	public boolean getDivingSuit() { }
	public void setFacingWay(Way w) { }
	public Way getFacingWay() { }
	public void useItem(PlayerActions pa) {

	}
	public void useEssentials() {

	}
	public int getBodyHeat() { }
	public BackPack getBackPack(){ }
	public int getActionsLeft(){  }
	public void resetActionsLeft(){  }
	public void loseOneAction(){  }

	public abstract void ability();
}
