package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.util.Observable;
import java.util.Observer;
import java.awt.Color;

import javax.swing.ImageIcon;

import javax.swing.JPanel;

import model.Model;
import model.Robot;
import model.FallingItem;
import model.Furniture;
import model.Item;

/**
 * Ez az osztály felelős a Model-ben tárolt adatok megjelenítéséért
 * 
 * @author Us and our previous projects
 */
public class GamePanel extends JPanel implements Observer {

    private Image background;
    private Image victoryScreen;
    private Model data;
    private boolean debugMode;

    public GamePanel() {
        super();
        this.debugMode = false;
        setPreferredSize(new Dimension(1600, 900));
        setFocusable(true);
        requestFocusInWindow();
        victoryScreen = new ImageIcon("images/victory_screen2.png").getImage();
        background = new ImageIcon("images/back.jpg").getImage();
        this.setVisible(true);
    }

    /**
     * Kirajzolja a Model-től kapott kép listát
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (data == null) return;
        
        g.drawImage(background, 0, 0, null);

        for (Item item : data.getItems()) {
            if ((item instanceof Furniture) && (((Furniture) item).isLootedBefore()))
                continue;

            this.drawItem(g, item);
            if (this.debugMode) {
                this.drawItemBoundaries(g, item);
            }
        }

        drawItem(g, data.getPlayer());
        if (this.debugMode)
            this.drawItemBoundaries(g, data.getPlayer());

        if (data.getPlayer().isLooting()) {
            drawLootBar(g);
        }

        // Neptun pipák kirajzolása
        for (int i = 0; i < data.getPlayer().getLootCount(); i++) {
            g.drawImage(new ImageIcon("images/ok.png").getImage(), i * 30 + 30, 20, null);
        }

        if (data.hasWon()) {
            g.drawImage(victoryScreen, 0, 0, null);  
        }

    }

    /**
     * Kirajzolja egy Item határait
     * 
     * @author Szabó Martin
     */
    private void drawItemBoundaries(Graphics g, Item item) {
        Rectangle rect = item.getBoundaries();
        g.setColor(Color.PINK);
        g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());

        if (item instanceof FallingItem) {
            FallingItem fitem = (FallingItem) item;
            g.setColor(Color.YELLOW);
            rect = fitem.getLeftBoundaries();
            g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());

            rect = fitem.getRightBoundaries();
            g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());

            g.setColor(Color.BLUE);
            rect = fitem.getMiddleBoundaries();
            g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        }

        if (item instanceof Robot) {
            Robot ritem = (Robot) item;
            if (ritem.isAttacking()) {
                g.setColor(Color.GREEN);
                rect = ritem.getAttackingBoundaries();
                g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    /**
     * Kirajzol egy Item-et
     * 
     * @author Szabó Martin
     */
    private void drawItem(Graphics g, Item item) {
        g.drawImage(new ImageIcon("images/" + item.getImageName()).getImage(), (int) item.getPosX(),
                (int) item.getPosY(), null);
        if (item instanceof Robot) {
            Robot ritem = (Robot) item;
            if (ritem.isAttacking()) {
                g.drawImage(new ImageIcon("images/" + ritem.getLaserImageName()).getImage(), ritem.getLaserPosX(),
                        ritem.getLaserPosY(), null);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        this.paintComponent(g);
    }

    /**
     * Ez a metódus fut le minden alkalommal mikor a megfigyelt objektum
     * megváltozik, és erről értesítést küld
     * 
     * @param observable A megfigylet objektum, a Model
     * @param arg        Az értesítéshez csatolt objektum
     */
    @Override
    public void update(Observable observable, Object arg) {
        this.data = (Model) observable;

        // System.out.println("[VIEW] Redrawing panel " +
        // Integer.toString(drawCount++));

        repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Debug mód ki-be kapcsolás
     * 
     * @author Szabó Martin
     */
    public void toggleDebugMode() {
        this.debugMode = !this.debugMode;
        repaint();
    }

    /**
     * Kirajzolja a keresés folyamatát
     * 
     * @author Simonyi Patrik
     */
    private void drawLootBar(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(615, 795, data.getPlayer().getLootProgress() * 2 + 10, 60);
        g.setColor(Color.BLACK);
        g.fillRect(620, 800, data.getPlayer().getLootProgress() * 2, 50);
    }

}