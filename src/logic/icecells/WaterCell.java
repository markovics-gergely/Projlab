package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Bear;
import logic.characters.Character;
import logic.items.Items;

/**
 * Olyan cella, amiről a játékosok nem tudnak elmozdulni, és ha nem viselnek búvárruhát, akkor egy körön belül meghalnak.
 */
public class WaterCell extends IceCell {
	/**
	 * Tárolja, hogy volt-e olyan karakter, aki már beleesett ebbe a vízcellába. Ha ez igaz, akkor nem tud hóréteg lenni a cellán ezután.
	 */
	private boolean broken = false;

	public WaterCell(IceField icef){
		super(0, icef);
	}

	public void setBroken() { broken = true; snow = 0; }
	/**
	 * Ha megtalálható a cellán az a játékos, akit az IceField a chosenToSave-ben tárol, akkor őt kilépteti a paraméterül adott irányba.
	 * @param from léptetés iránya
	 * @return Ha nem található rajta a játékos, akkor hamissal tér vissza.
	 */
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

	/**
	 * Ha még nincs betörve, akkor egy hóréteg hozzáadása.
	 */
	public void snowing() { if(!broken) gainOneSnow(); }
	/**
	 * Paraméterül kapott karakter befogadása, és a broken igazzá állítása.
	 * @param ch Cellára lépett játékos
	 */
	public void accept(Character ch) {
		addCharacter(ch);
		ch.addOneTurnInWater();
		ch.setOwnCell(this);
		setBroken();
	}

	//Kirajzolást segító függvények
	public boolean itHasIgloo(){ return false; }
}
