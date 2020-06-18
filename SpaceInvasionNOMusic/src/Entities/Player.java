/**
 * Player - A class for the game's player. 
 * The player moves left and right using the 'a' and 'd' keys respectively.
 * The player is capable of firing bullets using the space-bar.
 * The player becomes invincible for length of time after they loose a life to allow the a chance to recover.
 * The players current health status is represented on screen within the player entity itself and is denoted
 * by how red the player's 'body' is. If a life is lost the player will no longer be fully red in color. The player's
 * will flash when they have only a single life left.  
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Audio.Audio;
import SpaceInvaders.SpaceInvadersGame;

public class Player extends Entity {

	protected SpaceInvadersGame spaceInvadersGame;

	public ArrayList<Bullet> bulletArrayList;

	private int currentTickCount = 0;

	private int ticksToNextFire = 0;
	private int ticksBetweenFire = 10;

	private int health = 3;
	private int invincipleTimer = 0;
	private int invicibleLength = 100;

	private int healthFlashTimer = 0;
	private int healthFlashLength = 50;

	private int bulletWidth = 10;
	private int bulletHeight = 10;

	private float playerSpeed = 5f;

	private boolean multiBulletIsActive = false;
	private boolean speedBulletIsActive = false;
	private boolean quickFireBulletIsActive = false;
	private boolean isAbleToFire = false;

	private Audio audio;

	// Bullet will be instantiated from within the player
	private float bulletYPosition = yPosition - bulletHeight;

	/**
	 * @param spaceInvadersGame - the main game loop instance.
	 * @param xPosition         A float to set the players initial xPosition on screen.
	 * @param yPosition         A float to set the players initial yPosition on screen.
	 * @param width             An int to set the players width.
	 * @param height            An int to set the players height.
	 */
	public Player(SpaceInvadersGame spaceInvadersGame, float xPosition, float yPosition, int width, int height) {
		super(xPosition, yPosition, width, height);
		this.spaceInvadersGame = spaceInvadersGame;

		bulletArrayList = new ArrayList<Bullet>();
		audio = new Audio();
	}

	@Override
	public void tick() {

		movement();
		checkIsAbleToFire();

		// If the space-bar is pressed..
		if (spaceInvadersGame.getKeyManager().spaceBar) {
			fire();
		}

		for (int i = 0; i < bulletArrayList.size(); i++) {
			bulletArrayList.get(i).tick();
		}

		currentTickCount++;
	}

	/**
	 * A method to move the player left and right.
	 */
	public void movement() {

		// If the player wants to move left ie presses 'a' key..
		if (spaceInvadersGame.getKeyManager().left) {

			// If the player is on the left bound of the window do not move.
			if (xPosition <= 0) {

				// Move the player's xPosition left.
			} else {
				xPosition -= playerSpeed;
			}

		} else if (spaceInvadersGame.getKeyManager().right) {
			if (xPosition >= 500 - width) {

			} else {
				xPosition += playerSpeed;
			}
		}
	}

	/**
	 * A method to fire a bullet.
	 */
	public void fire() {

		// A player cannot continually fire, there is a delay between each bullet.
		if (isAbleToFire) {

			audio.playMusic("Audio/Fire.wav");

			if (multiBulletIsActive) {

				multiBulletFire();

			} else if (speedBulletIsActive) {

				speedBulletFire();

			} else if (quickFireBulletIsActive) {

				quickFireBulletFire();

			} else {

				// Single fire.
				// Adjust bullet position to be fired from the center of the player.
				bulletArrayList.add(new Bullet(xPosition + ((width / 2) - bulletWidth / 2), bulletYPosition,
						bulletWidth, bulletHeight, "Red"));
			}

			// Stop the player from being able to fire for 'ticksBetweemFire' amount.
			ticksToNextFire = currentTickCount + ticksBetweenFire;
		}
	}

	/**
	 * A method to check if the player is able to fire.
	 */
	private void checkIsAbleToFire() {

		if (ticksToNextFire <= currentTickCount) {
			isAbleToFire = true;
		} else {
			isAbleToFire = false;
		}
	}

	/**
	 * A method to fire blue color multiple-bullets at once when the multi-bullet
	 * power up has been activated.
	 */
	private void multiBulletFire() {

		bulletArrayList.add(new Bullet(xPosition + ((width / 2) - bulletWidth / 2) + 20, bulletYPosition, bulletWidth,
				bulletHeight, "Blue"));
		bulletArrayList.add(new Bullet(xPosition + ((width / 2) - bulletWidth / 2) - 10, bulletYPosition, bulletWidth,
				bulletHeight, "Blue"));
		bulletArrayList.add(new Bullet(xPosition + ((width / 2) - bulletWidth / 2) + 50, bulletYPosition, bulletWidth,
				bulletHeight, "Blue"));
	}

	/**
	 * A method to fire yellow color speed bullets. Speed bullets travel distance
	 * faster.
	 */
	private void speedBulletFire() {

		bulletArrayList.add(
				new Bullet(xPosition + ((width / 2) - bulletWidth / 2), bulletYPosition, bulletWidth, bulletHeight, "Yellow"));

		for (int i = 0; i < bulletArrayList.size(); i++) {
			bulletArrayList.get(i).setSpeed(5);
		}
	}

	/**
	 * A method to fire pink color quick-fire bullets. Quick fire bullets have a
	 * reduced waiting time between each fire.
	 */
	private void quickFireBulletFire() {

		bulletArrayList.add(
				new Bullet(xPosition + ((width / 2) - bulletWidth / 2), bulletYPosition, bulletWidth, bulletHeight, "Pink"));
	}

	/**
	 * A method to deduct a life from the player. Player becomes invincible for a
	 * set amount of time.
	 */
	public void lifeLost() {

		if (invincipleTimer <= currentTickCount) {
			System.out.println("Life Lost!");

			health--;
			invincipleTimer = currentTickCount + invicibleLength;
		}
	}

	@Override
	public void render(Graphics g) {

		// Draw main player block.
		g.setColor(Color.RED);
		g.drawRect((int) xPosition, (int) yPosition, width, height);

		if (health == 3) {

			g.setColor(Color.RED);
			g.fillRect((int) xPosition + 1, (int) yPosition + 1, width / 3, height - 2);

			g.setColor(Color.RED);
			g.fillRect((int) xPosition + width / 3, (int) yPosition + 1, width / 3, height - 2);

			g.setColor(Color.RED);
			g.fillRect((int) (xPosition + width) - (width / 3) - 2, (int) yPosition + 1, (width / 3) + 1, height - 2);

		} else if (health == 2) {

			g.setColor(Color.RED);
			g.fillRect((int) xPosition + 1, (int) yPosition + 1, width / 3, height - 2);

			g.setColor(Color.RED);
			g.fillRect((int) xPosition + width / 3, (int) yPosition + 1, width / 3, height - 2);

		} else if (health == 1) {

			// Flash health bar.
			if (healthFlashTimer <= healthFlashLength / 2) {

				g.setColor(Color.RED);
				g.fillRect((int) xPosition + 1, (int) yPosition + 1, width / 3, height - 2);
			} else {

				if (healthFlashTimer >= healthFlashLength)
					healthFlashTimer = 0;
			}

			healthFlashTimer++;
		}
	}

	/**
	 * A method to reset the player variables back to their default.
	 */
	public void resetPlayer() {
		health = 3;
		playerSpeed = 5f;
		multiBulletIsActive = false;
		speedBulletIsActive = false;
		quickFireBulletIsActive = false;
		invincipleTimer = 0;
		xPosition = 230;
	}

	// Setters Getters.

	public void setMultiBulletActive(boolean bool) {
		this.multiBulletIsActive = bool;
	}

	public boolean getMultiBulletActive() {
		return multiBulletIsActive;
	}

	public void setSpeedBullet(boolean bool) {
		this.speedBulletIsActive = bool;
	}

	public boolean getSpeedBulletActive() {
		return speedBulletIsActive;
	}

	public void setQuickFireBullet(boolean bool) {
		this.quickFireBulletIsActive = bool;
	}

	public boolean getQuickFireBulletActive() {
		return quickFireBulletIsActive;
	}

	public void setCountsBetweenFire(int counts) {
		this.ticksBetweenFire = counts;
	}

	public int getHealth() {
		return health;
	}

	public void setPlayerSpeed(float speed) {
		this.playerSpeed = speed;
	}
}
