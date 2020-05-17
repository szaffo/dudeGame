package controller;

import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.KeyEvent;

/**
 * Ez az osztály felelős a megadott billentyűnyomáskat figyelni.
 * A billentyűk lenyomásakor és felengedésekor jelet küld a Controller-nek
 * @author Szabó Martin
 */
public class KeyHandler implements KeyListener {

    private DudeGame parent;
    private Set<Integer> holdedButtons;

    public KeyHandler(DudeGame parent) {
        this.parent = parent;
        holdedButtons = new HashSet<Integer>();
    }   

    @Override
    public void keyPressed(KeyEvent e) {
        if (holdedButtons.contains(e.getKeyCode())) return;
        else holdedButtons.add(e.getKeyCode());
        
        int pressedButton = e.getKeyCode();
        switch (pressedButton){
            case 97: // ASCII for a
            case 65: // ASCII for A
            case 37: // ASCII for left arrow
                parent.playerMoveStart(Direction.LEFT);
                break;
            case 68: // ASCII for D
            case 100: // ASCII for d
            case 39:// ASCII for right arrow
                parent.playerMoveStart(Direction.RIGHT);
                break;

            case 38:
            case 87:
            case 119:
                parent.getData().playerSearchStart();;
                break;

            case 32:
                parent.playerJump();
                break;

            case 72:
            // case 104:
                parent.toggleDebugMode();
                break;

            case 10:
                parent.restart();
                break;
            
            default:
                //  System.out.println("KEYDOWN: " + e.getKeyCode());
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        holdedButtons.remove(e.getKeyCode());
        
        int pressedButton = e.getKeyCode();
        switch (pressedButton) {
            case 97: // ASCII for a
            case 65: // ASCII for A
            case 37: // ASCII for left arrow
                parent.playerMoveEnd(Direction.LEFT);;
                break;
           
            case 68: // ASCII for D
            case 100: // ASCII for d
            case 39:// ASCII for right arrow
                parent.playerMoveEnd(Direction.RIGHT);
                break;

            case 38:
            case 87:
            case 119:
                parent.getData().playerSearchEnd();;
                break;

            default:
                // System.out.println("KEYUP: " + e.getKeyChar());
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // System.out.println("pressed");
    }
}