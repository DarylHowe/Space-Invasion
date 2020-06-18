/**
 * SpaceInvadersGame - A class which holds the main game loop/clock and thread. This is the body of the 'game engine'.  
 */
package SpaceInvaders;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import Display.Display;
import GameManager.GameManager;
import Input.KeyManager;
import States.GameState;
import States.MenuState;
import States.State;

//NOTE: Implements Runnable
public class SpaceInvadersGame implements Runnable {

	private int width, height;
	private boolean running = false;

	private Display display;

	private Thread thread;

	private Graphics g;
	private BufferStrategy bs;

	private GameManager gameManager;
	private State gameState, menuState;
	private KeyManager keyManager;

	/**
	 * A constructor for SpaceInvadersGame.
	 * 
	 * @param title  A string to be displayed as the window's name/title.
	 * @param width  An int setting the width of the window.
	 * @param height An int setting the height of the window.
	 */
	public SpaceInvadersGame(String title, int width, int height) {
		display = new Display(title, width, height);

		this.width = width;
		this.height = height;

		gameManager = new GameManager(this);
		keyManager = new KeyManager();
	}

	/**
	 * A method to start the game thread.
	 */
	public synchronized void start() {
		if (running) {
			return;
		} else {

			running = true;

			// Create a new 'Game' thread object and run this class (SpaceInvadersGame) in
			// it.
			thread = new Thread(this);

			// Start the thread - note this will invoke the 'run()' method.
			thread.start();
		}
	}

	/**
	 * A method to stop the game thread.
	 */
	public synchronized void stop() {

		if (running == false) {
			return;
		} else {

			running = false;

			// How to stop a thread..
			try {
				// Stop the thread.
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * A method to act as the game clock. This method calls 'tick()' and 'render()'
	 * every frame.
	 *
	 */
	@Override
	public void run() {

		init();

		int fps = 60;

		// TimePerTick measured in nano seconds (more specific).

		double timePerTick = 1_000_000_000 / fps;
		double delta = 0;
		long now;

		// A clock in nano time.
		long lastTime = System.nanoTime();

		// Continually invoke 'tick()' and 'render()'.
		while (running == true) {

			// Another clock in nano time.
			now = System.nanoTime();

			// (now - lastTime) will give the amount of time passed since this code was last
			// call.
			delta += (now - lastTime) / timePerTick;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();

				// Reset delta.
				delta = delta - 1;
			}
		}
		// When loop is over.
		stop();
	}

	/**
	 * A method that is called once only at start-up.
	 */
	public void init() {

		// Get the canvas from the display window and add the 'gameManager' mouse
		// listener.
		// This makes the canvas listen for mouse clicks.
		display.getCanvas().addMouseListener(gameManager);

		// Get the JFrame and add a 'keyManager' Key Listener to it (allows access to
		// the keyboard).
		// Legal to pass in 'keyManager' as 'Key Manager' class implements
		// 'KeyListener'.
		display.getJFrame().addKeyListener(keyManager);

		// Create a GameState and MenuState and pass in 'this' instance of the 'Game'
		// class.
		gameState = new GameState(this, width, height);
		menuState = new MenuState(this, width, height);

		// Set the current state. Therefore 'menuState' will be loaded first at startup.
		GameManager.setState(menuState);
	}

	/**
	 * A method which is called on every frame update. This allows game variables to
	 * be updated every 'tick()'. Runs the 'tick()' of whichever game state is
	 * currently active. Runs the 'tick()' of the 'keyManager' class.
	 */
	public void tick() {

		// Once the game state is not empty IE once there is a state that exists..
		if (GameManager.getState() != null) {

			// Get the current game state and run its 'tick()'.
			GameManager.getState().tick();
		}

		// Update the 'keyManager.tick()' every tick.
		keyManager.tick();
	}

	/**
	 * A method which is called on every frame update that draws/paints to the
	 * screen/canvas. Calls the 'render()' of whichever game state is currently
	 * active each frame.
	 */
	private void render() {

		bs = display.getCanvas().getBufferStrategy();

		// If the canvas doesn't have a buffer strategy, create one and set number of
		// buffers..
		if (bs == null) {

			// Note: 3 buffers set.
			display.getCanvas().createBufferStrategy(3);
			return;
		}

		// g IE 'Graphics' allows content to be drawn to the canvas.
		// Creates the 'paint brush'.
		g = bs.getDrawGraphics();

		// Clear the entire screen.
		g.clearRect(0, 0, width, height);

		// Once the game state is not empty..
		if (GameManager.getState() != null) {

			// Get the current game state and run its 'render()' while passing in the
			// Graphics 'g' object.
			GameManager.getState().render(g);
		}

		// Stop drawing. These two lines tell Java we are finished drawing.
		bs.show();
		g.dispose();
	}

	// Setters Getters

	public void setToGameState() {
		GameManager.setState(gameState);
	}

	public void setToMenuState() {
		GameManager.setState(menuState);
	}
	
	public State getGameState() {
		return gameState;
	}
	
	public State getMenuState() {
		return menuState;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public GameManager getGameManager() {
		return gameManager;
	}
}
