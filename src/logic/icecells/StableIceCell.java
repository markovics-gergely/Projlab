package logic.icecells;

import logic.IceField;
import logic.characters.Bear;
import logic.characters.Character;
import logic.items.EssentialItem;
import logic.items.Items;
import logic.items.PlayerActions;

import java.util.HashMap;

public class StableIceCell extends IceCell  {
	private boolean hasIgloo = false;
	private int tentTurnsLeft = 0;
	private Items item;
	PlayerActions playerAct;


	public StableIceCell(IceField icef, Items i, PlayerActions pa){
		super(IceField.getMaxPlayer(), icef);
		item = i;
		playerAct = pa;
	}

	private void removeItem() { item = null;}
	public void mine(Character ch) {
		if(snow == 0 && item != null){
			if(item.equip(ch)){
				removeItem();
				ch.loseOneAction();
			}
		}
	}
	public boolean setIgloo(boolean b) {
		if(hasIgloo == b || tentTurnsLeft > 0) return false;
		else {
			hasIgloo = b;
			return true;
		}
	}
	public boolean setUpTent() {
		if(tentTurnsLeft == IceField.getMaxPlayer() || hasIgloo) return false;
		else {
			tentTurnsLeft = IceField.getMaxPlayer();
			return true;
		}
	}
	public void loseOneTentTurn(){ tentTurnsLeft--; }
	public void resetTentTurnsLeft() { tentTurnsLeft = 0; }
	public boolean safeToStart(){ return bear == null; }
	public void snowing() {
		gainOneSnow();
		if(!hasIgloo && tentTurnsLeft == 0){
			for(Character ch : standingPlayers){
				ch.loseOneHeat();
			}
		}
		if(hasIgloo && tentTurnsLeft != 0) resetTentTurnsLeft();
		else if(hasIgloo) setIgloo(false);
		else if (tentTurnsLeft != 0) resetTentTurnsLeft();
	}
	public void accept(Character ch) {
		addCharacter(ch);
		ch.setOwnCell(this);
		if(bear != null)
			ownField.gameLost("Megtámadott a medve!");
	}
	public boolean acceptBear(Bear b){
		bear = b;
		b.setOwnCell(this);
		if(!standingPlayers.isEmpty() && !hasIgloo)
			ownField.gameLost("Megtámadott a medve!");
		return true;
	}

	//Kirajzolást segító függvények
	public boolean itHasIgloo(){ return hasIgloo; }
	public int getTentTurnsLeft(){ return tentTurnsLeft; }
	public boolean hasItem(PlayerActions pa){
		if(playerAct == pa && item != null)
			return true;
		return false;
	}
	public EssentialItem getEssential(){
		return (EssentialItem)item;
	}
}
