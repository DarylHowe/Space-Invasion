/**
 * State - an abstract class. A state can be thought of as different screens eg menu, settings, game etc. 
 * Each state all have a certain number of behaviors in common. 
 * For example, they all need a 'tick()' so they can update their variables. 
 * They all need a 'render()' so they update the screen graphics .ie draw to canvas
 */
package States;

import java.awt.Graphics;

import SpaceInvaders.SpaceInvadersGame;

public abstract class State {

	protected SpaceInvadersGame game;
	protected int width, height;

	/**
	 * A constructor for State.
	 * @param game
	 */	public State(SpaceInvadersGame game, int width, int height) {
		this.game = game;
		this.width = width;
		this.height = height;
	}

	/**
	 * A method to update the game data/variables. 
	 */
	public abstract void tick();

	/**
	 * A method to draw to the canvas. 
	 * @param g	Graphics object. 
	 */
	public abstract void render(Graphics g);

}

