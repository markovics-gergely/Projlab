package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Bear;
import logic.characters.Character;

public class WaterCell extends IceCell {
	private boolean broken = false;

	public int getIgloo(){ return 0; } //CSAK TESZT
	public int getTent(){ return 0; } //CSAK TESZT

	public WaterCell(IceField icef){
		super(0, icef);
	}

	public void setBroken() { broken = true; }
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
	public boolean safeToStart(){ return false; }
	public void snowing() { if(!broken) gainOneSnow(); }
	public void accept(Character ch) {
		addCharacter(ch);
		ch.addOneTurnInWater();
		ch.setOwnCell(this);
		setBroken();
	}
}
