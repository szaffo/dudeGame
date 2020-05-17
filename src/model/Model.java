package model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Ez egy összefogó osztály, mely item-eket tárol egy listában. Valamint az
 * Agent-t
 * 
 * @author Szabó Martin
 * @since 2020-04-08
 */
public class Model extends Observable {

    private Agent player;

    private int tickCounter = 0;
    private HashMap<String, ItemSet> itemSets;
    private String currentActiveItemSet;
    private String startMapName;

    private int collisionCounter = 0;

    public Model(Observer observer) {
        Furniture.reset();
        addObserver(observer);
        player = new Agent(this, 0, 0);
        itemSets = new HashMap<String, ItemSet>();
        currentActiveItemSet = null;
        startMapName = null;
    }

    public boolean hasWon() {
        return this.player.lootCounter >= Furniture.availableLoots;
    }


    /**
     * Beállítja a játékost
     * @author Szabó Martin
     */
    public void setPlayer(Agent p) {
        this.player = p;
    }

    /**
     * @author Leczó Gábor Bálint
     */
    public Agent getPlayer() {
        return this.player;
    }

    /**
     * Megnezi mikor kell ujrarajzolni a kepet es kezeli az Agent "halalat"
     * @author Szabó Martin
     * @author Simonyi Patrik
     */
    public void tick() {
        if (currentActiveItemSet == null) return;
        
        if (hasWon()) {
            return;
        }
        
        for (Item item : itemSets.get(currentActiveItemSet)) {
            item.tick();
        }
        player.tick();
         
        if (isPlayerCollidesWithEnemy())  {
            resetPlayerPosition();
        }
        
        setChanged();
        notifyObservers();

    }

    /**
     * Megnezi hogy az Agent utkozik-e egy Enemy interface-t implementáló Item-mel
     * @author Simonyi Patrik
     */
    public boolean isPlayerCollidesWithEnemy() {
        if (currentActiveItemSet == null) return false;

        for (Item item : itemSets.get(currentActiveItemSet)) {
            if (item instanceof Enemy) {

                if (item instanceof Robot) {   
                    Robot r = (Robot) item;
                    
                    if  (r.collidesWith(player)) {
                        return true;
                    }
                } else {
                    if (item.collidesWith(player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Továbbítja az ügynök felé, hogy keressen a bútorban
     * és átadja neki a bútort
     * @author Szabó Martin
     */
    public void playerSearchStart() {
        if (currentActiveItemSet == null) return;

        for (Item item : itemSets.get(currentActiveItemSet)) {
            if ((item instanceof Furniture) && (item.collidesWith(player))) {
                player.startLooting((Furniture) item);
                break;
            }
        }
    }

    /**
     * Jelzés az Agent-nek, hogy fejezze be a keresést
     */
    public void playerSearchEnd() {
        player.stopLooting();
    }

    /**
     * Hozzáad a model-hez egy újabb Item csomagot
     * @author Szabó Martin
     */
    public void addItemSet(String id, ItemSet theSet) {
        this.itemSets.put(id, theSet);
    }

    /**
     * Beállítja, hogy a model milyen Item csomaggal dolgozzon.
     * @author Szabó Martin
     */
    public void setCurrentActiveItemSet(String id) {
        if (this.itemSets.containsKey(id)) {
            this.currentActiveItemSet = id;
            this.player.resetPosition();
        }
    }

    /**
     * Visszadja az éppen aktuális Item-ek listáját
     * @author Szabó Martin
     */
    public ItemSet getItems() {
        if (currentActiveItemSet == null)
            return null;

        return this.itemSets.get(currentActiveItemSet);
    }

    /**
     * Beállítja a kezdő pályát, ha még nem volt megadva
     * @author Szabó Martin
     */
    public void setStartingMap(String startMap) {
        if (startMapName == null) {
            startMapName = startMap;
            setCurrentActiveItemSet(startMap);
        }
    }

    public String getStartingMapName() {
        return startMapName;
    }

    public void resetPlayerPosition() {
        setCurrentActiveItemSet(getStartingMapName());

    }
}