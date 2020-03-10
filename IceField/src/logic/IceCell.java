package logic;

import java.util.ArrayList;

public abstract class IceCell {
	private int capacity;
	private boolean capacityKnown = false;
	protected int snow;
	private static int maxSnow = 5;
	private ArrayList<IceCell> neighbours = new ArrayList<>();
	protected Character standingPlayers;
	private IceField ownField;

	public void setOwnField(IceField icef) {}
	public void setCapacityKnown() { }
	public void loseSnow(boolean withShovel) { }
	public void gainOneSnow() { }
	public IceCell getNeighbour(Way w) { }
	public void addNeighbour(Way w, IceCell ic) { }
	public void removeCharacter(Character ch) { }
	public void addCharacter(Character ch) { }
	public void mine(Character actual) { }
	public void setIgloo(boolean b) { }
	public void movePlayerOut(Way from) { }

	public abstract void accept(Character ch);
	public abstract void snowing();
}
