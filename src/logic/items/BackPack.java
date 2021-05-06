package logic.items;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Egy karakter táskáját jelképezi. Itt vannak tárolva az adott játékos birtokolt tárgyai. Ezeket lehet kezelni, tehát eltávolítani, hozzáadni, stb.
 */
public class BackPack {
	/**
	 *  A birtokolt tárgyak egy HashMapben vannak tárolva. A tárgyak kapnak egy PlayerAction jelzőt, így megkülönböztethetővé válnak.
	 */
	private HashMap<PlayerActions, ArrayList<Items>> obtainedItems = new HashMap<>();

	/**
	 * Visszaadja, hogy a paraméterként kapott PlayerAction-höz (Tárgy jelölő) tartozó tárgyat a táskából, ha nem létezik ilyen akkor null-t ad vissza.
	 * @param pa A tárgy típusát kapja
	 * @return Az adott itemet (lehet null)
	 */
	public Items getItem(PlayerActions pa) {
		return obtainedItems.containsKey(pa) ? obtainedItems.get(pa).get(0) : null;
	}
	/**
	 * Mivel minden tárgyból 1 darab lehet a táskában, kivéve az essential és az étel, ezért az elején leellenőrzi,
	 * hogyha tartalmazza és nem ételről vagy essentialról van szó akkor visszatér egy falseval.
	 * Ha erről a két tárgyról van szó akkor azt hozzáadja az adott PlayerActionhöz tartozó HashMap Listájához. (HashMap<PlayerActions, ArrayList<Items>>)
	 * @param it Egy tárgy
	 * @param pa A tárgy típusa
	 * @return Ha sikerült belerakni igazat ad vissza
	 */
	public boolean addItem(Items it, PlayerActions pa) {
		if(obtainedItems.containsKey(pa) && pa != PlayerActions.eating && pa != PlayerActions.assemblingEssentials)
			return false;

		ArrayList<Items> array = new ArrayList<>();
		if(obtainedItems.containsKey(pa))
			array.addAll(obtainedItems.get(pa));
		array.add(it);
		obtainedItems.put(pa, array);
		return true;
	}
	/**
	 * Ha tartalmaz a táskánk ételt, akkor visszaadja a Lista utolsó elemét, és letörli a végéről. Tehát marad egy eggyel kisebb listánk, mert csökkent egy kajával.
	 * @return Egy ételt ad vissza
	 */
	public Food useFood() {
		Food f = null;

		if(obtainedItems.containsKey(PlayerActions.eating) && obtainedItems.get(PlayerActions.eating).size() != 0){
			int lastIndex = obtainedItems.get(PlayerActions.eating).size()-1;
			f = (Food)obtainedItems.get(PlayerActions.eating).get(lastIndex);
			obtainedItems.get(PlayerActions.eating).remove(lastIndex);
			if(obtainedItems.get(PlayerActions.eating).size() == 0) removeItem(PlayerActions.eating);
		}

		return f;
	}
	/**
	 * Visszaadja a lista méretét, hogy mennyi essentialitem van nála.
	 * @return nálalévő essentialitem száma
	 */
	public int getEssentialItemNumber() {
		return obtainedItems.containsKey(PlayerActions.assemblingEssentials) ? obtainedItems.get(PlayerActions.assemblingEssentials).size() : 0;
	}
	/**
	 * Eltávolítja a paraméterként kapott elemet a HashMapből.
	 * @param pa Tárgy típusa
	 */
	public void removeItem(PlayerActions pa){
		obtainedItems.remove(pa);
	}

	//Kirajzolást segító függvények
	public boolean hasEssentialItemID(int id){
		for(int i = 0; i < obtainedItems.get(PlayerActions.assemblingEssentials).size(); i++)
			if(((EssentialItem)obtainedItems.get(PlayerActions.assemblingEssentials).get(i)).getID() == id)
				return true;

		return false;
	}

	public boolean hasItem(PlayerActions pa){ return obtainedItems.containsKey(pa); }
}
