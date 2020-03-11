package logic;

public enum Way {
    up,
    right,
    down,
    left;

    public Way opposite(){
        switch(this) {
            case up: return down;
            case right: return left;
            case down: return up;
            case left: return right;
            default: throw new IllegalStateException("This should never happen: " + this + " has no opposite.");
        }
    }
}
