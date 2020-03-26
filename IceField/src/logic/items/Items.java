package logic.items;

import logic.characters.Character;

public interface Items {
	public void use(Character actualch);
	public boolean equip(Character ch);
}
