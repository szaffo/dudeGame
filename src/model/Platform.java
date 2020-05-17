package model;

/**
 * Ez az osztály implementálja a platformok tuljadonságait
 * @author Szabó Martin
 */
public class Platform extends Obstacle {

    public Platform(Model parent, double _posX, double _posY) {
        super(parent, _posX, _posY, 100, 30);
        this.imageName = "platform3.png";
    }

}