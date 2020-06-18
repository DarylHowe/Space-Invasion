/**
 * Enemy - A class for the enemy. 
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;

// NOTE entends Entity. 
public class Enemy extends Entity {

	private boolean isDestroyed = false;
	private float speed = 1f;
	private float xSpeed = 1f;
	private String color = "Green";

	public static int totalEnemy = 0;

	public Enemy(float xPosition, float yPosition, int width, int height) {
		super(xPosition, yPosition, width, height);

		totalEnemy++;
	}

	@Override
	public void tick() {

		if (isDestroyed) {

		} else {

			yPosition += speed;

			if (xPosition <= 0) {

				xPosition += xSpeed;

			} else if (xPosition >= 500 - width) {
				xPosition -= xSpeed;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		
		if (isDestroyed) {

		} else {
			if (color.equals("Green")) {
				g.setColor(Color.GREEN);
			} else if (color.equals("White")) {
				g.setColor(Color.WHITE);
			}
			g.fillRect((int) xPosition, (int) yPosition, width, height);
		}
	}
	
	// Setters Getters.

	public void setIsDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
