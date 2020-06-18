/**
 * GameManager - A class to control the game's state and keep track of the high-score. 
 * 	
 */
package GameManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Audio.Audio;
import SpaceInvaders.SpaceInvadersGame;
import States.State;

//NOTE: Implements MosueListener
public class GameManager extends MouseAdapter implements MouseListener {

	private int mouseX, mouseY;
	private int lastScore = 0;
	private int highScore = 0;
	
	private static State currentState = null;
	private SpaceInvadersGame spaceInvadersGame;
	
	/**
	 * A constructor for GameManager.
	 * @param SpaceInvadersGame - the main game loop instance.
	 */
	public GameManager(SpaceInvadersGame spaceInvadersGame) {
		this.spaceInvadersGame = spaceInvadersGame;
	}

	/**
	 * A method which is called when the user clicks on the screen/canvas.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		// Only if the menuState is in play..
		if (currentState != spaceInvadersGame.getGameState()) {

			mouseX = e.getX();
			mouseY = e.getY();
			
			// If the user clicks on the 'Play' button area..
			if (mouseX < 360 && mouseX > 150 && mouseY > 275 && mouseY < 305) {
				spaceInvadersGame.setToGameState();
			}

			// If the user clicks on the 'Audio' area..
			if (mouseX < 105 && mouseX > 10 && mouseY < 495 && mouseY > 475) {
				Audio.audioOnOff();
			}
		}
	}

	// Setters Getters

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public static void setState(State state) {
		currentState = state;
	}

	public static State getState() {
		return currentState;
	}
	
	public void setHighScore(int score) {
		if(this.highScore < score) {
			this.highScore = score;			
		}
	}
	
	public void setLastScore(int lastScore) {
		this.lastScore = lastScore;
	}
	
	public int getHighScore() {
		return highScore;
	}
	
	public int getLastScore() {
		return lastScore;
	}
	

	// Unused
	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
