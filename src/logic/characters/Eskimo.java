package logic.characters;

import logic.icecells.IceCell;

/**
 * Az eskimo karaktert jelképezi. A két játszható karakter közül az egyik.
 */
public class Eskimo extends Character {

	public Eskimo(IceCell ic){
		super(5, ic);
	}
	/**
	 * Beállítja a saját cellájára az iglut, ha már létezik rajta iglu akkor nem von le munka használatot.
	 */
	public void ability() {
		if(getOwnCell().setIgloo(true))
			loseOneAction();
	}
}
