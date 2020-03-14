package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class IceCell {
	protected int capacity;
	private boolean capacityKnown = false;
	protected int snow;
	protected static int maxSnow = 5;
	private HashMap<Way, IceCell> neighbours = new HashMap<>();
	protected ArrayList<Character> standingPlayers;
	protected IceField ownField;

	public IceCell(int c, IceField icef){
		Random r = new Random();
		snow = r.nextInt(maxSnow + 1);
		capacity = c;
		ownField = icef;
	}
	public void setOwnField(IceField icef) {ownField = icef;}
	public void setCapacityKnown() { capacityKnown = true;}
	public boolean safeToStart(){ return false; }
	public void loseSnow(boolean withShovel) {
		snow = (withShovel) ? snow - 2 : snow - 1;
		if(snow < 0) snow = 0;
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

	public void movePlayerOut(Way from) {}
	public void mine(Character actual) {}
	public void setIgloo(boolean b) {}
	public abstract void accept(Character ch);
	public abstract void snowing();
}
