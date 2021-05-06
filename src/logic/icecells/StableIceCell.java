package logic.icecells;

import logic.IceField;
import logic.characters.Bear;
import logic.characters.Character;
import logic.items.EssentialItem;
import logic.items.Items;
import logic.items.PlayerActions;

import java.util.HashMap;

/**
 * Olyan jégmező, amin bármennyi karakter állhat, és lehetnek benne eltemetve tárgyak, amit kiáshatnak.
 */
public class StableIceCell extends IceCell  {
	/**
	 * Megadja, hogy a cellán van-e építve iglu
	 */
	private boolean hasIgloo = false;
	/**
	 * Megadja, hogy a cellán található-e sátor, és hogy hány kör múlva tűnik el. Ha 0, akkor nincs rajta sátor.
	 */
	private int tentTurnsLeft = 0;
	/**
	 * Cellába fagyott tárgy, amit ki tudnak ásni, ha nincs felette hóréteg.
	 */
	private Items item;
	PlayerActions playerAct;


	public StableIceCell(IceField icef, Items i, PlayerActions pa){
		super(IceField.getMaxPlayer(), icef);
		item = i;
		playerAct = pa;
	}

	/**
	 * Eltávolítja a mezőn található tárgyat.
	 */
	private void removeItem() { item = null;}
	/**
	 * A cellán található tárgy odaadása a paraméterül adott karakternek, ha nincs felette hó.
	 * @param ch bányászó karakter
	 */
	public void mine(Character ch) {
		if(snow == 0 && item != null){
			if(item.equip(ch)){
				removeItem();
				ch.loseOneAction();
			}
		}
	}
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
	 * Sátor felállítása
	 * @return Visszaadja, hogy lehetett-e felállítani, nem ugyanebben a körben állította.
	 */
	public boolean setUpTent() {
		if(tentTurnsLeft == IceField.getMaxPlayer() || hasIgloo) return false;
		else {
			tentTurnsLeft = IceField.getMaxPlayer();
			return true;
		}
	}
	/**
	 * Rajta lévő sátor hátralévő köreinek csökkentése eggyel.
	 */
	public void loseOneTentTurn(){ tentTurnsLeft--; }
	/**
	 * Rajta lévő sátor hátra lévő köreinek számát visszaállítja 0-ra.
	 */
	public void resetTentTurnsLeft() { tentTurnsLeft = 0; }
	/**
	 * Biztonságos, hogy a játékosok ezen a mezőn kezdjenek
	 * @return Kezdhetnek-e itt a játékosok
	 */
	public boolean safeToStart(){ return bear == null; }
	/**
	 * Egy hóréteg hozzáadása. Ha nincs rajta iglu vagy sátor, akkor a rajta álló játékosok elvesztenek egy testhőt, és a sátor vagy iglu is rombolódik.
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
	 * @param ch Cellára lépő játékos
	 */
	public void accept(Character ch) {
		addCharacter(ch);
		ch.setOwnCell(this);
		if(bear != null)
			ownField.gameLost("Megtámadott a medve!");
	}
	/**
	 * Paraméterül kapott medve befogadása a cellára. Ha voltak a cellán játékosok, akkor vége a játéknak.
	 * @param b Cellára lépő medve
	 * @return Mindig igaz StableIceCellben
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
	public boolean hasItem(PlayerActions pa){
		if(playerAct == pa && item != null)
			return true;
		return false;
	}
	public EssentialItem getEssential(){
		return (EssentialItem)item;
	}
}
