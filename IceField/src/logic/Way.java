package logic;

public enum Way {
    up,         //felfele jobbra
    right,
    down,       //lefele jobbra
    left,
    upleft,     //felfele balra DE MINEK EZ HA ALAPBÓL CSAK 4 OLDALÚ AZ ALAP PÁLYA
    downleft;   //lefele balra

    /**
     * Visszaadja az ellenkező irányt annak, amin meghívták.
     * @return  Visszaadja az ellenkező irányt annak, amin meghívták.
     */
    public Way opposite(){
        switch(this) {
            case up: return down;
            case right: return left;
            case down: return up;
            case left: return right;
            case upleft: return downleft; //FÖLÖSLEGES CSAK TESZTRE KELL MERT 6 OLDALÚ A TESZT MIKÖZBEN NEM FELADAT
            case downleft: return upleft; //FÖLÖSLEGES CSAK TESZTRE KELL MERT 6 OLDALÚ A TESZT MIKÖZBEN NEM FELADAT
            default: throw new IllegalStateException("Opposite Error");
        }
    }

    /**
     * Elforgatja 90 fokkal az irányt amin
     * meghívták, a paraméter szerinti irányba.
     * @param toRight jobbra vagy balra
     * @return irány
     */
    public Way rotate(boolean toRight){
        switch(this) {
            case up: return toRight ? right : left;
            case right: return toRight ? down : up;
            case down: return toRight ? left : right;
            case left: return toRight ? up : down;
            default: throw new IllegalStateException("Rotate Error");
        }
    }
}
