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

public abstract class IceCell {
	protected int capacity;
	private boolean capacityKnown = false;
	protected int snow;
	protected static int maxSnow = 5;
	private HashMap<Way, IceCell> neighbours = new HashMap<>();
	protected ArrayList<Character> standingPlayers = new ArrayList<>();
	protected IceField ownField;
	protected Bear bear = null;


	public IceCell(int c, IceField icef){
		Random r = new Random();
		snow = r.nextInt(maxSnow + 1);
		capacity = c;
		ownField = icef;
	}

	public boolean setCapacityKnown() {
		if(capacityKnown) return false;
		else {
			capacityKnown = true;
			return true;
		}
	}
	public boolean loseSnow(boolean withShovel) {
		if(snow != 0){
			snow = (withShovel) ? snow - 2 : snow - 1;
			if(snow < 0) snow = 0;
			return true;
		}
		return false;
	}
	public void gainOneSnow() {
		snow ++;
		if(snow > maxSnow) snow = maxSnow;
	}
	public IceCell getNeighbour(Way w) {
		return neighbours.get(w);
	}
	public void addNeighbour(Way w, IceCell ic) {
		neighbours.remove(w);
		neighbours.put(w, ic);
	}
	public void removeCharacter(Character ch) { standingPlayers.remove(ch); }
	public void addCharacter(Character ch) { standingPlayers.add(ch); }

	public boolean acceptBear(Bear b){
		return false;
	}
	public boolean hasBear(){ return bear != null; }
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
