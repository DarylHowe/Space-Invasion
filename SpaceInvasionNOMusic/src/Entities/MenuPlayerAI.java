/**
 * MenuPlayerAI - A class for the menu player AI. The AI shoots and moves automaticially. 
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;

import SpaceInvaders.SpaceInvadersGame;

// NOTE extends Player.
public class MenuPlayerAI extends Player {

	// Will move right on startup by defualt. 
	private boolean moveRight = true;
	private boolean moveLeft = false;

	/**
	 * @param spaceInvadersGame - the main game loop instance.
	 * @param xPosition         A float to set the players initial xPosition on screen.
	 * @param yPosition         A float to set the players initial yPosition on screen.
	 * @param width             An int to set the players width.
	 * @param height            An int to set the players height.
	 */
	public MenuPlayerAI(SpaceInvadersGame spaceInvadersGame, float xPosition, float yPosition, int width, int height) {
		super(spaceInvadersGame, xPosition, yPosition, width, height);
	}

	@Override
	public void movement() {
		
		if (moveRight) {
			xPosition++;

			if (xPosition % 40 == 0) {
				super.fire();
			}
		}

		if (moveLeft) {
			xPosition--;

			if (xPosition % 70 == 0) {
				super.fire();
			}
		}

		// Change direction if.. 
		if (xPosition >= 400) {
			moveRight = false;
			moveLeft = true;
		}

		if (xPosition <= 100) {
			moveRight = true;
			moveLeft = false;
		}
	
		if (xPosition % 100 == 0) {
			super.fire();
		}
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.RED);
		g.drawRect((int) xPosition, (int) yPosition, width, height);

		g.setColor(Color.RED);
		g.fillRect((int) xPosition + 1, (int) yPosition + 1, width / 3, height - 2);

		g.setColor(Color.RED);
		g.fillRect((int) xPosition + width / 3, (int) yPosition + 1, width / 3, height - 2);

		g.setColor(Color.RED);
		g.fillRect((int) (xPosition + width) - (width / 3) - 2, (int) yPosition + 1, (width / 3) + 1, height - 2);

		for (int i = 0; i < bulletArrayList.size(); i++) {
			bulletArrayList.get(i).render(g);
		}
	}
}
