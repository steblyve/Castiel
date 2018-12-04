package game;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class gameSound {

	private Clip writingclip;
	
	private Clip roomClip;
	
	public gameSound() throws LineUnavailableException {
		writingclip = AudioSystem.getClip();
		roomClip = AudioSystem.getClip();
	}

	public void playWritingSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if(!writingclip.isActive()) {
			writingclip = AudioSystem.getClip();
			InputStream reader = getClass().getResourceAsStream("/game/gameFiles/writing.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(reader);
			writingclip.open(audioStream);
			writingclip.start();
		}
	}
	
	public void startRoomSound(String path) throws LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException {
		roomClip.stop();
		roomClip = AudioSystem.getClip();
		InputStream reader = getClass().getResourceAsStream("/game/gameFiles/" + path);
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(reader);
		roomClip.open(audioStream);
		roomClip.loop(99999);
		roomClip.start();
	}
}
