package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EnumMap;

/**
 * The main class of the game. Responsible for initializing, starting, and exiting the game.
 */
public class Tetris {
	static int framesPerSecond = 60;
	static int fallsPerSecond = 2;
	static boolean gameRunning = true;
	private static SwingGraphics swingGraphics;
	static EnumMap<ShapeEnum, ShapeData> shapeDatas;
	public static GameField gameField;

	/**
	 * The main method of the game.
	 * @param args The arguments of the program. Unused.
	 */
	public static void main(String[] args) {
		initGame();
		JFrame window = initSwing();
		startGame();
		//The startGame method returns when the game is over
		window.dispose();
	}

	/**
	 * Initializes the game, should be called right after the game is launched.
	 * Reads the ShapeDatas from file and creates a new GameField.
	 */
	public static void initGame() {
		//We load the shapes from file
		shapeDatas = new EnumMap<ShapeEnum, ShapeData>(ShapeEnum.class);
		for (int i = 0; i < ShapeEnum.values().length; i++) {
			shapeDatas.put(ShapeEnum.values()[i], new ShapeData(ShapeEnum.values()[i])); //gesundheit
		}

		gameField = new GameField();
	}

	/**
	 * Should be called after everything was initialized. Contains the main game loop.
	 */
	private static void startGame() {
		long currentTime = System.currentTimeMillis();
		int frameDelay = 1000 / framesPerSecond;

		long lastRender = currentTime;
		long lastUpdate = currentTime;

		gameField.spawnNewShape();

		//Game loop
		while (gameRunning) {
			int tickDelay = 1000 / fallsPerSecond; 	//This is calculated inside the loop because by pressing the S button, we change the falling speed
													// and thus the tickDelay needs to be recalculated
			currentTime = System.currentTimeMillis();

			if (frameDelay < (currentTime - lastRender)) {
				lastRender = currentTime;
				renderGame();
			}

			if (tickDelay < (currentTime - lastUpdate)) {
				lastUpdate = currentTime;
				updateGame();
			}
		}
	}

	/**
	 * Updates the state of the game.
	 * Makes the active block fall one block.
	 */
	public static void updateGame() {

		gameField.updateGameField();
	}

	/**
	 * Tells the renderer to re-render the current state of the game.
	 */
	private static void renderGame() {
		swingGraphics.repaint();
	}

	/**
	 * Creates a window, initializes the renderer, and handles keypresses.
	 * @return The JFrame so that we can destroy it at the end of the game.
	 */
	private static JFrame initSwing() {
		//Creating the JFrame
		JFrame window  = new JFrame("Tetris");
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setResizable(false);

		//Creating the JMenu
		JMenuBar jMenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		jMenuBar.getAccessibleContext().setAccessibleDescription("File menu");
		jMenuBar.add(fileMenu);
		JMenuItem fileMenuExit = new JMenuItem("Exit");
		fileMenuExit.getAccessibleContext().setAccessibleDescription("Exits the game");
		fileMenu.add(fileMenuExit);
		fileMenuExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameRunning = false;
			}
		});

		//Initializing the renderer
		swingGraphics = new SwingGraphics();
		Dimension JPanelSize = new Dimension(swingGraphics.blockRenderSize * gameField.width, swingGraphics.blockRenderSize * gameField.height);
		swingGraphics.setPreferredSize(JPanelSize);
		window.add(jMenuBar, BorderLayout.NORTH);
		window.add(swingGraphics, BorderLayout.CENTER);
		window.pack();
		window.setVisible(true);

		//Handling keypresses
		window.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_D:
						gameField.moveActiveShape(Direction.RIGHT);
						break;
					case KeyEvent.VK_A:
						gameField.moveActiveShape(Direction.LEFT);
						break;
					case KeyEvent.VK_E:
						gameField.rotateActiveShape(Direction.RIGHT);
						break;
					case KeyEvent.VK_Q:
						gameField.rotateActiveShape(Direction.LEFT);
						break;
					case KeyEvent.VK_S:
						fallsPerSecond = 50;
						break;
				}
			}
		});

		return window;
	}
}