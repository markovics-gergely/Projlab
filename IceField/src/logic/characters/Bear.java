package logic.characters;

import logic.IceField;
import logic.Way;
import logic.icecells.IceCell;

import java.util.Random;

public class Bear {
    private IceCell ownCell;

    public Bear(IceCell ownc){
        ownCell = ownc;
    }

    public void move(){
        Random r = new Random();
        IceCell ic = null;
        while(ic == null){
            int i = r.nextInt(Way.values().length);
            ic = ownCell.getNeighbour(Way.values()[i]);
        }
        ownCell.removeBear();
        ic.acceptBear(this);
    }

    public void setOwnCell(IceCell ownc) {
        ownCell = ownc;
    }
}
