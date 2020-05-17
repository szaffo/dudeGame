package model;

import controller.Direction;

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

    public Agent(Model parent, double _posX, double _posY) {
        super(parent, _posX, _posY, 50, 100);
        direction = Direction.LEFT;
        inLooting = false;
        lootStateChanged = false;
        lootCounter = 0;
        sound = "jump";
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

    /**
     * Az ős osztályban (FallingItem) implementált állapotoktól függően
     * meghatározza, hogy milyen képet kell megjeleníteni.
     */
    @Override
    public String getImageName() {
        if (standing)
            return "player_standing.png";
        if (falling)
            return "player_falling.png";
        if (jumping)
            return "player_jumping.png";
        if (shift.getX() > 0 && shift.getY() == 0)
            return "player_right_walking.png";
        if (shift.getX() < 0 && shift.getY() == 0)
            return "player_left_walking.png";

        return "player_standing.png";
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