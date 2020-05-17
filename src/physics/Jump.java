package physics;

/**
 * @author Leczó Gábor Bálint
 */
public class Jump {

    public static final double FRICTION = 0.99;
    public static final double GRAVITY = 0.01;

    private double posX;
    private double posY;
    private double speedX = 0;
    private double speedY = 0;

    public Jump(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void accelerate(double accelerationX, double accelerationY) {
        speedX += accelerationX;
        speedY += accelerationY;
    }

    public void move(double xDelta, double yDelta) {
        posX += xDelta;
        posY += yDelta;
        // do collision detection here. upon collision, set speedX/speedY to zero..!
    }

    public void update() {
        move(speedX, speedY);
        speedX *= FRICTION;
        speedY *= FRICTION;
        accelerate(0, -GRAVITY);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}