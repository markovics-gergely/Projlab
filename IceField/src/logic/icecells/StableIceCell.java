package logic.icecells;

import logic.IceField;
import logic.characters.Character;
import logic.items.Items;

public class StableIceCell extends IceCell  {
	private boolean hasIgloo = false;
	private boolean hasTent = false;
	private Items item;

	public int getIgloo(){ return hasIgloo ? 1 : 0; } //CSAK TESZT

	public StableIceCell(IceField icef, Items i){
		super(icef.getMaxPlayer(), icef);
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
	public boolean setTent(boolean b) {
		if(hasTent == b) return false;
		else {
			hasTent = b;
			return true;
		}
	}

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
