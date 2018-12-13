package game;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/**
 * Manager for the gamesound. Can play a writing sound and also start a RoomSound.
 * @author Yves Stebler
 * @version 1.0
 */
public class GameSound {

	private Clip writingclip;
	
	private Clip roomClip;
	
	/**
	 * Constructor creating Clips for further use.
	 * @throws LineUnavailableException
	 */
	public GameSound() throws LineUnavailableException {
		writingclip = AudioSystem.getClip();
		roomClip = AudioSystem.getClip();
	}

	/**
	 * plays a adioclip with a writing sound.
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
	 * plays a aoundfile from the given path. Only works with .wac files
	 * @param path	audio file path example: /game/gameFiles/file.wav
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
