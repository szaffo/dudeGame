package model;

import controller.Direction;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * @author Simonyi Patrik
 * @author Szabó Martin
 */
public class Robot extends FallingItem implements Enemy {

    private boolean isAttacking;
    private int tickCount;
    private int nextWaitRandomOffset;
    private int nextWalkRandomOffset;
    private Random rng;
    private Image leftImage;
    private Image rightImage;
    private Image attackLeftImage;
    private Image attackRightImage;

    public Robot(Model parent, double _posX, double _posY) {
        super(parent, _posX, _posY, 60, 80);
        speed = 6;
        isAttacking = false;
        tickCount = 0;

        rightImage = new ImageIcon("images/robot_right.png").getImage();
        leftImage = new ImageIcon("images/robot_left.png").getImage();
        attackLeftImage = new ImageIcon("images/attack_left.png").getImage();
        attackRightImage = new ImageIcon("images/attack_right.png").getImage();
        
        this.rng = new Random();
        
        if ((rng.nextInt() % 2) == 1) {
            this.moveOn(Direction.RIGHT);
        } else {
            this.moveOn(Direction.LEFT);
        }

        nextWalkRandomOffset = rng.nextInt() % 20;
    }

    @Override
    public Image getImage() {
        if (this.direction == Direction.RIGHT) return rightImage;
        return leftImage;
    }

    @Override
    public boolean tick() {
        tickCount++;

        if (tickCount == (20 + nextWalkRandomOffset)) {
            // Enters to waiting stage
            
            isAttacking = true;
            moveOff(this.direction);
            nextWaitRandomOffset = rng.nextInt() % 20;
        
        } else if (tickCount == (90 + nextWaitRandomOffset)) {
            // Enters to walking stage

            isAttacking = false;
            moveOn(this.direction.getOppositie());
            nextWalkRandomOffset = rng.nextInt() % 20;
            tickCount = 0;
        }

        super.tick();

        return true;
    }

    /**
     * Le lehet kérdezni, hogy a Robot éppen támad-e
     * @return True ha a robot támad
     */
    public boolean isAttacking() {
        return this.isAttacking;
    }

    /**
     * A lövést reprezentáló téglalap
     * @return
     */
    public Rectangle getAttackingBoundaries() {
        int x = getLaserPosX();
        int y = getLaserPosY();
        int height = 10;
        int width = 160;
        return new Rectangle(x,y,width,height);
    }

    /**
     * Megnézi hogy az agent ütközik-e a robottal. 
     * Ha a robot éppen támad, akkor azt is, hogy az
     * agent ütközik-e a lézerrel.
     */
    public boolean collidesWith(Agent player) {
        boolean bodyCollide = this.getMiddleBoundaries().intersects(player.getMiddleBoundaries());
        boolean shotCollide = false;
        if (isAttacking()) {
            shotCollide = this.getAttackingBoundaries().intersects(player.getBoundaries());
        }

        return (bodyCollide || shotCollide);
    }

    /**
     * A nézés irányától függően az egyik lézer sprite neve
     * @return
     */
    public Image getLaserImage() {
        if (direction == Direction.LEFT) {
            return attackLeftImage;
        } else {
            return attackRightImage;
        }
    }

    public int getLaserPosX() {
        int x = (int) (getPosX() + (getWidth() / 2));
        if (this.direction == Direction.LEFT) {
            x = x - 160;
        }
        return x;
    }

    public int getLaserPosY() {
        return (int) (getPosY() + (getHeight() * 0.3));
    }
}