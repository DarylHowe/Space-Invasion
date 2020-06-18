/**
 * ButtonObject - A class which creates the 'Space Invasion' title display on the menu screen. 
 */
package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MenuTitle extends UIObject {
	
	private Font font = new Font("Serif", Font.BOLD, 38);


	public MenuTitle(int xPosition, int yPosition, int width, int height) {
		super(xPosition, yPosition, width, height);
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.GREEN);
		g.fillRect(xPosition, yPosition, width, height);
		
		g.setColor(Color.BLACK);
		g.fillRect(xPosition+5, yPosition+5, width-10, height-10);
		
		g.setColor(Color.YELLOW);
		g.setFont(font);
		g.drawString("SPACE INVASION", xPosition + 27, yPosition + 63);
	}
}
