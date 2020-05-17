package controller;

/**
 * Ez a felsoroló az irányokkal való munkát hivatott megkönnyíteni.
 * Minden irány rendelkezik x és y kordinátákkal, így akár egységvektorokként
 * is értelmezhetőek.
 * @author Szabó Martin
 */
public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    RIGHT(0, 1),
    LEFT(0, -1),
    SPACE(0, 0);

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //Getterek  az x és y koordinátákra
    public int getCol() {return this.col;}
    public int getRow() {return this.row;}

    private int col;
    private int row;

    /**
     * Megvizsgálja, hogy két irány ellenkező irányú-e
     */
	public boolean isOpposite(Direction direction) {
		return (((this.col + direction.col) == 0) || ((this.row + direction.row) == 0));
    }
    
    /**
     * Megnézi, hogy a két irány azonos-e
     */
    public boolean isSame(Direction direction) {
        return ((this.col == direction.col) && (this.row == direction.row));
    }

    /**
     * Ugyan az mint a Direction.equals()
     */
    public boolean equals(Direction other) {
        return isSame(other);
    }

    /**
     * Visszad egy ellentétes irányt
     * @return
     */
    public Direction getOppositie() {
        switch (this) {
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            
            default:
                return this;          
        }
    }

}