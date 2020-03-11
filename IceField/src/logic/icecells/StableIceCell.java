package logic.icecells;

import logic.characters.Character;
import logic.items.Items;

public class StableIceCell extends IceCell  {
	private boolean hasIgloo = false;
	private Items item;

	private void removeItem() { item = null;}
	public void mine(Character actual) { }
	public void setIgloo(boolean b) { hasIgloo = true;}
	
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
