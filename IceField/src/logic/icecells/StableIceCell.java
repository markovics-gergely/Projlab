package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Character;
import logic.items.Items;

public class StableIceCell extends IceCell  {
	private boolean hasIgloo = false;
	private Items item;

	public StableIceCell(IceField icef, Items i){
		super(icef.getMaxPlayer(), icef);
		item = i;
	}

	private void removeItem() { item = null;}
	public void mine(Character ch) {
		if(snow == 0 && item != null){
			if(item.equip(ch)) removeItem();
		}
	}
	public void setIgloo(boolean b) { hasIgloo = true;}

	public boolean safeToStart(){ return true; }
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
	}
}
