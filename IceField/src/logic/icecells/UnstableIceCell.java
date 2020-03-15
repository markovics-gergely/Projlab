package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Character;

public class UnstableIceCell extends IceCell  {
	private boolean hasIgloo = false;

	public UnstableIceCell(int c, IceField icef){
		super(c, icef);
	}

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
		if(standingPlayers.size() <= capacity)
			return;

		WaterCell wc = new WaterCell(ownField);
		for (Way w : Way.values()) {
			IceCell ic = getNeighbour(w);
			wc.addNeighbour(w, ic);
			ic.addNeighbour(w.opposite(), wc);
		}
		for(Character c : standingPlayers){
			wc.addCharacter(c);
			c.setOwnCell(wc);
			c.addOneTurnInWater();
		}
		wc.setBroken();

		ownField.removeIceCell(this);
		ownField.addIceCell(wc);
	}
}
