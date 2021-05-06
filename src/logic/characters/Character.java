package logic.characters;

import logic.icecells.IceCell;
import logic.items.Items;
import logic.items.PlayerActions;
import logic.Way;
import logic.items.BackPack;

/**
 * Absztrakt osztály, ami a játékban választható karakterek állapotát, tulajdonságait és képességeit összesíti.
 */
public abstract class Character {
	/**
	 *Megadja a karakter max szerezhető testhőjét, és a kezdő testhőjét.
	 */
	private int maxBodyHeat;
	/**
	 *Megadja a karakter jelenlegi testhőjét, ami kezdetben max. Ha 0-ra csökken akkor meghal.
	 */
	private int bodyHeat;
	/**
	 *Tárolja, hogy a karakter hány kör óta van vízben. Ha ez
	 * a szám eléri a játékosok számát (tehát mindenki lelépte a körét), és nincs a
	 * karakteren búvárruha, akkor megfullad és vége a játéknak.
	 */
	private int turnsInWater = 0;
	/**
	 *Megmondja, hogy adott játékos visel-e búvárruhát.
	 */
	private boolean wearingDivingSuit = false;
	/**
	 *Tárolja, hogy az adott karakter éppen milyen irányba néz.
	 */
	private Way facingWay = Way.up;
	/**
	 *Az a mező, ahol az adott játékos éppen áll.
	 */
	private IceCell ownCell;
	/**
	 *A játékos hátizsákja, amiben a felvett tárgyait tárolja.
	 */
	private BackPack backpack = new BackPack();
	/**
	 *A karakter maximum ennyi akciót hajthat végre.
	 */
	private static int maxActions = 4;
	/**
	 *Karakternek hátralévő használható a akciói a körben, ami ha 0-ra csökken, akkor a következő játékos jön.
	 */
	private int actionsLeft;

	Character(int mb, IceCell ic){
		maxBodyHeat = mb;
		ownCell = ic;
		bodyHeat = maxBodyHeat;
		actionsLeft = maxActions;
	}

	/**
	 * Karakter mozgatása egy mezővel amerre néz, ha található arra mező.
	 */
	public void move() {
		IceCell ic = ownCell.getNeighbour(facingWay);
		if(ic != null){
			ownCell.removeCharacter(this);
			ic.accept(this);
			loseOneAction();
		}
	}
	/**
	 * Játékos eltakarít egy vagy kettő hóréteget a saját mezején, a paramétertől függően, amíg van rajta hó.
	 * @param withShovel Megadja, hogy a játékos ásóval tünteti-e el a hóréteget.
	 * @return True, ha sikerült eltüntetni hóréteget, false hogyha  a munkavégzés sikertelen, mert nem volt hóréteg.
	 */
	public boolean dig(boolean withShovel) {
		return ownCell.loseSnow(withShovel);
	}

	/**
	 * Tárgy kibányászása a saját cellán, ha nincs rajta hóréteg és található ott tárgy.
	 * Emellett nem vesz bányászunk ki olyan tárgyat, ami nála van, ha az nem Food vagy EssentialItem.
	 */
	public void mine() { ownCell.mine(this); }
	/**
	 * Karakterhez ad egy testhőt, ha az nem nőne meg vele a maximum fölé.
	 * @return True, ha sikeresen hozzáadott egyet.
	 */
	public boolean gainOneHeat() {
		if(bodyHeat != maxBodyHeat){
			bodyHeat++;
			return true;
		}
		return false;
	}
	/**
	 * Karakter elveszít egy testhőt.
	 */
	public void loseOneHeat() {
		if(bodyHeat != 0) bodyHeat--;
	}
	public void setOwnCell(IceCell ic) { ownCell = ic; }
	public IceCell getOwnCell() { return ownCell; }
	/**
	 * Hozzáad egyet a turnsInWaterhez, ami tárolja, hogy a játékos hány köre van vízalatt.
	 */
	public void addOneTurnInWater() { turnsInWater++; }
	public int getTurnsInWater() { return turnsInWater; }
	/**
	 * turnsInWater attribútum visszaállítása 0-ra.
	 */
	public void resetTurnsInWater() { turnsInWater = 0; }
	/**
	 * Egy Itemet belerak a BackPackbe a hozzá illő PlayerActionnel együtt,
	 * ha a táskában még nincs belőle, kivéve a Food és EssentialItem.
	 * @param it A belerakni kívánt tárgy.
	 * @param pa A tárgyhoz tartozó cselekvés.
	 * @return True, ha sikerült belerakni az adott Itemet.
	 */
	public boolean putItemtoBackPack(Items it, PlayerActions pa) { return backpack.addItem(it, pa); }
	/**
	 *Divingsuit felvétele a wearingDivingSuit beállításával.
	 * @return True, ha sikerült (most először) felvenni a búvárruhát.
	 */
	public boolean wearDivingSuit() {
		if(!wearingDivingSuit){
			wearingDivingSuit = true;
			return true;
		}
		return false;
	}
	public boolean getDivingSuit() { return wearingDivingSuit;}
	public void setFacingWay(Way w) { facingWay = w;}
	public Way getFacingWay() { return facingWay;}
	/**
	 * A megadott kulcsú Item használata. Ha tudta használni az Itemet, akkor elveszít egy munkát vele.
	 * @param pa A cselekvés típusa.
	 */
	public void useItem(PlayerActions pa) {
		Items item;
		if(pa == PlayerActions.eating && bodyHeat != maxBodyHeat)
			item = backpack.useFood();
		else
			item = backpack.getItem(pa);
		if(item != null){
			item.use(this);
		}
		else if(pa == PlayerActions.shoveling || pa == PlayerActions.fragileshoveling) {
			if(dig(false))
				loseOneAction();
		}
	}
	/**
	 * Jelzőpisztoly összeszerelésének próbálkozása.
	 * Ha össze tudták szerelni, akkor elveszít egy munkát, de megnyerik a játékot.
	 */
	public void useEssentials() {
		Items ei = backpack.getItem(PlayerActions.assemblingEssentials);
		if(ei != null) ei.use(this);
	}
	public int getBodyHeat() { return bodyHeat; }
	public BackPack getBackPack(){ return backpack; }
	public int getActionsLeft(){ return actionsLeft; }
	/**
	 * Felhasználható munkát visszaállítja a maximumra.
	 */
	public void resetActionsLeft(){ actionsLeft = maxActions; }
	/**
	 * Egy felhasználható munka elvesztése.
	 */
	public void loseOneAction(){ actionsLeft--; }
	/**
	 * Használja az adott karakter a speciális képességét.
	 */
	public abstract void ability();
}
