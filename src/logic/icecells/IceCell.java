package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Character;
import logic.characters.Bear;
import logic.items.EssentialItem;
import logic.items.Items;
import logic.items.PlayerActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A játéktábla egy celláját reprezentálja. Tárolja a szomszédait, a hóréteget, a rajta
 * álló karaktereket, az IceField objektumot, amin a játék épp játszódik.
 */
public abstract class IceCell {
	/**
	 * Az adott cella kapacitása. Ez jelzi, hogy maximálisan mennyi ember léphet rá, mielőtt beszakadna.
	 */
	protected int capacity;
	/**
	 * Megmondja, hogy a játékosok tudják-e ennek a cellának a kapacitását.
	 */
	private boolean capacityKnown = false;
	/**
	 * Az aktuálisan cellán lévő hórétegek száma.
	 */
	protected int snow;
	/**
	 *A maximális hóréteg, ami egy cellán szerepelhet.
	 */
	protected static int maxSnow = 5;
	/**
	 * A cellával szomszédos mezőket tároljuk itt irányaik szerint.
	 */
	private HashMap<Way, IceCell> neighbours = new HashMap<>();
	/**
	 * A mezőn éppen álló karakterek.
	 */
	protected ArrayList<Character> standingPlayers = new ArrayList<>();
	/**
	 * A játéktábla, ahol a játék épp játszódik.
	 */
	protected IceField ownField;
	/**
	 * Mezőn álló medve.
	 */
	protected Bear bear = null;


	public IceCell(int c, IceField icef){
		Random r = new Random();
		snow = r.nextInt(maxSnow + 1);
		capacity = c;
		ownField = icef;
	}

	/**
	 * Beállítja, hogy ismerik a cella kapacitását
	 * @return Igazzal tér vissza, ha még nem volt ismert ezelőtt
	 */
	public boolean setCapacityKnown() {
		if(capacityKnown) return false;
		else {
			capacityKnown = true;
			return true;
		}
	}
	/**
	 * Eltávolít hóréteget az adott celláról.
	 * @param withShovel Ha hamis, akkor kézzel próbálnak ásni
	 * @return Visszaadja, hogy kellett-e ásni, vagy 0 volt a hó.
	 */
	public boolean loseSnow(boolean withShovel) {
		if(snow != 0){
			snow = (withShovel) ? snow - 2 : snow - 1;
			if(snow < 0) snow = 0;
			return true;
		}
		return false;
	}
	/**
	 * Megnöveli a cella hórétegét eggyel. Ha ezzel együtt a hóréteg maxSnow fölött lenne, akkor marad a cellán az maxSnow réteg hó.
	 */
	public void gainOneSnow() {
		snow ++;
		if(snow > maxSnow) snow = maxSnow;
	}
	public IceCell getNeighbour(Way w) {
		return neighbours.get(w);
	}
	/**
	 * Hozzáadja a paraméterként kapott cellát a szomszédok közé.
	 * @param w Milyen irányba akarja hozzáadni.
	 * @param ic Hozzáadott szomszéd cella.
	 */
	public void addNeighbour(Way w, IceCell ic) {
		neighbours.remove(w);
		neighbours.put(w, ic);
	}
	/**
	 * Eltávolítja az adott karaktert a standingPlayers tömbből.
	 * @param ch eltávolított karakter
	 */
	public void removeCharacter(Character ch) { standingPlayers.remove(ch); }
	/**
	 * Hozzáadja az adott karaktert a standindPlayers tömbhöz.
	 * @param ch hozzáadott karakter
	 */
	public void addCharacter(Character ch) { standingPlayers.add(ch); }

	public boolean acceptBear(Bear b){
		return false;
	}
	/**
	 * Megmondja, hogy az adott cellán áll-e medve.
	 * @return áll-e medve
	 */
	public boolean hasBear(){ return bear != null; }
	/**
	 * A medvét eltávolítja az adott cella tagváltozójából.
	 */
	public void removeBear(){ bear = null; }

	public void loseOneTentTurn(){}
	public int getTentTurnsLeft() { return 0; }
	public boolean movePlayerOut(Way from) { return false;}
	public void mine(Character actual) {}
	public boolean setIgloo(boolean b) { return false;}
	public boolean setUpTent() { return false;}
	public boolean safeToStart(){ return false; }
	public abstract void accept(Character ch);
	public abstract void snowing();

	//Kirajzoláshoz szükséges
	public boolean isCapacityKnown(){ return capacityKnown; }
	public Character getPlayersFromCell(int i){ return standingPlayers.get(i); }
	public ArrayList<Character> getPlayersFromCell(){ return standingPlayers; }
	public int getSnow(){ return snow; }
	public int getCapacity(){ return capacity; }
	public boolean hasItem(PlayerActions pa){ return false; }
	public abstract boolean itHasIgloo();
	public EssentialItem getEssential(){ return null; }
}
