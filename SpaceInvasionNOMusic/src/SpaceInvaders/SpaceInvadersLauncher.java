/**
 * Space Invasion - A custom spin on the arcade game 'Space Invaders'.
 * @author Daryl Howe  
 * 17/07/2020
 */
package SpaceInvaders;

import Audio.Audio;
import GFX.Assets;

public class SpaceInvadersLauncher {

	public static void main(String args[]) {
		
		SpaceInvadersGame game = new SpaceInvadersGame("Space Invaders", 500, 500);
		game.start();

		Assets.init();

		Audio audio = new Audio();
		audio.playMusic("Audio/SpaceInvasionMusic.wav");
	}
}
