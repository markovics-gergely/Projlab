package logic;

import logic.characters.Character;
import logic.icecells.IceCell;
import logic.items.PlayerActions;

import java.util.ArrayList;

public class IceField {
	private static int maxPlayer;
	private static int fieldLengths = maxPlayer + 4;
	private int currentPlayer;
	public int actionsLeft;
	public static int maxActions = 4;
	private boolean gameWon = false;
	private boolean gameLost = false;
	private ArrayList<ArrayList<IceCell>> field;
	private ArrayList<Character> characters;
	private WinChecker wc;

	public void nextPlayer() { }
	private void snowStorm() { }
	public void movePlayer() { }
	public void usePlayerItem(PlayerActions ia) { }
	public void useAbility() { }
	public void useEssentialItems() { }
	public void addIceCell(IceCell ic) { }
	public void removeIceCell(IceCell ic) { }
	public void mineActualCell() { }
	public void setPlayerWay(Way w) { }
	private void gameLost() { }
	private void gameWon() { }
}
