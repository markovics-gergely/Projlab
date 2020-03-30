package logic.items;

import logic.characters.Character;

public class Tent implements Items {
    public void use(Character actualch) {
        //Nincs benne a loseOneAction, rakd bele
        actualch.getOwnCell().setTent(true);
    }
    public boolean equip(Character ch) {
        return ch.putItemtoBackPack(this, PlayerActions.useTent);
    }
}
