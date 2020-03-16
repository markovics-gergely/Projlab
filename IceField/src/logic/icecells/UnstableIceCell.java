package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Character;

public class UnstableIceCell extends IceCell  {
	private boolean hasIgloo = false;

	public UnstableIceCell(int c, IceField icef){
		super(c, icef);
	}

	public boolean safeToStart(){ return false; }
	public void setIgloo(boolean b) { hasIgloo = true; }
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
	}
}
