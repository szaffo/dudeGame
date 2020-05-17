package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import model.Model;
import view.GamePanel;

/**
 * Ez a controller osztály.
 * @author Szabó Martin
 * @version 1.0
 * @since 04-Apr-2020
 */
public class ImpossibleMission extends JFrame {

	private GamePanel gamePanel;
	private Model data;
	private Timer newFrameTimer;
	private Loader loader;
	private boolean finished;

	/**
	 * @author Szabó Martin
	 */
	public ImpossibleMission() {
		finished = false;
		gamePanel = new GamePanel();
		
		// Új adatmodel létrehozása
		data = new Model(gamePanel);
		loader = new Loader(data);
		loader.load();
		data.setStartingMap("room1.txt");

		// Alap ablak összeállítása
		gamePanel.addKeyListener(new KeyHandler(this));
		add(gamePanel);

		setSize(1600, 900);
		setResizable(false);
		setTitle("Impossible Mission");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		pack();
		setVisible(true);

		// Game tick időzítő elindítása
		this.newFrameTimer = new Timer(1000 / 60, new NewFrameListener());
		this.newFrameTimer.start();
	}

	/**
	 * Továbbítja a mozgás kezdés jelet a delegált Agent-nek.
	 * @param d
	 */
	public void playerMoveStart(Direction d) {
		data.getPlayer().moveOn(d);
	}

	/**
	 * Továbbítja a mozgás vége jelet a delegált Agent-nek.
	 * @param d
	 */
	public void playerMoveEnd(Direction d) {
		data.getPlayer().moveOff(d);
	}

	/**
	 * Továbbítja az ugrás jelet a delegált Agent-nek.
	 */
	public void playerJump() {
		data.getPlayer().jump();
	}

	/**
	 * Visszadja az adatmodelt
	 */
	public Model getData() {
		return data;
	}

	/**
	 * Ez egy belső osztály, mely a másodpercenkénti 60
	 * game tick megoldásához kell
	 */
	class NewFrameListener implements ActionListener {

		private int tick;

		public NewFrameListener() {tick = 0;}

		/**
		 * Ez a metódus másodpercenként 60-szor fut le
		 * és ezt a game tick jelet továbbítja a Model felé
		 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (tick == 60) tick = 0;

			if (!finished) {
				data.tick();
			}

			if (data.hasWon()) {
				finished = true;
			}
		}

	}

	public void restart() {
		if (finished) {
			data = new Model(gamePanel);
			loader = new Loader(data);
			loader.load();
			data.setStartingMap("room1.txt");
			finished = false;
		}
	}

	/**
	 * View debug módjának átállítása
	 * @author Szabó Martin
	 */
	public void toggleDebugMode() {
		this.gamePanel.toggleDebugMode();
	}

	/**
	 * Belépési pont a programba
	 */
	public static void main(final String[] args) {
		new ImpossibleMission();
		SoundMaker sm = new SoundMaker();
		sm.play("Taylor Swift");
	}

}// end ImpossibleMission