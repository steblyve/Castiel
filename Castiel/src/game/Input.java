package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.Level;

public class Input {

	private static String brk = System.getProperty("line.separator");
	private static BufferedReader br;
	private static GameFileReader fileReader = new GameFileReader();
	private static gameSound sound;
	
	public static void main(String[] args) throws Exception {	
	    sound = new gameSound();
        try {
        	printStartMenü();
        }catch(

	IOException e)
	{
		e.printStackTrace();
	}finally
	{
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	}

	private static void printStartMenü() throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException, ParserConfigurationException, SAXException {
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(fileReader.readFile("/game/gameFiles/startMenu.txt") + brk + "");
		
		while (true) {
			String input = br.readLine();
			if(input.equals("new game")) {
				playGame();
			}
			else if (input.contains("load game")) {
				System.out.print("not implemented yet");
			}
			else if (input.contains("tutorial")) {
				System.out.print("not implemented yet");
			}
		}
	}

	private static void playGame() throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException, ParserConfigurationException, SAXException {
		printStartOfGame();
		Game god = new Game();
		Level level = god.create();
		while (true) {
			
			System.out.print(brk + ":" + brk);
			String input = br.readLine();
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			String response = level.interact(input);
			if (response != null) {
				delayedTextOutput(response);
			} else {
				delayedTextOutput("i dont understand");
			}
		}
	}
	
	private static void printStartOfGame() throws InterruptedException, IOException {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		String start = "You wake up in an room that looks familiar." + brk
				+ "your head hurts like hell. You do not remember what happend." + brk
				+ "you feel like you have already been here but you dont quite remember why and when." + brk
				+ "i better look for help";
		System.out.println(start);
	}
	
	private static void delayedTextOutput(String output) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		String[] words = output.split("");
		for (String word : words) {
			Thread.currentThread();
			//Thread.sleep(10);
			System.out.print(word);
		}
	}
}
