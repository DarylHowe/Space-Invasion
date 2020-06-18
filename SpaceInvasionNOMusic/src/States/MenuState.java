/**
 * MenuState - A state for the menu screen. 
 */

package States;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Entities.MenuPlayerAI;
import GFX.Assets;
import SpaceInvaders.SpaceInvadersGame;
import UI.MenuTitle;
import UI.UIButton;

public class MenuState extends State {

	private MenuTitle playButton;
	public UIButton audioButton;
	private MenuPlayerAI playerAI;

	private Font font = new Font("Serif", Font.BOLD, 38);

	/**
	 * A constructor for the menu state.
	 * 
	 * @param game - the main game loop instance.
	 * @param width
	 * @param height
	 */
	public MenuState(SpaceInvadersGame game, int width, int height) {
		super(game, width, height);
		playButton = new MenuTitle(70, 100, 375, 100);
		playerAI = new MenuPlayerAI(game, 300, 400, 50, 20);
		audioButton = new UIButton(10, 490, 50, 20);

	}
	
	public UIButton getAudioButton() {
		return audioButton;
	}


	@Override
	public void tick() {
		playButton.tick();
		playerAI.tick();
		audioButton.tick();
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		g.drawImage(Assets.spaceBackgroundImage, 0, 0, 500, 500, null);

		playerAI.render(g);

		playButton.render(g);

		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("PLAY NOW", 150, 300);
		
		g.setColor(Color.GREEN);
		g.drawString("High Score: " + Integer.toString(game.getGameManager().getHighScore()), 143, 350);
		
		audioButton.render(g);
	}
}