package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Character;
import logic.characters.Eskimo;

public class WaterCell extends IceCell {
	private boolean broken = false;

	public WaterCell(IceField icef){
		super(0, icef);
	}

	public void setBroken() { broken = true; }
	public void movePlayerOut(Way from) {
		if(standingPlayers.size() != 0){
			Character c = null;
			int i = 0;
			for(Character ch : standingPlayers){
				if(ch.getTurnsInWater() > i && ch.getDivingSuit()) {
					i = ch.getTurnsInWater();
					c = ch;
				}
			}
			if(i == 0){
				for(Character ch : standingPlayers){
					if(ch.getTurnsInWater() > i && !ch.getDivingSuit()) {
						i = ch.getTurnsInWater();
						c = ch;
					}
				}
			}
			c.setFacingWay(from.opposite());
			c.move();
			c.resetTurnsInWater();
		}
	}

	public void snowing() { if(!broken) gainOneSnow(); }
	public void accept(Character ch) {
		addCharacter(ch);
		ch.addOneTurnInWater();
		ch.setOwnCell(this);
		setBroken();
	}

}
