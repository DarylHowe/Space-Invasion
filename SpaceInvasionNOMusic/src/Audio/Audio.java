/**
 * Audio - A class which plays music from a specific file location. Note file type must be '.wav'.
 * Audio sound effects can be toggled via the menu screen. 
 * Music will play once through and then stop. 
 */
package Audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	private static boolean audioIsActive = true;
	private Clip clip;
	/**
	 * A method to player music/audio from a specific file location.
	 * 
	 * @param musicLocation A string containing the file name which you wish to
	 *                      play.
	 */
	public void playMusic(String musicLocation) {

		if (audioIsActive) {
			try {
				// Create new file
				File musicPath = new File(musicLocation);

				if (musicPath.exists()) {

					AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
					clip = AudioSystem.getClip();
					clip.open(audioInput);
					clip.start();
					// clip.loop(Clip.LOOP_CONTINUOUSLY);

				} else {
					System.out.println("Cant find file.");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void audioOnOff() {
		if(audioIsActive) {
			audioIsActive = false;
		}else {
			audioIsActive = true;
		}	
	}
	
	public static boolean getAudioIsActive() {
		return audioIsActive;
	}
	
}