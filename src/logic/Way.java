package logic;

public enum Way {
    up,
    right,
    down,
    left;

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
