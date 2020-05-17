package model;

import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Ez az osztály implementálja a Doort amin keresztül átmehetünk másik szobába.
 * @author Leczó Gábor Bálint
 */
public class Door extends Item {

    private String level;

    public Door(Model parent, double _posX, double _posY,String level) {
        super(parent, _posX, _posY,100,120);
        image = new ImageIcon("images/door.png").getImage();
        this.level = level;
        this.sound = "door";
    }

    @Override
    public boolean tick() {
        if(collidesWith(parent.getPlayer())) {
            parent.setCurrentActiveItemSet(level);
            makeSound();
        }
        return false;
    }

    /**
     * @author Szabó Martin
     */
    @Override
    public Rectangle getBoundaries() {
        int width = (int) (getWidth() * 0.2);
        int height = (int) (getHeight() * 0.7);
        int x = (int) (getPosX() + ((getWidth() - width) / 2));
        int y = (int) (getPosY() + (getHeight() - height));
        return new Rectangle(x, y, width, height);
    }
}