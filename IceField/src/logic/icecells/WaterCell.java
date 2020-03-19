package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Character;

public class WaterCell extends IceCell {
	private boolean broken = false;

	public int getIgloo(){ return 0; }

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
			if(c != null) {
				c.setFacingWay(from);
				c.move();
				c.resetTurnsInWater();
			}
		}
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
