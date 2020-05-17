package model;

import physics.Size;

/**
 * Ez az osztály felelteti meg azt a játékbeli elemet ahol, a puzzle-ket lehet
 * találni.
 * 
 * @author Leczó Gábor Bálint
 * @since 2020-04-26
 */
public class Furniture extends FallingItem {

    private boolean hasLoot;
    private boolean isLooted;
    private int searchProgress;
    private int totalProgress;

    static int availableLoots = 0;

    public Furniture(Model parent, double x, double y, boolean hasLoot, String imageName) {
        super(parent, x, y, 0, 0);
        switch (imageName) {
        case "pc.gif":
            this.size = new Size(120, 75);
            break;

        case "electric.png":
            this.size = new Size(80, 150);
            break;
        }
        this.imageName = imageName;
        this.hasLoot = hasLoot;
        this.isLooted = false;
        this.searchProgress = 0;
        this.totalProgress = 180;

        if (hasLoot)
            Furniture.availableLoots++;
    }

    public boolean isLootedBefore() {
        return this.isLooted;
    }

    public int getSearchProgress() {
        return this.searchProgress;
    }

    public boolean loot() {
        if (isLooted)
            return false;
        searchProgress++;
        // System.out.println("[Furniture] Progress: " + this.searchProgress + "/" +
        // this.totalProgress);
        if (searchProgress == totalProgress) {
            this.isLooted = true;
            if (hasLoot) {
                makeSound("loot");
            }
            return hasLoot;
        } else {
            return false;
        }
    }

    static void reset() {
        Furniture.availableLoots = 0;
    }
}