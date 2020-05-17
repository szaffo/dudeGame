package controller;

import java.io.File;

/**
 * @author Leczó Gábor Bálint
 */
public class SoundMaker {

    public SoundMaker() {
    }

    /**
     * Lejátsza a megodd nevű hangfájlt
     */
    public void play(String name) {
        try {
            Player p = new Player(new File("sounds/" + name + ".wav"));
            p.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}