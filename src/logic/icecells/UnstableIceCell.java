package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Bear;
import logic.characters.Character;
import logic.items.Items;

/**
 * Instabil típusú cellát valósít meg. Ha ilyen típusú cellára a kapacitásánál több karakter lép, akkor beszakad, és a rajta lévő karakterek vízbe kerülnek.
 */
public class UnstableIceCell extends IceCell  {
	/**
	 * Megadja, hogy a cellán van-e építve iglu
	 */
	private boolean hasIgloo = false;
	/**
	 * Megadja, hogy a cellán található-e sátor, és hogy hány kör múlva tűnik el. Ha 0, akkor nincs rajta sátor.
	 */
	private int tentTurnsLeft = 0;


	public UnstableIceCell(int c, IceField icef){
		super(c, icef);
	}


	/**
	 * Rajta lévő sátor hátralévő köreinek csökkentése eggyel.
	 */
	public void loseOneTentTurn(){ tentTurnsLeft--; }
	/**
	 * Sátor telepítése a cellán.
	 * @return Hamissal tér vissza, ha már ugyanez a játékos rakott már ebben a körben le sátrat.
	 */
	public boolean setUpTent() {
		if(tentTurnsLeft == IceField.getMaxPlayer() || hasIgloo) return false;
		else {
			tentTurnsLeft = IceField.getMaxPlayer();
			return true;
		}
	}
	/**
	 * Rajta lévő sátor hátra lévő köreinek számát visszaállítja 0-ra.
	 */
	public void resetTentTurnsLeft() { tentTurnsLeft = 0; }
	/**
	 * Iglu építése
	 * @param b Igaz, ha építeni, hamis, ha rombolni akarjuk az iglut
	 * @return Visszaadja, hogy lehetett-e építeni vagy rombolni
	 */
	public boolean setIgloo(boolean b) {
		if(hasIgloo == b || tentTurnsLeft > 0) return false;
		else {
			hasIgloo = b;
			return true;
		}
	}
	/**
	 * Egy hóréteg hozzáadása, és ha nincs rajta iglu vagy sátor, akkor a rajta álló játékosok elvesztenek egy testhőt.
	 */
	public void snowing() {
		gainOneSnow();
		if(!hasIgloo && tentTurnsLeft == 0){
			for(Character ch : standingPlayers){
				ch.loseOneHeat();
			}
		}
		if(hasIgloo && tentTurnsLeft != 0) resetTentTurnsLeft();
		else if(hasIgloo) setIgloo(false);
		else if (tentTurnsLeft != 0) resetTentTurnsLeft();
	}
	/**
	 * Paraméterül kapott játékos befogadása a cellára. Ha volt rajta medve, akkor vége a játéknak.
	 * Ha a kapacitás feletti játékos van rajta, akkor WaterCellé válik.
	 * @param ch Cellára lépő játékos
	 */
	public void accept(Character ch) {
		addCharacter(ch);
		ch.setOwnCell(this);
		if(standingPlayers.size() <= capacity)
			return;

		WaterCell wc = new WaterCell(ownField);
		for (Way w : Way.values()) {
			IceCell ic = getNeighbour(w);
			if(ic != null){
				wc.addNeighbour(w, ic);
				ic.addNeighbour(w.opposite(), wc);
			}
		}
		for(Character c : standingPlayers){
			wc.addCharacter(c);
			c.setOwnCell(wc);
			c.addOneTurnInWater();
		}
		wc.setBroken();
		ownField.addIceCell(wc, this);
		if(bear != null)
			ownField.gameLost("Megtámadott a medve!");
	}

	/**
	 * Paraméterül kapott medve befogadása a cellára. Ha voltak a cellán játékosok, akkor vége a játéknak.
	 * @param b Cellára lépő medve
	 * @return Mindig igaz UnStableIceCellben
	 */
	public boolean acceptBear(Bear b){
		bear = b;
		b.setOwnCell(this);
		if(!standingPlayers.isEmpty() && !hasIgloo)
			ownField.gameLost("Megtámadott a medve!");
		return true;
	}

	//Kirajzolást segító függvények
	public boolean itHasIgloo(){ return hasIgloo; }
	public int getTentTurnsLeft(){ return tentTurnsLeft; }
}
