package view;

/**
 * Ezt az osztály arra használjuk, hogy kiragadjuk egy Item-ből annak
 * lényeges adatait (pozíció, méret és a hozzá tartozó kép neve), hogy azt
 * a View felé közöljük. Így a view megkapja a számára elengedhetetlen információt
 * minden itemről, de nem tud meg arról semmi plusz dolgot.
 * Ezeknek a becsomagoló objektumoknak a létrehozásáért a Model legfelsőbb komponense felelős.
 * @author Szabó Martin
 */
public class ImageWrapper {
    public int posX;
    public int posY;
    public String image;
    
    public ImageWrapper(String imgName, int x, int y) {
        this.image = imgName;
        this.posX = x;
        this.posY = y;
    }

    public ImageWrapper(String imgName, double x, double y) {
        this.image = imgName;
        this.posX = (int) Math.round(x);
        this.posY = (int) Math.round(y);
    }
}