package logic.characters;

import logic.Way;
import logic.icecells.IceCell;

public class Explorer extends Character {

	public Explorer(IceCell ic){
		super(4, ic);
	}
	public void ability() {
		IceCell ic = getOwnCell().getNeighbour(getFacingWay());

		if(ic != null && ic.setCapacityKnown())
			loseOneAction();
	}
}
