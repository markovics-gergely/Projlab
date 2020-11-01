package logic;

public class WinChecker {
	private int assemblingEssentials = 0;
	private static int maxEssentials = 3;

	public void addEssentials(int i) { assemblingEssentials += i; }
	public void resetAssembledItems() { assemblingEssentials = 0;}
	public boolean isAssembled() { return assemblingEssentials == maxEssentials; }
}
