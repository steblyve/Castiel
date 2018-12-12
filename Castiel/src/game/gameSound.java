package game;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Manager for the gamesound. Can play a writing sound and also start a RoomSound.
 * @author Yves
 * @version 1.0
 */
public class gameSound {

	private Clip writingclip;
	
	private Clip roomClip;
	
	/**
	 * Constructor creating Clips for further use.
	 * @throws LineUnavailableException
	 */
	public gameSound() throws LineUnavailableException {
		writingclip = AudioSystem.getClip();
		roomClip = AudioSystem.getClip();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void playWritingSound() throws Exception {
		if(!writingclip.isActive()) {
			writingclip = AudioSystem.getClip();
			InputStream reader = getClass().getResourceAsStream("/game/gameFiles/writing.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(reader);
			writingclip.open(audioStream);
			writingclip.start();
		}
	}

	/**
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void startRoomSound(String path) throws Exception {
		roomClip.stop();
		roomClip = AudioSystem.getClip();
		InputStream reader = getClass().getResourceAsStream("/game/gameFiles/" + path);
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(reader);
		roomClip.open(audioStream);
		roomClip.loop(99999);
		roomClip.start();
	}
}
