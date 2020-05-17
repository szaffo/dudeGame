package physics;

/**
 * Ez egy méretet leíró osztály
 * @author Szabó Martin
 */
public class Size extends Coordinate {

    public Size() {
        x = 0;
        y = 0;
    }

    public Size(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Size(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getWidth() {return getX();}
    public double getHeight() {return getY();}

}