package logic.characters;

import logic.IceField;
import logic.Way;
import logic.icecells.IceCell;

import java.util.Random;

/**
 * A pályán mozgó jegesmedvét valósítja meg, amivel ha a játékosok egy mezőre kerülnek, akkor megeszi őket.
 */
public class Bear {
    /**
     * A medve saját mezőjét ebben az attribútumban tároljuk.
     */
    private IceCell ownCell;

    public Bear(IceCell ownc){
        ownCell = ownc;
        ownCell.acceptBear(this);
    }

    /**
     * Lépteti a medvét egy random irányba, és eltávolítja az aktuális mezejéről.
     */
    public void move(){
        Random r = new Random();
        boolean accepted = false;
        IceCell prevOwnCell = ownCell;

        while(!accepted){
            int i = r.nextInt(Way.values().length);
            IceCell ic = ownCell.getNeighbour(Way.values()[i]);
            if(ic != null) accepted = ic.acceptBear(this);
        }
        prevOwnCell.removeBear();
    }
    public void setOwnCell(IceCell ownc) {
        ownCell = ownc;
    }
}
