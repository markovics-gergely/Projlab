package logic.items;

import logic.characters.Character;

public class FragileShovel implements Items {
    private int capacityMax = 3;
    private int capacity = capacityMax;

    public void use(Character actualch) {
        if(actualch.dig(true)) {
            actualch.loseOneAction();
            capacity--;
            if (capacity == 0) {
                actualch.getBackPack().removeItem(PlayerActions.fragileshoveling);
            }
        }
    }
    public boolean equip(Character ch) {
        return ch.putItemtoBackPack(this, PlayerActions.fragileshoveling);
    }
}
