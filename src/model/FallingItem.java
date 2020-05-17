package model;

import java.util.Date;

import java.awt.Rectangle;

import controller.Direction;
import physics.Vector;
import physics.Position;

/**
 * Ebben az osztályban van implementálva az Item-ek fizikája.
 * @author Szabó Martin
 * @since 2020-04-11
 */
public class FallingItem extends Item {

    private Vector momentum;
    private Vector jumpingVector;
    // private Position startingPosition;
    private long time;
    private int updateStage;
    protected Vector gravityVector;
    protected Vector goalVelocity;
    protected Vector shift;
    protected Direction direction;
    protected boolean falling;
    protected boolean jumping;
    protected boolean standing;
    protected int speed;

    public FallingItem(Model parent, double _posX, double _posY, int _width, int _height) {
        super(parent, _posX, _posY, _width, _height);
        momentum = new Vector(0,0);
        shift = new Vector(0,0);
        goalVelocity = new Vector(0,0);
        gravityVector = new Vector(0,20);
        jumpingVector = new Vector(0,40);
        jumping = false;
        falling = true;
        standing = false;
        updateStage = 0;
        time = 0;
        imageName = "placeholder.png";
        // this.startingPosition = new Position(_posX,_posY);
        speed = 10;
    }

    /**
     * Vissza allitja az objektum poziciojat a kezdo poziciora
     * @author Simonyi Patrik
     * @author Szabó Martin (deepCopy() hozzáadás hardcode helyett)
     */
    public void resetPosition() {
        this.setPosition(parent.getItems().getSpawnPoint());
    }

    /**
     * Vissztér egy téglalappal, ami az Item közepén van
     */
    public Rectangle getMiddleBoundaries(Vector shift) {
        int height = getHeight();
        double width = getWidth() * 0.6;
        int x = (int) (getPosX() + ((getWidth() - width) /2 ));
        int y = (int) (getPosY() + shift.getY());
        return new Rectangle(x,y,(int) width, height);
    }
    public Rectangle getMiddleBoundaries() {return getMiddleBoundaries(new Vector(0,0));}

    /**
     * Vissztér egy téglalappal, ami az Item bal oldalán van
     */
    public Rectangle getLeftBoundaries(Vector shift) {
        int height = (int) (getHeight() * 0.9);
        int width = 2;
        int x = (int) (getPosX() + shift.getX());
        int y = (int) getPosY() + ((getHeight() - height) / 2);
        return new Rectangle(x, y, width, height);
    }
    public Rectangle getLeftBoundaries() {return getLeftBoundaries(new Vector(0,0));}

    /**
     * Vissztér egy téglalappal, ami az Item jobb oldalán van
     */
    public Rectangle getRightBoundaries(Vector shift) {
        int height = (int) (getHeight() * 0.9);
        int width = 2;
        int x = (int) ((getPosX() + getWidth() - width) + shift.getX());
        int y = (int) getPosY() + ((getHeight() - height) / 2);
        return new Rectangle(x, y, width, height);
    }
    public Rectangle getRightBoundaries() {return getRightBoundaries(new Vector(0,0));}

    /**
     * Megvizsgálja, hogy az adott Item ütközik-e a szülő által tárolt bármelyik itemmel.
     * A vizsgálat függ a mozgási iránytól, és módosítja a poziciót ha szükséges.
     */
    public Vector checkCollision(Vector shiftBy) {

         // Check right
        if (shiftBy.getX() > 0) {
            standing = false;
            for (Item item : parent.getItems()) {
                if (!(item instanceof Obstacle)) continue;
                if (this.getRightBoundaries(shiftBy).intersects(item.getBoundaries())) {
                    shiftBy.x = item.getPosX() - (getPosX() + getWidth());
                    standing = true;
                }
            }
        }

        // Check left
        if (shiftBy.getX() < 0) {
            standing = false;
            for (Item item : parent.getItems()) {
                if (!(item instanceof Obstacle)) continue;
                if (this.getLeftBoundaries().intersects(item.getBoundaries())) {
                    shiftBy.x = (item.getPosX() + item.getWidth()) - this.getPosX() + 1;
                    standing = true;
                }
            }
        }

        // Check down
        if (shiftBy.getY() > 0) {
            falling = true;
            for (Item item : parent.getItems()) {
                if (!(item instanceof Obstacle)) continue;
                if (this.getMiddleBoundaries(shiftBy).intersects(item.getBoundaries())){
                    shiftBy.y = item.getPosY() - (this.getPosY() + this.getHeight());
                    falling = false;
                }
            }
        }

        // Check up
        if (shiftBy.getY() < 0) {
            for (Item item : parent.getItems()) {
                if (!(item instanceof Obstacle)) continue;
                if (this.getMiddleBoundaries(shiftBy).intersects(item.getBoundaries())) {
                    shiftBy.y = (item.getPosY() + item.getHeight()) - this.getPosY();
                    jumping = false;
                    falling = true;
                }
            }
        }

        return shiftBy;
    }

    /**
     * Ez a metódus lefut minden game tick alkalmával
     * és frissíti az adatoka ha kell.
     * Ha volt lényeges frissítés (elmozdult az item),
     * akkor true-val tér visza, máskülönben false
     */
    @Override
    public boolean tick() {
        // Initializing time at the first tick
        if (time == 0) time = new Date().getTime();

        // Calculate delta time
        long newTime = new Date().getTime();
        double deltaT = (newTime - time) / 10 * 3.2;
        time = newTime;

        // Calculate new velocity depending on the time passed and the goal
        momentum.x = approach(goalVelocity.x, momentum.x, deltaT);
        momentum.y = approach(goalVelocity.y, momentum.y, deltaT);

        // This is a placeholder vector that I made to
        // describe the shifting with only one vector 
        this.shift = new Vector(0,0);
        shift.add(momentum);

        // Adding the gravity if the item is in falling
        // if (!standing)
        shift.add(gravityVector);

        // If in jumping, adding the jump vector
        if (jumping) {
            shift.y -= jumpingVector.y * deltaT;
            shift.x -= jumpingVector.x * deltaT;
            updateJumpVelocity(deltaT);
        }

        // Save the current position
        Position beforeShift = new Position(0,0);
        beforeShift.add(position);

        // Check collision, and recalculate position if needed
        shift = checkCollision(shift);

        // Calculate new position depending on the shift
        position.add(shift);

        // System.out.println("Shifted with " + shift.toString());
        if ((updateStage < 2) && !((shift.getX()) != 0 || (shift.getY() != 0))) updateStage++;
        if (((shift.getX()) != 0 || (shift.getY() != 0)) && updateStage == 2) updateStage = 0;
        return updateStage < 2;
    }

    /**
     * Ez egy segédfüggvény, ami a gyorsulás mértékét számolja ki
     * két game tick között, az eltelt időtől függően.
     * @param goal
     * @param current
     * @param deltaT
     * @return
     */
    protected double approach(double goal, double current, double deltaT) {
        double difference = goal - current;

        if (difference > deltaT) {
            return current + deltaT;
        }

        if (difference < -deltaT) {
            return current - deltaT;
        }

        return goal;
    }

    /**
     * Jelzés az objektum számára, hogy kezdjen el mozogni.
     * @param direction milyen irányba mozogjon (LEFT, RIGHT)
     */
    public void moveOn(Direction direction) {
        switch (direction) {
            case LEFT:
                goalVelocity.x -= speed;
                this.direction = Direction.LEFT;
                break;
                
            case RIGHT:
                goalVelocity.x += speed;
                this.direction = Direction.RIGHT;
                break;
            default:
                break;
        }
    }

    /**
     * Jelzés az objektum számára, hogy álljon meg.
     * @param d milyen irányból jött a stop. (Az objektum egyszerre több irányba is elkezdhet mozgást)
     */
    public void moveOff(Direction d) {
        goalVelocity.x -= d.getCol() * speed;
        standing = true;
    }

    /**
     * Jelzés az objektum számára, hogy ugorjon egyet.
     * Csak akkor ugrik, ha már nincs ugrásban, és nincs szabadesésben
     */
    public void jump() {
        if (jumping || falling) return;
        
        jumpingVector.x = -1 * direction.getCol();
        jumpingVector.y = 15;
        jumping = true;
    }

    /**
     * Frissíti az ugrás vektorát a időtől függően
     * @param deltaT
     */
    private void updateJumpVelocity(double deltaT) {
        jumpingVector.y -= gravityVector.y * (deltaT / 100);
        jumpingVector.x -= gravityVector.x * (deltaT / 100);
        if (jumpingVector.y <= 0) {
            jumping = false;
            falling = true;
        }
            
    }

}