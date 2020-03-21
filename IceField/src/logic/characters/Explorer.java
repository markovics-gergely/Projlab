package logic.characters;

import logic.IceField;
import logic.Way;
import logic.icecells.IceCell;

public class Explorer extends Character {

	public Explorer(IceCell ic){
		super(4, ic);
	}
	public void ability() {
		IceCell ic = getOwnCell().getNeighbour(getFacingWay());
		if(ic != null)
			if (ic.setCapacityKnown())
				ownField.actionHandler(false);

		if(ic == null) ownField.actionHandler(false);
	}
}
