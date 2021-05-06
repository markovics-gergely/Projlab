package logic.characters;

import logic.Way;
import logic.icecells.IceCell;

/**
 * A felfedező karaktert jelképezi. A két játszható karakter közül az egyik.
 */
public class Explorer extends Character {

	public Explorer(IceCell ic){
		super(4, ic);
	}
	/**
	 * Az adott karakter nézési irányába lévő cellára beállítja az ismeretséget.
	 * Ha az adott cella létezik és még nincs beállítva rá az ismeretség akkor levon egy munkát.
	 */
	public void ability() {
		IceCell ic = getOwnCell().getNeighbour(getFacingWay());

		if(ic != null && ic.setCapacityKnown())
			loseOneAction();
	}
}
