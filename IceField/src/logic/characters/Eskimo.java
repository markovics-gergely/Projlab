package logic.characters;

import logic.IceField;
import logic.icecells.IceCell;

public class Eskimo extends Character {

	public Eskimo(IceCell ic){
		super(5, ic);
	}
	public void ability() {
		if(getOwnCell().setIgloo(true))
			ownField.actionHandler(false);
	}
}
