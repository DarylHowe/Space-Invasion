package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Audio.Audio;

public class UIButton extends UIObject {

	private Font font = new Font("Serif", Font.BOLD, 18);

	public UIButton(int xPosition, int yPosition, int width, int height) {
		super(xPosition, yPosition, width, height);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.setFont(font);

		if (Audio.getAudioIsActive()) {
			g.setColor(Color.YELLOW);
			g.drawString("SFX: ON", xPosition, yPosition);
		} else {
			g.setColor(Color.RED);
			g.drawString("SFX: OFF", xPosition, yPosition);
		}
	}

}
