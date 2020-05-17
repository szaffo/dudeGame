package physics;

/**
 * Ez egy x,y koordináta párost összefogó osztály
 * @author Szabó Martin
 */
public class Coordinate {
    public double x;
    public double y;

    public Coordinate() {
        x = 0;
        y = 0;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
     }

    /**
     * Összead két pozíciót
     */
    public void add(Coordinate other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Páronként összeszorozza a koordinátákat
     */
    public void multiply(Coordinate other) {
        this.x *= other.x;
        this.y *= other.y;
    }
    
    /**
     * Mindkét tagot megszorozza az adott számmal
     * @param number
     */
    public void multiply(double number) {
        this.x *= number;
        this.y *= number;
    }

    // Getterek
    public double getX() {return x;}
    public double getY() {return y;}

    /**
     * Egy teljesen új objektumot hoz létre, lemásolva az eredetit
     * @return Coordinate
     */
    public Coordinate deepCopy() {
        return new Coordinate(this.getX(), this.getY());
    }
}