package logic;

import logic.characters.Character;
import logic.icecells.IceCell;
import logic.items.PlayerActions;

import java.util.ArrayList;
import java.util.Random;

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

	public void nextPlayer() {
		currentPlayer++;
		Random r = new Random();
		int i = r.nextInt(3);
		if(currentPlayer == maxPlayer){
			currentPlayer = 0;
			if(i == 0) snowStorm();
		}
		for(Character c : characters){
			if(c.getTurnsInWater() != 0)
				c.addOneTurnInWater();
			if(c.getBodyHeat() == 0 || (c.getTurnsInWater() > maxPlayer && !c.getDivingSuit()))
				gameLost();
		}
	}
	private void snowStorm() { }
	public void movePlayer() { characters.get(currentPlayer).move(); }
	public void usePlayerItem(PlayerActions pa) { characters.get(currentPlayer).useItem(pa);}
	public void useAbility() { characters.get(currentPlayer).ability(); }
	public void useEssentialItems() {
		IceCell ic = characters.get(0).getOwnCell();

		for(Character ch : characters){
			if(ch.getOwnCell() != ic)
				return;
		}
		for(Character ch : characters){
			ch.useEssentials();
		}

		if(wc.isAssembled())
			gameWon();
		else
			wc.resetAssembledItems();
	}
	public void addIceCell(IceCell ic) { }
	public void removeIceCell(IceCell ic) { }
	public void mineActualCell() {
		characters.get(currentPlayer).mine();
	}
	public void setPlayerWay(Way w) {
		characters.get(currentPlayer).setFacingWay(w);
	}
	private void gameLost() { gameLost = true; }
	private void gameWon() { gameWon = true; }
}
