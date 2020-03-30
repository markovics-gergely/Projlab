package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Bear;
import logic.characters.Character;

public class UnstableIceCell extends IceCell  {
	private boolean hasIgloo = false;
	private boolean hasTent = false;

	public int getIgloo(){ return hasIgloo ? 1 : 0; } //CSAK TESZT

	public UnstableIceCell(int c, IceField icef){
		super(c, icef);
	}

	public boolean safeToStart(){ return false; }
	public boolean setTent(boolean b) {
		if(hasTent == b) return false;
		else {
			hasTent = b;
			return true;
		}
	}
	public boolean setIgloo(boolean b) {
		if(hasIgloo == b) return false;
		else {
			hasIgloo = b;
			return true;
		}
	}
	public void snowing() {
		gainOneSnow();
		if(!hasIgloo){
			for(Character ch : standingPlayers){
				ch.loseOneHeat();
			}
		}
		setIgloo(false);
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
		if(bear != null) ownField.gameLost();
	}

	public boolean acceptBear(Bear b){
		bear = b;
		b.setOwnCell(this);
		if(!standingPlayers.isEmpty() && !hasIgloo) ownField.gameLost();
		return true;
	}
}
