package logic.icecells;

import logic.IceField;
import logic.characters.Bear;
import logic.characters.Character;
import logic.items.Items;

public class StableIceCell extends IceCell  {
	private boolean hasIgloo = false;
	private int tentTurnsLeft = 0;
	private Items item;

	public int getIgloo(){ return hasIgloo ? 1 : 0; } //CSAK TESZT
	public int getTentTurnsLeft(){ return tentTurnsLeft; } //CSAK TESZT

	public StableIceCell(IceField icef, Items i){
		super(IceField.getMaxPlayer(), icef);
		item = i;
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
		if(hasIgloo == b) return false;
		else {
			hasIgloo = b;
			return true;
		}
	}
	public boolean setUpTent() {
		if(tentTurnsLeft == IceField.getMaxPlayer()) return false;
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
		if(bear != null) ownField.gameLost();
	}
	public boolean acceptBear(Bear b){
		bear = b;
		b.setOwnCell(this);
		if(!standingPlayers.isEmpty() && !hasIgloo) ownField.gameLost();
		return true;
	}
}
