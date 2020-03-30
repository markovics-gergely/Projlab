package logic.items;

import logic.characters.Character;

public class Tent implements Items {

    public void use(Character actualch) {
        if(actualch.getOwnCell().setTent(true)){
            actualch.loseOneAction();
        }
    }
    public boolean equip(Character ch) {
        return ch.putItemtoBackPack(this, PlayerActions.useTent);
    }
}
