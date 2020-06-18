/**
 * Display - A class to create a game window and canvas to draw on. The frame holds the canvas and the program draws
 * to/on the canvas. 
 */

package Display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {

	private Canvas canvas;
	private JFrame jFrame;

	/**
	 * Creates a frame and canvas and sets their settings.
	 * 
	 * @param title  A string to be displayed as the window's name/title.
	 * @param width  An int setting the width of the window.
	 * @param height An int setting the height of the window.
	 */
	public Display(String title, int width, int height) {

		jFrame = new JFrame(title);
		jFrame.setSize(width, height);
		jFrame.setResizable(false);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));

		// Ensures JFrame is the only thing that can have focus, without this computer
		// may be listening only to the canvas (we have our KeyListener on the JFrame.)
		canvas.setFocusable(false);

		jFrame.add(canvas);

		// Ensures entire canvas can always be fully seen within frame by slightly
		// adjusting frame size.
		jFrame.pack();
	}

	// Getters

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getJFrame() {
		return jFrame;
	}
}
