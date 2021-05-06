package logic;

/**
 * Ez az osztály ellenőrzi, hogy a játékosok össze tudják-e szerelni a jelzőpisztolyt, ezzel megnyerve a játékot.
 */
public class WinChecker {
	/**
	 * Számláló, ami azt tartja nyilván, hogy az egy mezőn álló karaktereknél
	 * (azon karakterek, akik az összerakást kezdeményező játékossal egy mezőn állnak)
	 * összesen hány EssentialItem van.
	 */
	private int assemblingEssentials = 0;
	/**
	 * Statikus attribútum, ami az összegyűjtendő tárgyak mennyiségét tárolja.
	 */
	private static int maxEssentials = 3;

	/**
	 * Megnöveli a jelzőpisztoly-darab számláló értékét.
	 * @param i Az az érték, amennyivel az összeszerelhető alkatrészek számát megnöveljük.
	 */
	public void addEssentials(int i) { assemblingEssentials += i; }
	/**
	 * Visszaállítja nullára a jelzőpisztoly-darab számláló értékét.
	 */
	public void resetAssembledItems() { assemblingEssentials = 0;}
	/**
	 * Megmondja, hogy sikerült-e összeszerelni a jelzőpiszolyt.
	 * @return True, ha az összeszerelés sikeres.
	 */
	public boolean isAssembled() { return assemblingEssentials == maxEssentials; }
}
