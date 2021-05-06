package logic.items;

import logic.characters.Character;

public interface Items {
	/**
	 * Az implementált tárgyat használja az a karakter, amit paraméterül kap.
	 * @param actualch akutális karakter
	 */
	public void use(Character actualch);
	/**
	 * Tárgy hozzáadása a paraméterül kapott karakter táskájához. Ha nem tudta hozzáadni, akkor hamissal tér vissza.
	 * @param ch aktuális karakter
	 * @return igaz ha sikerüt belerakni a táskába
	 */
	public boolean equip(Character ch);
}
