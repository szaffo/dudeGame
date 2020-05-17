package physics;

/**
 * Ez egy pozíciót leíró osztály
 * @author Szabó Martin
 */
public class Position extends Coordinate {
    public Position() {
        x = 0;
        y = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Egy teljesen új objektumot hoz létre, lemásolva az eredetit
     * 
     * @return Position
     */
    public Position deepCopy() {
        return new Position(this.getX(), this.getY());
    }
}