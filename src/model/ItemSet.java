package model;

import java.util.ArrayList;
import physics.Position;

/**
 * @author Szabó Martin
 */
public class ItemSet extends ArrayList<Item> {

    private Position agentStartingPostion;


    public void setSpawnPoint(Position pos) {
        this.agentStartingPostion = pos;
    }

    public Position getSpawnPoint() {
        return this.agentStartingPostion.deepCopy();
    }
    
}