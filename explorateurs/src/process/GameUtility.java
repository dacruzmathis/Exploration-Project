package process;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import configuration.GameConfiguration;

public class GameUtility {

    public static Image readImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.err.println("-- Can not read the image file ! --");
            return null;
        }
    }

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max + 1 - min)) + min;
    }
    
    /**
	 * Simulates the unit time (for an iteration) defined for the simulation. 
	 */
	public static void unitTime() {
		try {
			Thread.sleep(GameConfiguration.GAME_SPEED);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Simulated the necessary refresh time for avoiding printing issue.
	 */
	public static void windowRefreshTime() {
		try {
			Thread.sleep(GameConfiguration.GAME_SPEED * 10);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

}
