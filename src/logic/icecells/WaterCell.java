package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Bear;
import logic.characters.Character;
import logic.items.Items;

public class WaterCell extends IceCell {
	private boolean broken = false;

	public WaterCell(IceField icef){
		super(0, icef);
	}

	public void setBroken() { broken = true; snow = 0; }
	public boolean movePlayerOut(Way from) {
		Character chosen = ownField.getChosenToSave();
		if(chosen != null && standingPlayers.contains(chosen)){
			chosen.setFacingWay(from);
			chosen.move();
			chosen.resetTurnsInWater();
			return true;
		}
		return false;
	}

	public void snowing() { if(!broken) gainOneSnow(); }
	public void accept(Character ch) {
		addCharacter(ch);
		ch.addOneTurnInWater();
		ch.setOwnCell(this);
		setBroken();
	}

	//Kirajzolást segító függvények
	public boolean itHasIgloo(){ return false; }
}
