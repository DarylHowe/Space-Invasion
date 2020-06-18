/**
 * GameState - A state for the main game screen. 
 * A power up cannot be spawned twice in a single wave. 
 */

package States;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import Audio.Audio;
import Entities.Bullet;
import Entities.Enemy;
import Entities.Player;
import Entities.PowerUp;
import GFX.Assets;
import SpaceInvaders.SpaceInvadersGame;

public class GameState extends State {

	private int totalEnemyDestoryedForCurrentWave = 0;
	private int score = 0;

	private int currentTickCount = 0;

	private int waveNumber = 0;
	private int firstEnemySpawn = 100;
	private int ticksBetweenEnemy = 50;
	private int ticksBetweenWaves = 350;

	private int powerUpTimer;
	private int powerUpLength = 400;
	
	private int enemyWidth = 60;
	private int enemyHeight = 20;

	private int windowWidth;

	private boolean gameIsActive = true;
	private boolean multiBulletIsPresent = false;
	private boolean speedBulletIsPresent = false;
	private boolean quickFireBulletIsPresent = false;
	private boolean isPausedBetweenWave = false;

	// An ArrayList to store all enemy.
	private ArrayList<Enemy> enemyList;
	
	// An ArrayList to store all the enemy bullets. 
	private ArrayList<Bullet> enemyBulletList;
	
	private SpaceInvadersGame spaceInvadersGame;

	private Player player;
	
	private PowerUp multiBullet;
	private PowerUp speedBullet;
	private PowerUp quickFireBullet;
		
	private Audio audio;

	private Font font = new Font("Serif", Font.BOLD, 18);

	private Random random;

	/**
	 * 
	 * @param SpaceInvadersGame - the main game loop instance.
	 * @param width		the width of the game window as an int.
	 * @param height	the width of the game window as an int.
	 */
	public GameState(SpaceInvadersGame spaceInvadersGame, int width, int height) {
		super(spaceInvadersGame, width, height);

		this.windowWidth = width;
		this.spaceInvadersGame = spaceInvadersGame;

		player = new Player(game, 230, 400, 50, 20);
		enemyList = new ArrayList<Enemy>();
		enemyBulletList = new ArrayList<Bullet>();
		random = new Random();		
		audio = new Audio();		
	}

	@Override
	public void tick() {

		player.tick();
		randomEnemySpawn();
		bulletCollision();

		tickPowerUpsIfPresent();
		
		// Tick each of the enemy's bullets.
		for (int i = 0; i < enemyBulletList.size(); i++) {
			enemyBulletList.get(i).tick();
		}

		// Tick each of the enemy.
		for (int i = 0; i < enemyList.size(); i++) {

			enemyList.get(i).tick();
			checkPlayerEnemyCollision(i);
			checkEnemyGoesPastPlayer(i);
			
			// Depending on the wave number enemy will have various settings. 
			// This will update the enemy with the appropriate setting. 
			setEnemyWaveSettings(i);
		}

		// Every 500 ticks..
		if (currentTickCount % 500 == 0) {
			
			int r = random.nextInt(3);
			if (r == 0) {
				randomPowerUpSpawn();
			}
		}

		currentTickCount++;		
	}

	/**
	 * A method to run the tick method of a power up if it has been instantiated.
	 */
	public void tickPowerUpsIfPresent() {

		if (multiBulletIsPresent) {
			multiBullet.tick();
		}

		if (speedBulletIsPresent) {
			speedBullet.tick();
		}

		if (quickFireBulletIsPresent) {
			quickFireBullet.tick();
		}
	}

	/**
	 * A method to set enemy settings for each wave.
	 * 
	 * @param i the enemy to set the settings on.
	 */
	private void setEnemyWaveSettings(int i) {

		switch (waveNumber) {
		case 0:
			break;

		case 1:
			if (!isPausedBetweenWave) {
				enemyList.get(i).setSpeed(2);
				enemyList.get(i).setColor("White");
			}
			break;

		default:
			enemyList.get(i).setSpeed(1);
			enemyList.get(i).setColor("Green");
			break;
		}
	}

	/**
	 * A method to spawn a random power up. A power up can only be spawned once per
	 * wave.
	 */
	public void randomPowerUpSpawn() {
		int powerUpWidth = 20;
		int powerUpHeight = 20;
		int yPosition = 20;

		int r = random.nextInt(3);
		int randomXPosition = random.nextInt(windowWidth - powerUpWidth);

		if (r == 0 && !multiBulletIsPresent) {

			multiBullet = new PowerUp(randomXPosition, yPosition, powerUpWidth, powerUpHeight, Color.BLUE);
			multiBulletIsPresent = true;

		} else if (r == 1 && !speedBulletIsPresent) {

			speedBullet = new PowerUp(randomXPosition, yPosition, powerUpWidth, powerUpHeight, Color.YELLOW);
			speedBulletIsPresent = true;

		} else if (r == 2 && !quickFireBulletIsPresent) {

			quickFireBullet = new PowerUp(randomXPosition, yPosition, powerUpWidth, powerUpHeight, Color.PINK);
			quickFireBulletIsPresent = true;
		}
	}

	/**
	 * A method to spawn an enemy.
	 */
	public void randomEnemySpawn() {

		if (firstEnemySpawn < currentTickCount && gameIsActive) {

			isPausedBetweenWave = false;

			int randomXPosition = random.nextInt(windowWidth - enemyWidth);

			if (totalEnemyDestoryedForCurrentWave <= 10) {

				singleEnemySpawn(randomXPosition);

			} else if (totalEnemyDestoryedForCurrentWave > 10 && totalEnemyDestoryedForCurrentWave <= 30) {

				formation02EnemySpawn(randomXPosition);

			} else if (totalEnemyDestoryedForCurrentWave > 30 && totalEnemyDestoryedForCurrentWave < 50) {

				formation03EnemySpawn(randomXPosition);

			} else if (totalEnemyDestoryedForCurrentWave >= 50) {

				isPausedBetweenWave = true;
				waveNumber++;
				totalEnemyDestoryedForCurrentWave = 0;
				multiBulletIsPresent = false;
				speedBulletIsPresent = false;
				quickFireBulletIsPresent = false;

				// Time to complete wave before new wave begins.
				firstEnemySpawn = currentTickCount + ticksBetweenWaves;
			}

			firstEnemySpawn = currentTickCount + ticksBetweenEnemy;
		}
	}

	/**
	 * A method to spawn an enemy in a single formation at a random x position.
	 * Where 'x' is an enemy formation is: x
	 */
	private void singleEnemySpawn(int randomXPosition) {

		enemyList.add(new Enemy(randomXPosition, -30, enemyWidth, enemyHeight));
		singleEnemyBullet(randomXPosition);
	}

	/**
	 * A method to add bullets to the single enemy type formation. Bullet speed
	 * varies depending on the wave number.
	 * 
	 * @param randomXPosition the position of the bullet to fire from.
	 */
	private void singleEnemyBullet(int randomXPosition) {

		float bulletSpeed = 1.5f;
		int yPosition = -10;
		int width = 10;
		int height = 10;

		// Accounts for the length of the enemy (bullet should fire from center of
		// enemy).
		randomXPosition = randomXPosition + ((enemyWidth / 2) - width / 2);
		
		audio.playMusic("Audio/EnemyShot.wav");


		switch (waveNumber) {
		case 0:
			enemyBulletList.add(new Bullet(randomXPosition, yPosition, width, height, "White",-(bulletSpeed)));
			break;
		case 1:
			enemyBulletList.add(new Bullet(randomXPosition, yPosition, width, height, "White", -(bulletSpeed + 3)));
			break;
		case 2:
			enemyBulletList.add(new Bullet(randomXPosition, yPosition, width, height, "White", -(bulletSpeed + 4)));
			break;
		default:
			enemyBulletList.add(new Bullet(randomXPosition, yPosition, width, height, "White", -(bulletSpeed)));
			break;
		}
	}

	/**
	 * A method to spawn an enemy in formation02 at a random x position. Where 'x'
	 * is an enemy formation is: x x x
	 */
	private void formation02EnemySpawn(int randomXPosition) {

		enemyList.add(new Enemy(randomXPosition + 40, -30, enemyWidth, enemyHeight));
		enemyList.add(new Enemy(randomXPosition + 0, -30, enemyWidth, enemyHeight));
		enemyList.add(new Enemy(randomXPosition - 40, -30, enemyWidth, enemyHeight));
	}

	/**
	 * A method to spawn an enemy in formation03 at a random x position. Where 'x'
	 * is an enemy formation is: x x x
	 */
	private void formation03EnemySpawn(int randomXPosition) {

		enemyList.add(new Enemy(randomXPosition + 30, -30, enemyWidth, enemyHeight));
		enemyList.add(new Enemy(randomXPosition + 0, 0, enemyWidth, enemyHeight));
		enemyList.add(new Enemy(randomXPosition - 30, -30, enemyWidth, enemyHeight));
	}

	/**
	 * A method to check if the player and enemy has collided.
	 * 
	 * @param i The enemy index to check on the enemyList.
	 */
	public void checkPlayerEnemyCollision(int i) {

		if (player.getEntityBounds().intersects(enemyList.get(i).getEntityBounds())) {

			player.lifeLost();
			checkAllPlayerLivesLost();
		}
	}

	/**
	 * A method to check bullet for all types of bullet collision within the game.
	 */
	public void bulletCollision() {

		checkEnemyBulletAndPlayerCollision();

		checkPlayerBulletAndEnemyBulletCollision();

		checkPlayerBulletAndPowerUpCollision();

		checkPlayerBulletAndEnemyCollision();
	}

	/**
	 * A method to check if an enemy bullet has collided with the player.
	 */
	private void checkEnemyBulletAndPlayerCollision() {

		for (int i = 0; i < enemyBulletList.size(); i++) {
			if (player.getEntityBounds().intersects(enemyBulletList.get(i).getEntityBounds())) {

				player.lifeLost();

				if (player.getHealth() <= 0) {
					gameOver();
				}
			}
		}
	}

	/**
	 * A method to check if the players bullet has collided with an enemy bullet.
	 */
	private void checkPlayerBulletAndEnemyBulletCollision() {

		for (int j = 0; j < player.bulletArrayList.size(); j++) {
			for (int i = 0; i < enemyBulletList.size(); i++) {

				if (player.bulletArrayList.get(j).getEntityBounds()
						.intersects(enemyBulletList.get(i).getEntityBounds())) {
					enemyBulletList.remove(i);
				}
			}
		}
	}

	/**
	 * A method to check if the players bullet has collided with a power up.
	 */
	private void checkPlayerBulletAndPowerUpCollision() {

		for (int j = 0; j < player.bulletArrayList.size(); j++) {

			if (multiBulletIsPresent) {
				if (multiBullet.getEntityBounds().intersects(player.bulletArrayList.get(j).getEntityBounds())) {
					
					audio.playMusic("Audio/PowerUp.wav");

					multiBullet.setIsVisible(false);
					player.setMultiBulletActive(true);
					powerUpTimer = currentTickCount + powerUpLength;
				}
			}

			if (speedBulletIsPresent) {
				if (speedBullet.getEntityBounds().intersects(player.bulletArrayList.get(j).getEntityBounds())) {
					
					audio.playMusic("Audio/PowerUp.wav");
					
					speedBullet.setIsVisible(false);
					player.setSpeedBullet(true);
					powerUpTimer = currentTickCount + powerUpLength;
				}
			}

			if (quickFireBulletIsPresent) {
				if (quickFireBullet.getEntityBounds().intersects(player.bulletArrayList.get(j).getEntityBounds())) {
					
					audio.playMusic("Audio/PowerUp.wav");
					
					quickFireBullet.setIsVisible(false);
					player.setQuickFireBullet(true);
					player.setCountsBetweenFire(2);
					powerUpTimer = currentTickCount + powerUpLength;
				}
			}

			if (powerUpTimer < currentTickCount) {
				resetPlayerAfterPowerUp();
			}
		}
	}

	/**
	 * A method to reset the player settings to default after a power-up is no
	 * longer active.
	 */
	private void resetPlayerAfterPowerUp() {
		player.setCountsBetweenFire(10);
		player.setMultiBulletActive(false);
		player.setSpeedBullet(false);
		player.setQuickFireBullet(false);
	}

	/**
	 * A method to check if the players bullet has collided with an enemy.
	 */
	private void checkPlayerBulletAndEnemyCollision() {

		for (int j = 0; j < player.bulletArrayList.size(); j++) {
			for (int i = 0; i < enemyList.size(); i++) {

				if (!player.bulletArrayList.isEmpty() && !enemyList.isEmpty()) {

					if (player.bulletArrayList.get(j).getEntityBounds()
							.intersects(enemyList.get(i).getEntityBounds())) {

						audio.playMusic("Audio/EnemyExpode.wav");

						player.bulletArrayList.remove(j);
						System.out.println(j);
						enemyList.remove(i);

						// Fixes bug 'array index out of bounds'
						j = 0;

						totalEnemyDestoryedForCurrentWave++;
						score++;

					}
					
					/*
					// If the bullet goes off screen remove it from the list. - IE past the top wall. 
					if (player.bulletArrayList.get(j).getYPosition() < -20) {
						player.bulletArrayList.remove(j);
						j = 0;
					}
					*/
				}
			}
		}
	}

	/**
	 * A method to check if the enemy has gone past the player.
	 * 
	 * @param i The enemy index on the enemyList to check.
	 */
	public void checkEnemyGoesPastPlayer(int i) {

		if (enemyList.get(i).getYPosition() >= 500) {

			enemyList.remove(i);
			player.lifeLost();
			checkAllPlayerLivesLost();
		}
	}

	/**
	 * A method to check if all the players lives are lost.
	 */
	private void checkAllPlayerLivesLost() {

		if (player.getHealth() <= 0) {
			gameOver();
		}
	}

	/**
	 * A method which is called when the player has lost all lives.
	 */
	public void gameOver() {

		if (gameIsActive) {

			audio.playMusic("Audio/GameOver.wav");

			gameIsActive = false;
			player.setPlayerSpeed(0);

			// Enemy travel backwards off screen.
			for (int i = 0; i < enemyList.size(); i++) {
				enemyList.get(i).setSpeed(-3);
			}

			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					resetGame();
				}
			}, 3000);
		}
	}
	
	/**
	 * A method to reset the game variables and high score if it has been passed. 
	 */
	private void resetGame() {

		waveNumber = 0;
		totalEnemyDestoryedForCurrentWave = 0;
		currentTickCount = 0;
		firstEnemySpawn = 200;
		enemyBulletList.clear();
		player.bulletArrayList.clear();
		enemyList.clear();
		spaceInvadersGame.getGameManager().setLastScore(score);
		spaceInvadersGame.getGameManager().setHighScore(score);
		score = 0;
		player.resetPlayer();
		gameIsActive = true;
		spaceInvadersGame.setToMenuState();
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		g.drawImage(Assets.spaceBackgroundImage, 0, 0, 500, 500, null);

		player.render(g);

		if (multiBulletIsPresent) {
			multiBullet.render(g);
		}

		if (speedBulletIsPresent) {
			speedBullet.render(g);
		}

		if (quickFireBulletIsPresent) {
			quickFireBullet.render(g);
		}

		for (int i = 0; i < enemyList.size(); i++) {
			enemyList.get(i).render(g);
		}

		for (int i = 0; i < enemyBulletList.size(); i++) {
			enemyBulletList.get(i).render(g);
		}

		for (int i = 0; i < player.bulletArrayList.size(); i++) {
			player.bulletArrayList.get(i).render(g);
		}

		g.setColor(Color.WHITE);
		g.drawLine(0, 450, width, 450);
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Score: " + Integer.toString(score), 20, 480);
		
		g.setColor(Color.WHITE);
		g.drawString("Wave: " + Integer.toString(waveNumber+1), 415, 480);
		
		if(player.getMultiBulletActive()) {
			g.setColor(Color.BLUE);
			g.fillRect(210, 465, 20, 20);			
		}

		if(player.getQuickFireBulletActive()) {
			g.setColor(Color.PINK);
			g.fillRect(210 + 40, 465, 20, 20);				
		}

		if(player.getSpeedBulletActive()) {
			g.setColor(Color.YELLOW);
			g.fillRect(210 + 80, 465, 20, 20);				
		}		
	}
}
