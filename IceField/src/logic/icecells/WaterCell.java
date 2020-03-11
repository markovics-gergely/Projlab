package logic.icecells;

import logic.Way;
import logic.characters.Character;

public class WaterCell extends IceCell {
	private boolean broken = false;

	private void setBroken() { broken = true; }
	public void movePlayerOut(Way from) { }

	public void snowing() { if(!broken) gainOneSnow(); }
	public void accept(Character ch) { }

}
