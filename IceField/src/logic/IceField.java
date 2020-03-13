package logic;

import logic.characters.Character;
import logic.icecells.IceCell;
import logic.icecells.StableIceCell;
import logic.icecells.UnstableIceCell;
import logic.icecells.WaterCell;
import logic.items.*;

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

	private void buildCells(){
		for(int y = 0; y < fieldLengths; y++)
			for(int x = 0; x < fieldLengths; x++){
				field.get(y).add(x, new StableIceCell(this, null));
			}
		for(int y = 0; y < fieldLengths; y++)
			for(int x = 0; x < fieldLengths; x++){
				buildNeighbours(field.get(y).get(x), y, x);
			}
	}
	private void buildNeighbours(IceCell ic, int y, int x){
		if(y != 0) ic.addNeighbour(Way.up, field.get(y - 1).get(x));
		if(y != fieldLengths - 1) ic.addNeighbour(Way.down, field.get(y + 1).get(x));
		if(x != 0) ic.addNeighbour(Way.left, field.get(y).get(x - 1));
		if(x != fieldLengths - 1) ic.addNeighbour(Way.right, field.get(y).get(x + 1));
	}
	private void configureCells(int numberOfWater, int numberOfUnstable){
		int[][] confTable = new int[fieldLengths][fieldLengths];
		for(int j = 0; j < fieldLengths; j++)
			for(int i = 0; i < fieldLengths; i++)
				confTable[j][i] = 0;

		Random random = new Random();
		int x = random.nextInt(fieldLengths);
		int y = random.nextInt(fieldLengths);

		for(int i = 0; i < numberOfWater; i++){
			while(confTable[y][x] != 0){
				x = random.nextInt(fieldLengths);
				y = random.nextInt(fieldLengths);
			}
			WaterCell water = new WaterCell(this);
			field.get(y).add(x, water);
			buildNeighbours(water, y, x);
			for(Way w : Way.values()){
				if(water.getNeighbour(w) != null)
					water.getNeighbour(w).addNeighbour(w.opposite(), water);
			}
			confTable[y][x] = 1;
		}

		for(int i = 0; i < numberOfUnstable; i++){
			while(confTable[y][x] != 0){
				x = random.nextInt(fieldLengths);
				y = random.nextInt(fieldLengths);
			}
			UnstableIceCell unstable = new UnstableIceCell(random.nextInt(maxPlayer - 2) + 2, this);
			field.get(y).add(x, unstable);
			buildNeighbours(unstable, y, x);
			for(Way w : Way.values()){
				if(unstable.getNeighbour(w) != null)
					unstable.getNeighbour(w).addNeighbour(w.opposite(), unstable);
			}
			confTable[y][x] = 2;
		}

		putItemsIntoCells(confTable);
	}
	private void putItemsIntoCells(int[][] confTable){
		int[] items = new int[5];
		int essentialID = 0;
		items[0] = maxPlayer*2;
		items[1] = maxPlayer-1; items[2] = maxPlayer-1; items[3] = maxPlayer-1;
		items[4] = 3;

		Random random = new Random();
		int x = random.nextInt(fieldLengths);
		int y = random.nextInt(fieldLengths);

		for(int itemNumber = 0; itemNumber < items.length; itemNumber++) {
			for (int i = 0; i < items[itemNumber]; i++) {
				while (confTable[y][x] != 0) {
					x = random.nextInt(fieldLengths);
					y = random.nextInt(fieldLengths);
				}
				StableIceCell stable = new StableIceCell(this, selectItem(itemNumber, essentialID));
				field.get(y).add(x, stable);
				buildNeighbours(stable, y, x);
				for (Way w : Way.values()) {
					if (stable.getNeighbour(w) != null)
						stable.getNeighbour(w).addNeighbour(w.opposite(), stable);
				}
			}
			if(itemNumber == 4) essentialID++;
		}
	}
	private Items selectItem(int itemNumber, int essentialID){
		switch(itemNumber){
			case 0 : return new Food();
			case 1 : return new Shovel();
			case 2 : return new Divingsuit();
			case 3 : return new Rope();
			case 4 : return new EssentialItem(essentialID);
			default: return new Rope();
		}
	}

	private void snowStorm() {
		Random r = new Random();
		int x = r.nextInt(fieldLengths);
		int y = r.nextInt(fieldLengths);
		IceCell rootCell = field.get(y).get(x);
		int radius = (int)(Math.ceil(((double)fieldLengths)/2));
		snow(radius, Way.up, rootCell, false);
		snow(radius, Way.right, rootCell, true);
		snow(radius, Way.down, rootCell, false);
		snow(radius, Way.left, rootCell, true);
	}
	private void snow(int seqNum, Way to, IceCell from, boolean subroot){
		if(seqNum == 0) return;
		from.snowing();
		snow(--seqNum, to, from.getNeighbour(to), subroot);
		if(subroot){
			snow(--seqNum, to.rotate(true), from.getNeighbour(to.rotate(true)), false);
			snow(--seqNum, to.rotate(false), from.getNeighbour(to.rotate(false)), false);
		}
	}
	public void addIceCell(IceCell ic) { }
	public void removeIceCell(IceCell ic) { }
	private void gameLost() { gameLost = true; }
	private void gameWon() { gameWon = true; }
	private void actionHandler(){
		actionsLeft--;
		if(actionsLeft == 0){
			actionsLeft = maxActions;
			nextPlayer();
		}
	}

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
	public void setPlayerWay(Way w) {
		characters.get(currentPlayer).setFacingWay(w);
	}
	public void usePlayerItem(PlayerActions pa) {
		characters.get(currentPlayer).useItem(pa);
		actionHandler();
	}
	public void useAbility() {
		characters.get(currentPlayer).ability();
		actionHandler();
	}
	public void movePlayer() {
		characters.get(currentPlayer).move();
		actionHandler();
	}
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
		actionHandler();
	}
	public void mineActualCell() {
		characters.get(currentPlayer).mine();
		actionHandler();
	}
}
