package logic.icecells;

import logic.IceField;
import logic.Way;
import logic.characters.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class IceCell {
	protected int capacity;
	protected boolean capacityKnown = false;
	protected int snow;
	protected static int maxSnow = 5;
	private HashMap<Way, IceCell> neighbours = new HashMap<>();
	protected ArrayList<Character> standingPlayers = new ArrayList<>();
	protected IceField ownField;

	public int getSnow(){ } //CSAK TESZT
	public int getCapacity(){ } //CSAK TESZT
	public int getPlayers(){ } //CSAK TESZT
	public int getCapacityKnown(){ } //CSAK TESZT
	public abstract int getIgloo(); //CSAK TESZT

	public IceCell(int c, IceField icef){

	}

	public boolean setCapacityKnown() {

	}
	public boolean loseSnow(boolean withShovel) {

	}
	public void gainOneSnow() {

	}
	public IceCell getNeighbour(Way w) {

	}
	public void addNeighbour(Way w, IceCell ic) {

	}
	public void removeCharacter(Character ch) { }
	public void addCharacter(Character ch) {  }

	public boolean movePlayerOut(Way from) { }
	public void mine(Character actual) {}
	public boolean setIgloo(boolean b) { }
	public abstract boolean safeToStart();
	public abstract void accept(Character ch);
	public abstract void snowing();
}
