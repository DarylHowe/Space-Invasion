/**
 * Bullet - A class for a bullet. 
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;

// NOTE entends Entity. 
public class Bullet extends Entity {

	private String color = "Red";
	private float speed = 2;

	public Bullet(float xPosition, float yPosition, int width, int height, String color) {
		super(xPosition, yPosition, width, height);
		this.color = color;
	}
	
	public Bullet(float xPosition, float yPosition, int width, int height, String color, float speed) {
		super(xPosition, yPosition, width, height);
		this.color = color;
		this.speed = speed;
	}

	@Override
	public void tick() {
		yPosition -= speed;
	}

	@Override
	public void render(Graphics g) {

		switch (color) {
		
		case "Red":
			g.setColor(Color.RED);
			break;
			
		case "Blue":
			g.setColor(Color.BLUE);
			break;
			
		case "Yellow":
			g.setColor(Color.YELLOW);
			break;
			
		case "Pink":
			g.setColor(Color.PINK);
			break;
			
		case "White":
			g.setColor(Color.WHITE);
			break;
			
		default:
			g.setColor(Color.RED);
		}

		g.fillRect((int) xPosition, (int) yPosition, width, height);
	}

	// Setters. 
	
	public void setColor(String color) {
		this.color = color;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
