package model;

import physics.Size;
import physics.Position;

/**
 * Olyan speciális Item, ami mellett nem tud elmenni az Agent
 * @author Szabó Martin
 * @since 2020-04-26
 */
public class Obstacle extends Item {
    public Obstacle(Model parent, double x, double y, int width, int height) {
        super(parent, x,y,width,height);
    }

    public Obstacle(Model parent, Position pos, Size cords) {
        super(parent, pos, cords);
    }
}