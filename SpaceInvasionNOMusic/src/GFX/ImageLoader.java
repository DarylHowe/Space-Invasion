/**
 * ImageLoader - A class which reads/loads an image from a file location and returns as a BufferedImage. 
 */
package GFX;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {

	/**
	 * A method which returns an image at a specific file path.
	 * 
	 * @param path A string containing the file path of an image.
	 * @return A BufferedImage as the image.
	 */
	public static BufferedImage loadImage(String path) {

		try {

			// Return the image.
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();

			// If image can't be loaded close game.
			System.exit(1);
		}
		// Ensures no errors.
		return null;
	}

}
