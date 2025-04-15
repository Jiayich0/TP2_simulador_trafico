package simulator.view;

import java.io.InputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	private static float _volume = -25.0f;	// la f es para indicar que es un float (no un double)
	
	public static void playSound(String soundFilePath) {
		try {	
			InputStream soundInputStream = Sound.class.getResourceAsStream(soundFilePath);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundInputStream);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); // Master_Gain en decibelios
			gainControl.setValue(_volume);
			
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.err.println("Ha cargado mal " + soundFilePath);
		}
	}
}
