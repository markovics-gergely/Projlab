package logic.icecells;

import logic.characters.Character;

public class UnstableIceCell extends IceCell  {
	private boolean hasIgloo = false;

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
	public void accept(Character ch) { }

}
