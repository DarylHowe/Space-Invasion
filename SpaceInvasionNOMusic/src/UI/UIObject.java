/**
 * UI Object - A class to create a UI object. 
 */
package UI;

import java.awt.Graphics;

public abstract class UIObject {

	protected int xPosition;
	protected int yPosition;
	protected int width;
	protected int height;

	/**
	 * A constructor to create a UI object which sets the objects position and size. 
	 * @param xPosition	An int to set the UI's xPosition / xLocation on screen.
	 * @param yPosition An int to set the UI's yPosition / yLocation on screen.
	 * @param width An int to set the UI's width.
	 * @param height An int to set the UI's height.
	 */
	public UIObject(int xPosition, int yPosition, int width, int height) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;		
	}
		
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
}
