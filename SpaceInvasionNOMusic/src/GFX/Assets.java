/**
 * Assets - A class to store the games assets. 
 */

/* Using an assets class makes the game run more efficient, images are cropped,
 * saved and then can be re-used as many times as need as opposed to creating the image,
 * cropping it and loading it every 'frame'.
 */

package GFX;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage spaceBackgroundImage;

	public Assets() {
		init();
	}

	/**
	 * A method which is called once only once to assign game assets to their variable holders.
	 */
	public static void init() {
		spaceBackgroundImage = ImageLoader.loadImage("/SpaceBackground.png");
	}

}





