package model;

import controller.Direction;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Ez az Agent osztály
 * 
 * @author Szabó Martin
 * @author Leczó Gábor Bálint
 * @since 2020-04-11
 */
public class Agent extends FallingItem {

    private Furniture furniture;
    private boolean inLooting;
    private boolean lootStateChanged;
    public int lootCounter;

    protected Image leftImage;
    protected Image rightImage;
    protected Image jumpImage;
    protected Image fallImage;
    protected Image standImage;
    

    public Agent(Model parent, double _posX, double _posY) {
        super(parent, _posX, _posY, 50, 100);
        direction = Direction.LEFT;
        inLooting = false;
        lootStateChanged = false;
        lootCounter = 0;
        sound = "jump";
        this.rightImage = new ImageIcon("images/player_right_walking.png").getImage();
        this.leftImage = new ImageIcon("images/player_left_walking.png").getImage();
        this.standImage = new ImageIcon("images/player_standing.png").getImage();
        this.fallImage = new ImageIcon("images/player_falling.png").getImage();
        this.jumpImage = new ImageIcon("images/player_jumping.png").getImage();
    }

    /**
     * Visszaadja hogy milyen irányba haladt legutóbb az objektum
     * 
     * @return
     */
    public Direction getDirection() {
        return this.direction;
    }

    public boolean isLooting() {
        return this.inLooting;
    }

    public int getLootProgress() {
        return this.furniture.getSearchProgress();
    }

    public int getLootCount() {
        return this.lootCounter;
    }

    @Override
    public Image getImage() {
        if (standing)
            return standImage;
        if (falling)
            return fallImage;
        if (jumping)
            return jumpImage;
        if (shift.getX() > 0 && shift.getY() == 0)
            return rightImage;
        if (shift.getX() < 0 && shift.getY() == 0)
            return leftImage;

        return standImage;
    }

    /**
     * Hívásra az  Agent keresni kezd a megadott bútorban
     * @author Szabó Martin
     * @param furniture
     */
    public void startLooting(Furniture furniture) {
        if (furniture.isLootedBefore())
            return;
        this.furniture = furniture;
        this.inLooting = true;
        this.lootStateChanged = true;
    }

    /**
     * Hívásra abbahagyja a keresést az Agent
     * @author Szabó Martin
     */
    public void stopLooting() {
        this.inLooting = false;
        this.lootStateChanged = true;
    }

    @Override
    public boolean tick() {
        boolean hasChange = lootStateChanged;
        super.tick();

        if (inLooting) {
            inLooting = furniture.collidesWith(this);
            if (furniture.loot()) {
                hasChange = true;
                inLooting = false;
                // System.out.println("Loot found");
                lootCounter++;
            }
        }

        if (this.position.y > 900) {
            parent.resetPlayerPosition();
            makeSound("death");
        }
        
        return hasChange;
    }

    @Override
    public void jump() {
        super.jump();
        makeSound();
    }

}