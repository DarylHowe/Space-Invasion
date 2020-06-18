/**
 * Entity - An abstract class to set the blueprint for all game entities. 
 */
package Entities;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {

	protected float xPosition, yPosition;
	protected int width, height;

	/**
	 * A constructor for Entity.
	 * @param x      a float to store the entities x Position.
	 * @param y      a float to store the entities y Position.
	 * @param width  an int to store the entities width.
	 * @param height an int to store the entities height.
	 */
	public Entity(float xPosition, float yPosition, int width, int height) {

		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
	}

	/**
	 * A method to update an entities variables when called.
	 */
	public abstract void tick();

	/**
	 * A method to draw an entity to the canvas when called. 
	 * @param g	The graphics object.
	 */
	public abstract void render(Graphics g);
	
	/**
	 * A method to return the bounds of an entity. This can be used in collision detection. 
	 * @return	A rectangle object representing the bounds of the entity. 
	 */
	public Rectangle getEntityBounds() {
		return new Rectangle((int) xPosition, (int) yPosition, width, height);
	}

	// Getters

	public float getXPosition() {
		return xPosition;
	}

	public float getYPosition() {
		return yPosition;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
