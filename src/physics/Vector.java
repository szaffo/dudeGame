package physics;

/**
 * Ez egy osztály, ami a kordinátát újra értelmezi vektorként
 * @author Szabó Martin
 */
public class Vector  extends Coordinate {

	public Vector() {
        x = 0;
        y = 0;
    }

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
}