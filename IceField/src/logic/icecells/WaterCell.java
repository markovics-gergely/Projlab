package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Character;

public class WaterCell extends IceCell {
	private boolean broken = false;

	public int getIgloo(){ return 0; } //CSAK TESZT

	public WaterCell(IceField icef){
		super(0, icef);
	}

	public void setBroken() { broken = true; }
	public boolean movePlayerOut(Way from) {
		Character chosen = ownField.getChosenToSave();
		if(chosen == null) return false;
		if(standingPlayers.contains(chosen)){
			chosen.setFacingWay(from);
			chosen.move();
			chosen.resetTurnsInWater();
		}
		return true;
	}

	public boolean setIgloo(boolean b) {
		return false;
	}
	public boolean safeToStart(){ return false; }
	public void snowing() { if(!broken) gainOneSnow(); }
	public void accept(Character ch) {
		addCharacter(ch);
		ch.addOneTurnInWater();
		ch.setOwnCell(this);
		setBroken();
	}
}
