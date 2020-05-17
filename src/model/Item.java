package model;

import java.awt.Rectangle;

import controller.SoundMaker;
import physics.Size;
import physics.Position;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Generic Item class
 * @author Simonyi Patrik
 * @author Szabó Martin
 * @author Leczó Gábor
 */
public class Item {

    protected Image image;
    protected Position position;
    protected Size size;
    protected SoundMaker soundMaker;
    protected Model parent; 
    protected String sound;

    public Item(Model parent, double x, double y, int width, int height) {
        this.parent = parent;
        this.position = new Position(x,y);
        this.size = new Size(width, height);
        this.image = new ImageIcon("images/placeholder.png").getImage();
        soundMaker = new SoundMaker();
        sound = null;
    }

    public Item(Model parent, Position pos, Size cords) {
        this.parent = parent;
        this.position = pos;
        this.size = cords;
        soundMaker = new SoundMaker();
    }

    /**
     * Visszatér egy az objektum méreteivel megegyező téglalappal
     * @author Szabó Martin
     * @return Rectangle
     */
    public Rectangle getBoundaries() {
        return new Rectangle((int) getPosX(), (int) getPosY(), getWidth(), getHeight());
    }

    /**
     * Megvizsgálja, hogy this ütközik-e other-el
     * @param other a másik Item
     * @author Szabó Martin
     * @author Simonyi Patrik
     */
    public boolean collidesWith(Item other) {
        return getBoundaries().intersects(other.getBoundaries());
    }

    //Getterek és setterek
    public double getPosX() {return this.position.x;}

    public void setPosX(double _posX) {this.position.x = _posX;}

    public double getPosY() {return this.position.y;}

    public void setPosY(double _posY) {this.position.y = _posY;}

    public int getWidth() {return (int) this.size.x;}
    
    public void setWidth(int _width) {this.size.x = _width;}
    
    public int getHeight() {return (int) this.size.y;}
    
    public void setHeight(int _height) {this.size.y = _height;}
    
    public Position getPosition() {return this.position;}
    
    public Size getSize() {return this.size;}

    public void setPosition(Position p) {
        Position pos = p.deepCopy();
        this.position = pos;
        // System.out.println(this.toString() + " moved to " + pos.toString());
    }
    
    /**
     * Visszatér az osztályt képviselő kép nevével.
     */
    public Image getImage() {return this.image;}

    /**
     * Ez a metódus meghívódik minden game tick alkalmával.
     * Ebben az osztályban nincs implementálva semmi hozzá.
     * Ez a gyerek osztályok feladata a szerepkörüknek megfelelően.
     * @return true ha volt lényegbeli változás, false ha nem
     */
    public boolean tick() {return false;}

    @Override
    public String toString() {
        return  this.getClass().getName() + "(" + getPosX() + ", " + getPosY() +", " + getHeight() + ", " + getWidth() + ")";
    }

    /**
     * Ha van az Item-hez megadva hang, akkor azt
     * lejátsza a soundMaker-rel
     * @author Szabó Martin
     */
    public void makeSound() {
        if (!(sound == null)) {
            soundMaker.play(sound);
        }
    }

    public void makeSound(String sound) {
        if (!(sound == null)) {
            soundMaker.play(sound);
        }   
    }
}