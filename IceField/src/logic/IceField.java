package logic;

import logic.characters.Bear;
import logic.characters.Character;
import logic.characters.Eskimo;
import logic.characters.Explorer;
import logic.icecells.IceCell;
import logic.icecells.StableIceCell;
import logic.icecells.UnstableIceCell;
import logic.icecells.WaterCell;
import logic.items.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Ez az osztály képviseli az egész táblát, tárolja a mezőket, karaktereket és a játék
 * menetéhez kapcsolódó fontosabb információkat. (játék végkimenetele,
 * játékosszám, aktuális játékos)
 */
public class IceField {
	/**
	 * 3-6 és közötti értéket vehet fel, a játékosok számát jelöli.
	 */
	private static int maxPlayer;
	/**
	 * Pálya mérete, mivel négyzet alakú és a
	 * játékosok számától függ. Pl: 3 játékosnál a pálya mérete 7*7.
	 */
	private static int fieldLengths;
	/**
	 * Aktuális játékos sorszámát jelöli.
	 */
	private int currentPlayer = 0;
	/**
	 * A játék sikeres kimenetele esetén az értéke true-
	 * ra változik, alaphelyzetben false.
	 */
	public boolean gameWon = false;
	/**
	 * A játék sikertelen kimenetele esetén az értéke
	 * true-ra változik, alaphelyzetben false.
	 */
	public boolean gameLost = false;
	/**
	 * A pályát jelöli ami IceCellekből épül fel. Lehet 7*7,
	 * 8*8, 9*9 és 10*10-es a pálya.
	 */
	private List<List<IceCell>> field = new ArrayList<>();
	/**
	 * A karakterek listája. aminek maximális
	 * mérete 6 lehet.
	 */
	private ArrayList<Character> characters;
	/**
	 * Ellenőrzi a nyerési feltételt, amennyiben egy játékos
	 * kezdeményezi a pisztoly összeszerelését.
	 */
	private WinChecker wc = new WinChecker();
	/**
	 * Amikor egy kötéllel kimentünk egy embert, akkor ez az érték fog átváltozni a kimentendő karakter sorszámára.
	 */
    private int chosenToSave = -1;
	/**
	 * A medvét jelképezi.
	 */
	private Bear bear;

	private int[][] cellTable; //CSAK TESZT, KIKOMMENTELNI A configureCells() ELSŐ SORÁT ÉS KISZEDNI A KONSTRUKTORBÓL
	private void drawField(){
		System.out.println(currentPlayer+1 + ". játékos hátralévő munkája: " + characters.get(currentPlayer).getActionsLeft() + ", testhője: " + characters.get(currentPlayer).getBodyHeat() + " és vízben töltött köre: " + characters.get(currentPlayer).getTurnsInWater());
		System.out.println(
				"Ásó:" + characters.get(currentPlayer).getBackPack().getNumber(PlayerActions.shoveling) +
				"  Kötél:" + characters.get(currentPlayer).getBackPack().getNumber(PlayerActions.savingWithRope) +
				"  Ruha:" + characters.get(currentPlayer).getBackPack().getNumber(PlayerActions.wearingSuit) +
				"  Étel:" + characters.get(currentPlayer).getBackPack().getNumber(PlayerActions.eating) +
				"  Plusz:" + characters.get(currentPlayer).getBackPack().getNumber(PlayerActions.assemblingEssentials) +
				"  Törékeny ásó:" + characters.get(currentPlayer).getBackPack().getNumber(PlayerActions.fragileshoveling) +
				"  Sátor:" + characters.get(currentPlayer).getBackPack().getNumber(PlayerActions.setUpTent)
		); //TÖRÖLNI A BACKPACK CLASSBÓL.
		System.out.print("Típus 0:St 1:Víz"); System.out.println();
		System.out.print("2:Inst 3:Item    ");
		System.out.print("Karakterek       ");
		System.out.print("Kapacitás        ");
		System.out.print("Hóréteg          ");
		System.out.print("Kap. Known       ");
		System.out.print("Iglu             ");
		System.out.print("Sátor            "); System.out.println();
		for (int j = 0; j < fieldLengths; j++) {
			for (int i = 0; i < fieldLengths; i++)
				System.out.print(cellTable[j][i] + " ");
			System.out.print("   ");
			for (int i = 0; i < fieldLengths; i++){
				if(field.get(j).get(i).hasBear()) System.out.print("B ");
				else System.out.print(field.get(j).get(i).getPlayers() + " "); //CSAK TESZT, KISZEDNI a getPlayers()-t az IceCellből.
			}
			System.out.print("   ");
			for (int i = 0; i < fieldLengths; i++)
				System.out.print(field.get(j).get(i).getCapacity() + " "); //CSAK TESZT, KISZEDNI a getCapacity()-t az IceCellből.
			System.out.print("   ");
			for (int i = 0; i < fieldLengths; i++)
				System.out.print(field.get(j).get(i).getSnow() + " "); //CSAK TESZT, KISZEDNI a getSnow()-t az IceCellből.
			System.out.print("   ");
			for (int i = 0; i < fieldLengths; i++)
				System.out.print(field.get(j).get(i).getCapacityKnown() + " "); //CSAK TESZT, KISZEDNI a getCapacityKnown()-t az IceCellből.
			System.out.print("   ");
			for (int i = 0; i < fieldLengths; i++)
				System.out.print(field.get(j).get(i).getIgloo() + " "); //CSAK TESZT, KISZEDNI a getIgloo()-t az IceCellből és leszármazottaiból.
			System.out.print("   ");
			for (int i = 0; i < fieldLengths; i++)
				System.out.print(field.get(j).get(i).getTentTurnsLeft() + " "); //CSAK TESZT, KISZEDNI a getTent()-t az IceCellből és leszármazottaiból.
			System.out.println();
		}
		System.out.println();
	} //CSAK TESZT, ÉS KISZEDNI AZ INPUT FV EK VÉGÉRŐL
	public IceField(int i, ArrayList<Character> c){
		maxPlayer = c.size();
		fieldLengths = maxPlayer + 4;
		characters = c;
		cellTable = new int[fieldLengths][fieldLengths]; //CSAK TESZT

		buildCells(i);
	}
	private void buildCells(int i){
		for(int y = 0; y < fieldLengths; y++)  {
			List<IceCell> row = new ArrayList<>(fieldLengths);
			for(int x = 0; x < fieldLengths; x++) {
				row.add(new StableIceCell(this, null));
			}
			field.add(row);
		}

		for(int y = 0; y < fieldLengths; y++)
			for(int x = 0; x < fieldLengths; x++)
				buildNeighbours(field.get(y).get(x), y, x);

		switch(i){
			case 1 : Test1(); break;
			case 2 : Test2(); break;
			case 3 : Test3(); break;
			case 4 : Test4(); break;
			case 5 : Test5(); break;
			case 6 : Test6(); break;
			case 7 : Test7(); break;
			case 8 : Test8(); break;
			case 9 : Test9(); break;
			case 10 : Test10(); break;
			case 11 : Test11(); break;
			case 12 : Test12(); break;
			case 13 : Test13(); break;
		}
	}
	private void buildNeighbours(IceCell ic, int y, int x){
		if(y != 0) ic.addNeighbour(Way.up, field.get(y - 1).get(x));
		if(y != fieldLengths - 1) ic.addNeighbour(Way.down, field.get(y + 1).get(x));
		if(x != 0) ic.addNeighbour(Way.left, field.get(y).get(x - 1));
		if(x != fieldLengths - 1) ic.addNeighbour(Way.right, field.get(y).get(x + 1));
	}

	/**
	 * Egy random középponttal a pályán, ezután választ egy véletlenszerű sugarat, 2 és a pálya szélessége felfele kerekítve között.
	 * Ezen a területen növeli a hóréteget eggyel, vagy ha van rajta igloo akkor azt törli.
	 * @param x1 x koordináta a hóra
	 * @param y1 y koordináta a hóra
	 *           (alapból nincsenek benne, csak a teszt miatt)
	 */
	public void snowStorm(int x1, int y1) {
		IceCell rootCell = field.get(y1).get(x1);
		int radius = (int)(Math.ceil(((double)fieldLengths)/2));

		rootCell.snowing();
		int i = 0;
		for(Way w : Way.values()){
			snow(radius - 1, w, rootCell.getNeighbour(w), i % 2 == 1);
			i++;
		}

		drawField();
	}
	private void snow(int seqNum, Way to, IceCell from, boolean subRoot){
		if(seqNum == 0 || from == null) return;
		from.snowing();

		snow(seqNum - 1, to, from.getNeighbour(to), subRoot);
		if(subRoot){
			snow(seqNum - 1, to.rotate(true), from.getNeighbour(to.rotate(true)), false);
			snow(seqNum - 1, to.rotate(false), from.getNeighbour(to.rotate(false)), false);
		}
	}

	/**
	 * Ha tartalmazza a pálya az adott törlendő cellát akkor átalakítja azzá amit első paraméterként megkap.
	 * @param ic
	 * @param removed
	 */
	public void addIceCell(IceCell ic, IceCell removed) {
		for(int j = 0; j < fieldLengths; j++)
			if(field.get(j).contains(removed)){
				int i = field.get(j).indexOf(removed);
				field.get(j).remove(i);
				field.get(j).add(i, ic);
			}
	}

	/**
	 *  A gameLost attribútumot truevá rakja.
	 */
	public void gameLost() { gameLost = true; }

	/**
	 * A gameWon attribútumot truevá rakja.
	 */
	public void gameWon() { gameWon = true; }

	/**
	 * Ha 0 munkája van hátra vagy több mint 0 kör óta vízben van akkor meghívja a nextPlayert. Ez a függvény minden munkát végző IceFieldben lévő függvény végén hívódik meg.
	 */
	private void actionHandler(){
		if(characters.get(currentPlayer).getActionsLeft() == 0 || characters.get(currentPlayer).getTurnsInWater() != 0){
			nextPlayer();
		}
	}
	public static int getMaxPlayer(){ return maxPlayer; }

	/**
	 * Ha nagyobb az i mint 0 és kisebb mint maxPlayer akkor beálíltja a chosenToSave értékét i re.
	 * @param i kiválasztott karakter indexe
	 */
	public void setChosenToSave(int i){
		if(i >= 0 && i < maxPlayer) chosenToSave = i;
	}

	/**
	 * Ha az alapértékkel egyenlő (-1) akkor null-t ad vissza, egyébként az adott indexű játékost. A végén visszarakja az attribútum értékét -1 re.
	 * @return Ha az alapértékkel egyenlő (-1) akkor null-t ad vissza, egyébként az adott indexű játékost
	 */
	public Character getChosenToSave(){
		if(chosenToSave == -1) return null;
		Character c = characters.get(chosenToSave);
		chosenToSave = -1;

		return c;
	}

	/**
	 * Az elején, visszarakja a még aktuális játékos munkáját 4 re, és mozgatja a medvét.
	 * Aztán meghívja a hóvihart 20% os eséllyel. Majd minden vízben lévő játékos vízben töltött körét növeli eggyel
	 * és minden játékosra megnézi, hogy a testhője 0-e vagy a vízben töltött ideje búvárruha nélkül meghaladja-e a maximálisat, mert akkor vége a játéknak.
	 * Majd törli az összes sátort a pályáról. Végül leellenőrzi, hogy mindenki vízben van-e és ha  nem akkor növeli az aktuális játékos számát eggyel.
	 */
	public void nextPlayer() {
		characters.get(currentPlayer).resetActionsLeft();

		for(Character c : characters){
			if(c.getTurnsInWater() != 0)
				c.addOneTurnInWater();
			if(c.getBodyHeat() == 0 || (c.getTurnsInWater() > maxPlayer && !c.getDivingSuit()))
				gameLost();
		}

		for (int y = 0; y < fieldLengths; y++) {
			for (int x = 0; x < fieldLengths; x++) {
				if (field.get(y).get(x).getTentTurnsLeft() > 0) {
					field.get(y).get(x).loseOneTentTurn();
				}
			}
		}

		int countAll;
		for(countAll = 0; countAll != maxPlayer; countAll++){
			currentPlayer = (currentPlayer + 1 == maxPlayer) ? 0 : (currentPlayer + 1);
			if(characters.get(currentPlayer).getTurnsInWater() == 0) break;
		}
		if(countAll == maxPlayer) gameLost();

		drawField();
	}

	/**
	 * Először megnézi, hogy egy cellán állnak-e, ha nem akkor visszatér simán. Ha egy cellán vannak összeadja az összes karakter essentialItemjét, ha megvan mind akkor nyertek.
	 */
	private void useEssentialItems() {
		IceCell ic = characters.get(0).getOwnCell();

		for(Character ch : characters){
			if(ch.getOwnCell() != ic)
				return;
		}
		for(Character ch : characters){
			ch.useEssentials();
		}

		if(wc.isAssembled()){
			gameWon();
			characters.get(currentPlayer).loseOneAction();
		}
		else wc.resetAssembledItems();

		actionHandler();

		drawField(); //CSAK TESZT
	}

	/**
	 * Beállítja az adott játékos nézési irányát.
	 * @param w irány beállítása
	 */
	public void setPlayerWay(Way w) {
		characters.get(currentPlayer).setFacingWay(w);
	}

	/**
	 * Ha a paraméter egy essentialItem, meghívja a privét useEssentialItems() függvényt. Egyébként a useItem() függvényt az aktuális karakteren.
	 * @param pa tárgy típusa amit használ
	 */
	public void usePlayerItem(PlayerActions pa) {
		if(pa == PlayerActions.assemblingEssentials) useEssentialItems();
		else characters.get(currentPlayer).useItem(pa);

		actionHandler();

		drawField(); //CSAK TESZT
	}

	/**
	 * Meghívja a képesség használatát az aktuális játékosra.
	 */
	public void useAbility() {
		characters.get(currentPlayer).ability();
		actionHandler();

		drawField(); //CSAK TESZT
	}
	/**
	 * Az adott irányba beállítja az aktuális játékos nézési irányát. Majd meghívja a karakteren a move() függvényt.
	 * @param w adott irányba lépés
	 */
	public void movePlayer(Way w) {
		setPlayerWay(w);
		characters.get(currentPlayer).move();
		actionHandler();

		drawField(); //CSAK TESZT
	}

	/**
	 * Meghívja az adott karakter mine() függvényét.
	 */
	public void mineActualCell() {
		characters.get(currentPlayer).mine();
		actionHandler();

		drawField(); //CSAK TESZT
	}

	//Tesztek
	private void Test1(){
		int x = 0, y = 1;
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y-1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(y-1).get(x));
			characters.get(i).getBackPack().addItem(new Rope(), PlayerActions.savingWithRope);
		}
		WaterCell water = new WaterCell(this);
		field.get(y).set(x, water);
		buildNeighbours(water, y, x);
		for(Way w : Way.values()){
			if(water.getNeighbour(w) != null)
				water.getNeighbour(w).addNeighbour(w.opposite(), water);
		}
		cellTable[y][x] = 1;

		drawField();
	}
	private void Test2(){
		int capacity = 2, x = 0, y = 1;

		UnstableIceCell uic = new UnstableIceCell(capacity, this);
		field.get(y).set(x, uic);

		buildNeighbours(uic, y, x);
		for(Way w : Way.values()){
			if(uic.getNeighbour(w) != null)
				uic.getNeighbour(w).addNeighbour(w.opposite(), uic);
		}
		cellTable[y][x] = 2;

		for(int i = 0; i < maxPlayer; i++) {
			field.get(y-1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(x).get(y-1));
		}
		drawField();
	}
	private void Test3(){
		int x = 0, y = 1;
		for(int i = 0; i < 3; i++) {
			StableIceCell stable = new StableIceCell(this, new EssentialItem(i, wc));
			field.get(y).set(i, stable);
			buildNeighbours(stable, y, i);
			for(Way w : Way.values()){
				if(stable.getNeighbour(w) != null)
					stable.getNeighbour(w).addNeighbour(w.opposite(), stable);
			}
		}
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y-1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(x).get(y-1));
		}
		for(int i = 0; i < 3; i++)
			cellTable[1][i] = 3;
		drawField();
	}
	private void Test4(){
		int y = 1, x = 0;
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y - 1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(y - 1).get(x));
		}
		drawField();
	}
	private void Test5(){
		int x = 0, y = 1;
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y - 1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(y - 1).get(x));
		}
		WaterCell water = new WaterCell(this);
		field.get(y).set(x, water);
		buildNeighbours(water, y, x);
		for(Way w : Way.values()){
			if(water.getNeighbour(w) != null)
				water.getNeighbour(w).addNeighbour(w.opposite(), water);
		}
		cellTable[y][x] = 1;
		drawField();
	}
	private void Test6(){
		int x = 0, y = 1;
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y - 1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(y - 1).get(x));
		}
		bear = new Bear(field.get(y+2).get(x+3));
		field.get(y+2).get(x+3).acceptBear(bear);

		drawField();
	}
	private void Test7(){
		int x = 0, y = 1;
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y - 1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(y - 1).get(x));
			characters.get(i).getBackPack().addItem(new Shovel(), PlayerActions.shoveling);
		}
		StableIceCell stable = new StableIceCell(this, new Rope());
		buildNeighbours(stable, y, x);
		field.get(y).set(x, stable);
		for(Way w : Way.values()){
			if(stable.getNeighbour(w) != null)
				stable.getNeighbour(w).addNeighbour(w.opposite(), stable);
		}
		cellTable[y][x] = 3;

		field.get(y).get(x).gainOneSnow();
		field.get(y).get(x).gainOneSnow();
		field.get(y).get(x).gainOneSnow();

		drawField();
	}
	private void Test8(){
		int x = 0, y = 1;
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y - 1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(y - 1).get(x));
			characters.get(i).getBackPack().addItem(new Tent(), PlayerActions.setUpTent);
		}

		drawField();
	}
	private void Test9(){
		int x = 0, y = 1;
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y - 1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(y - 1).get(x));
			characters.get(i).getBackPack().addItem(new Divingsuit(), PlayerActions.wearingSuit);
		}

		WaterCell water = new WaterCell(this);
		field.get(y).set(x, water);
		buildNeighbours(water, y, x);
		for(Way w : Way.values()){
			if(water.getNeighbour(w) != null)
				water.getNeighbour(w).addNeighbour(w.opposite(), water);
		}
		cellTable[y][x] = 1;

		drawField();
	}
	private void Test10(){
		int x = 0, y = 1;
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y - 1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(y - 1).get(x));
			characters.get(i).getBackPack().addItem(new Shovel(), PlayerActions.shoveling);
		}
		for(int i = x; i < x + 2; i++){
			StableIceCell stable = new StableIceCell(this, new Rope());
			field.get(y).set(i, stable);
			buildNeighbours(stable, y, i);
			for(Way w : Way.values()){
				if(stable.getNeighbour(w) != null)
					stable.getNeighbour(w).addNeighbour(w.opposite(), stable);
			}
			cellTable[y][i] = 3;
		}

		field.get(y).get(x).gainOneSnow();
		field.get(y).get(x).gainOneSnow();

		drawField();
	}
	private void Test11(){
		int x = 0, y = 1;
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y - 1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(y - 1).get(x));
		}

		WaterCell water = new WaterCell(this);
		field.get(y).set(x, water);
		buildNeighbours(water, y, x);
		for(Way w : Way.values()){
			if(water.getNeighbour(w) != null)
				water.getNeighbour(w).addNeighbour(w.opposite(), water);
		}
		cellTable[y][x] = 1;

		field.get(y).get(x).gainOneSnow();
		field.get(y).get(x).gainOneSnow();

		drawField();
	}
	private void Test12(){
		int x = 0, y = 1;
		for(int i = 0; i < maxPlayer; i++) {
			field.get(y - 1).get(x).addCharacter(characters.get(i));
			characters.get(i).setOwnCell(field.get(y - 1).get(x));
			characters.get(i).getBackPack().addItem(new Tent(), PlayerActions.setUpTent);
		}
		bear = new Bear(field.get(y+2).get(x+3));
		field.get(y+2).get(x+3).acceptBear(bear);

		drawField();
	}
	private void Test13(){
		StableIceCell r3 = new StableIceCell(this, new EssentialItem(2, wc)); //instabilnak kéne lennie de nem lehet
		StableIceCell M = new StableIceCell(this, null);
		WaterCell wc1 = new WaterCell(this);

		StableIceCell r1 = new StableIceCell(this, new EssentialItem(0, wc));
		WaterCell wc2 = new WaterCell(this);
		StableIceCell r2 = new StableIceCell(this, new EssentialItem(1, wc));
		UnstableIceCell E = new UnstableIceCell(2, this);
		WaterCell wc3 = new WaterCell(this);

		StableIceCell a = new StableIceCell(this, new Rope());
		WaterCell wc4 = new WaterCell(this);
		StableIceCell K1 = new StableIceCell(this, null);

		WaterCell wc5 = new WaterCell(this);
		UnstableIceCell K2 = new UnstableIceCell(2, this);


		bear = new Bear(M);
		characters.remove(2); characters.remove(1); characters.remove(0);
		Eskimo e1 = new Eskimo(E);
		Explorer k1 = new Explorer(K1);
		Explorer k2 = new Explorer(K2);
		E.addCharacter(e1); K1.addCharacter(k1); K2.addCharacter(k2); M.acceptBear(bear);
		characters.add(e1); characters.add(k1); characters.add(k2);

		r3.addNeighbour(Way.right, M); r3.addNeighbour(Way.down, wc2); r3.addNeighbour(Way.downleft, r1);
		M.addNeighbour(Way.left, r3); M.addNeighbour(Way.downleft, wc2); M.addNeighbour(Way.down, r2); M.addNeighbour(Way.right, wc1);
		wc1.addNeighbour(Way.left, M); wc1.addNeighbour(Way.downleft, r2); wc1.addNeighbour(Way.down, E);

		r1.addNeighbour(Way.up, r3); r1.addNeighbour(Way.right, wc2); r1.addNeighbour(Way.down, a); r1.addNeighbour(Way.downleft, wc5);
		wc2.addNeighbour(Way.left, r1); wc2.addNeighbour(Way.upleft, r3); wc2.addNeighbour(Way.up, M); wc2.addNeighbour(Way.right, r2); wc2.addNeighbour(Way.down, a);
		r2.addNeighbour(Way.left, wc2); r2.addNeighbour(Way.upleft, M); r2.addNeighbour(Way.up, wc1); r2.addNeighbour(Way.right, E); r2.addNeighbour(Way.down, wc4); r2.addNeighbour(Way.downleft, a);
		E.addNeighbour(Way.left, r2); E.addNeighbour(Way.upleft, wc1); E.addNeighbour(Way.right, wc3); E.addNeighbour(Way.down, K1); E.addNeighbour(Way.downleft, wc4);
		wc3.addNeighbour(Way.left, E); wc3.addNeighbour(Way.downleft, K1);

		a.addNeighbour(Way.left, r1); a.addNeighbour(Way.upleft, wc2); a.addNeighbour(Way.up, r2); a.addNeighbour(Way.right, wc4); a.addNeighbour(Way.down, K2);
		wc4.addNeighbour(Way.down, K2); wc4.addNeighbour(Way.right, K1); wc4.addNeighbour(Way.up, E); wc4.addNeighbour(Way.upleft, r2); wc4.addNeighbour(Way.left, a);
		K1.addNeighbour(Way.downleft, K2); K1.addNeighbour(Way.left, wc4); K1.addNeighbour(Way.upleft, E); K1.addNeighbour(Way.up, wc3);

		wc5.addNeighbour(Way.upleft, r1); wc5.addNeighbour(Way.right, K2);
		K2.addNeighbour(Way.left, wc5); K2.addNeighbour(Way.upleft, a); K2.addNeighbour(Way.up, wc4); K2.addNeighbour(Way.right, K1);
	}

	public Bear getBear(){
		drawField();
		return bear;
	}
	public void putIceCell(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Milyen cellát akar lerakni? (0-Stabil, 1-Instabil, 2-Víz)");
		int i = scan.nextInt();
		System.out.println("Hova szeretné lerakni? (Szóközzel elválasztva x és y, maximum " + fieldLengths + ")");
		int x = scan.nextInt();
		int y = scan.nextInt();
		switch(i){
			case 0:
				StableIceCell stable = new StableIceCell(this, null);
				field.get(y).set(x, stable);
				buildNeighbours(stable, y, x);
				for(Way w : Way.values()) {
					if (stable.getNeighbour(w) != null)
						stable.getNeighbour(w).addNeighbour(w.opposite(), stable);
				}
				break;
			case 1:
				System.out.println("Mekkora kapacitású legyen?");
				int c = scan.nextInt();
				UnstableIceCell unstable = new UnstableIceCell(c ,this );
				field.get(y).set(x, unstable);
				buildNeighbours(unstable, y, x);
				for(Way w : Way.values()) {
					if (unstable.getNeighbour(w) != null)
						unstable.getNeighbour(w).addNeighbour(w.opposite(), unstable);
				}
				break;
			case 2:
				WaterCell water = new WaterCell(this);
				field.get(y).set(x, water);
				buildNeighbours(water, y, x);
				for(Way w : Way.values()){
					if(water.getNeighbour(w) != null)
						water.getNeighbour(w).addNeighbour(w.opposite(), water);
				}
				break;
			default: System.out.println("Nincs ilyen cella"); return;
		}
		cellTable[y][x] = i;

		System.out.println("Hány hóréteget szeretnél rá? (0-5)");
		int snow = scan.nextInt();
		for(int j = 0; j < snow; j++)
			field.get(y).get(x).gainOneSnow();

		drawField();
	}
	public void putItem(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Milyen Itemet akar lerakni? (divingsuit, essentialitem, food, fragile, rope, shovel, tent)");
		String s = scan.nextLine();
		System.out.println("Hova szeretné lerakni? (Szóközzel elválasztva x és y, kisebb mint, " + fieldLengths + ")");
		int x = scan.nextInt();
		int y = scan.nextInt();
		switch(s){
			case "divingsuit":
				StableIceCell stable1 = new StableIceCell(this, new Divingsuit());
				field.get(y).set(x, stable1);
				buildNeighbours(stable1, y, x);
				for(Way w : Way.values()) {
					if (stable1.getNeighbour(w) != null)
						stable1.getNeighbour(w).addNeighbour(w.opposite(), stable1);
				}
				break;
			case "essentialitem":
				System.out.println("Milyen ID-val");
				int id = scan.nextInt();
				StableIceCell stable2 = new StableIceCell(this, new EssentialItem(id, wc));
				field.get(y).set(x, stable2);
				buildNeighbours(stable2, y, x);
				for(Way w : Way.values()) {
					if (stable2.getNeighbour(w) != null)
						stable2.getNeighbour(w).addNeighbour(w.opposite(), stable2);
				}
				break;
			case "food":
				StableIceCell stable3 = new StableIceCell(this, new Food());
				field.get(y).set(x, stable3);
				buildNeighbours(stable3, y, x);
				for(Way w : Way.values()) {
					if (stable3.getNeighbour(w) != null)
						stable3.getNeighbour(w).addNeighbour(w.opposite(), stable3);
				}
				break;
			case "fragile":
				StableIceCell stable4 = new StableIceCell(this, new FragileShovel());
				field.get(y).set(x, stable4);
				buildNeighbours(stable4, y, x);
				for(Way w : Way.values()) {
					if (stable4.getNeighbour(w) != null)
						stable4.getNeighbour(w).addNeighbour(w.opposite(), stable4);
				}
				break;
			case "rope":
				StableIceCell stable5 = new StableIceCell(this, new Rope());
				field.get(y).set(x, stable5);
				buildNeighbours(stable5, y, x);
				for(Way w : Way.values()) {
					if (stable5.getNeighbour(w) != null)
						stable5.getNeighbour(w).addNeighbour(w.opposite(), stable5);
				}
				break;
			case "shovel":
				StableIceCell stable6 = new StableIceCell(this, new Shovel());
				field.get(y).set(x, stable6);
				buildNeighbours(stable6, y, x);
				for(Way w : Way.values()) {
					if (stable6.getNeighbour(w) != null)
						stable6.getNeighbour(w).addNeighbour(w.opposite(), stable6);
				}
				break;
			case "tent":
				StableIceCell stable7 = new StableIceCell(this, new Tent());
				field.get(y).set(x, stable7);
				buildNeighbours(stable7, y, x);
				for(Way w : Way.values()) {
					if (stable7.getNeighbour(w) != null)
						stable7.getNeighbour(w).addNeighbour(w.opposite(), stable7);
				}
				break;
			default: System.out.println("Nincs ilyen tárgy"); return;
		}
		cellTable[y][x] = 3;
		drawField();
	}
	public void putBear() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Hova szeretné lerakni a medvét? (Szóközzel elválasztva x és y, kisebb, mint " + fieldLengths + ")");
		int x = scan.nextInt();
		int y = scan.nextInt();
		bear.getOwnCell().removeBear();
		bear = new Bear(field.get(y).get(x));
		if(field.get(y).get(x).getPlayers() != 0) gameLost();
		drawField();
	}
	public void putCharacter(){
		maxPlayer = 3;
		Scanner scan = new Scanner(System.in);
		IceCell ownCell = null;
		boolean safeToStart = false;
		while(!safeToStart){
			System.out.println("Hova szeretné lerakni a játékosokat? (Szóközzel elválasztva x és y, kisebb, mint " + fieldLengths + ")");
			int x = scan.nextInt();
			int y = scan.nextInt();
			ownCell = field.get(y).get(x);
			if(ownCell.safeToStart()){
				safeToStart = true;
			}
			else System.out.println("Nem biztonságos, válassz másikat!");
		}
		for(int i = maxPlayer; i > 0;){
			System.out.println("Milyen karaktert választ? (eskimo-explorer)" );
			System.out.println("Még " + i + " darabot választhat.");
			String s = scan.nextLine();
			Character ch = null;
			switch(s) {
				case "eskimo":
					ch = new Eskimo(ownCell);
					ownCell.addCharacter(ch);
					characters.add(ch);
					i--;
					break;
				case "explorer":
					ch = new Explorer(ownCell);
					ownCell.addCharacter(ch);
					characters.add(ch);
					i--;
					break;
				default: System.out.println("Nincs ilyen karakter."); break;
			}

			if(ch != null){
				System.out.println("Milyen tárgyat akar neki adni? (exittel tud kilépni)" );
				s = scan.nextLine();
				while(s != "exit") {
					switch (s){
						case "divingsuit":
							ch.putItemtoBackPack(new Divingsuit(), PlayerActions.wearingSuit);
							break;
						case "essentialitem":
							System.out.println("Milyen ID-val");
							int id = scan.nextInt();
							ch.putItemtoBackPack(new EssentialItem(id, wc), PlayerActions.assemblingEssentials);
							break;
						case "food":
							ch.putItemtoBackPack(new Food(), PlayerActions.eating);
							break;
						case "fragile":
							ch.putItemtoBackPack(new FragileShovel(), PlayerActions.fragileshoveling);
							break;
						case "rope":
							ch.putItemtoBackPack(new Rope(), PlayerActions.savingWithRope);
							break;
						case "shovel":
							ch.putItemtoBackPack(new Shovel(), PlayerActions.shoveling);
							break;
						case "tent":
							ch.putItemtoBackPack(new Tent(), PlayerActions.setUpTent);
							break;
						default: System.out.println("Nincs ilyen tárgy"); break;
					}
					s = scan.nextLine();
				}
			}

		}
		drawField();
	}
}
