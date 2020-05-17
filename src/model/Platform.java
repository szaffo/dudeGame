package model;

import javax.swing.ImageIcon;

/**
 * Ez az osztály implementálja a platformok tuljadonságait
 * @author Szabó Martin
 */
public class Platform extends Obstacle {

    public Platform(Model parent, double _posX, double _posY) {
        super(parent, _posX, _posY, 100, 30);
        image = new ImageIcon("images/platform3.png").getImage();
    }

}