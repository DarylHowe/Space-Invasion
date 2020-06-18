package Entities;

import java.awt.Color;
import java.awt.Graphics;

public class PowerUp extends Entity {
	
	private boolean isVisible = true;
	private Color color;

	public PowerUp(float xPosition, float yPosition, int width, int height, Color color) {
		super(xPosition, yPosition, width, height);
		this.color = color;
	}
	
	@Override
	public void tick() {
		yPosition++;
	}

	@Override
	public void render(Graphics g) {

		if (isVisible) {
			g.setColor(color);
			g.fillRect((int) xPosition, (int) yPosition, width, height);
		}
	}

	public void setIsVisible(boolean bool) {
		this.isVisible = bool;
	}

}
