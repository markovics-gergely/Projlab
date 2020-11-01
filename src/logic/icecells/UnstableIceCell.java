package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Bear;
import logic.characters.Character;
import logic.items.Items;

public class UnstableIceCell extends IceCell  {
	private boolean hasIgloo = false;
	private int tentTurnsLeft = 0;


	public UnstableIceCell(int c, IceField icef){
		super(c, icef);
	}

	public void loseOneTentTurn(){ tentTurnsLeft--; }
	public boolean setUpTent() {
		if(tentTurnsLeft == IceField.getMaxPlayer() || hasIgloo) return false;
		else {
			tentTurnsLeft = IceField.getMaxPlayer();
			return true;
		}
	}
	public void resetTentTurnsLeft() { tentTurnsLeft = 0; }
	public boolean setIgloo(boolean b) {
		if(hasIgloo == b || tentTurnsLeft > 0) return false;
		else {
			hasIgloo = b;
			return true;
		}
	}
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
		if(standingPlayers.size() <= capacity)
			return;

		WaterCell wc = new WaterCell(ownField);
		for (Way w : Way.values()) {
			IceCell ic = getNeighbour(w);
			if(ic != null){
				wc.addNeighbour(w, ic);
				ic.addNeighbour(w.opposite(), wc);
			}
		}
		for(Character c : standingPlayers){
			wc.addCharacter(c);
			c.setOwnCell(wc);
			c.addOneTurnInWater();
		}
		wc.setBroken();
		ownField.addIceCell(wc, this);
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
}
