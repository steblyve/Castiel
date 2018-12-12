package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.Level;

/**
 * The game class. A game object creates the game inside its constructor and is
 * playable over a method. It runs until the user closes the game over consol
 * commands.
 * 
 * @author Yves Stebler
 * @version 1.0
 */
public class Game {

	private Boolean gameRunning;
	private GameBuilder gameBuilder = new GameBuilder();
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private GameFileReader fileReader = new GameFileReader();
	private Level level;
	private String startTextFile;
	private String helpFilePath;
	private String startMenuFile;
	private ArrayList<String> roomNameList = new ArrayList<>();

	/**
	 * Konstruktor of Game object.
	 * 
	 * @param roomNameList      a list with all RoomFileNames zBs. "office".
	 * @param startTextFilePath path to the start Text file. zBs.
	 *                          /game/gameFiles/fileName.txt
	 * @param helpFilePath      path to the help Text file zBs.
	 *                          /game/gameFiles/fileName.txt
	 * @param startMenuFilePath path to the Start menu ascii art zBs
	 *                          /game/gameFiles/fileName.txt
	 */
	public Game(ArrayList<String> roomNameList, String startTextFilePath, String helpFilePath,
			String startMenuFilePath) {
		this.roomNameList = roomNameList;
		this.startTextFile = startTextFilePath;
		this.helpFilePath = helpFilePath;
		this.startMenuFile = startMenuFilePath;
	}

	/**
	 * starts the game. The game will not end until quit is entered into the
	 * console.
	 */
	public void play() {
		try {
			gameRunning = true;
			System.out.println(fileReader.readTextFile(startMenuFile));
			while (gameRunning) {
				String input = br.readLine();
				printRepsonse(input);
			}
		} catch (Exception e) {
			System.out.println("Game crashed ERROR:" + e);
		}

	}

	/**
	 * uses the user input to print a response on the console.
	 * 
	 * @param input the input entered by the user
	 * @throws can thow an Exception of any given type
	 */
	private void printRepsonse(String input) throws Exception {

		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

		// check for special commands otherwise it is interacting with the level
		switch (input) {
		case "help":
			System.out.println(fileReader.readTextFile(helpFilePath));
			break;
		case "new game":
			level = gameBuilder.createLevel(roomNameList);
			System.out.println(fileReader.readTextFile(startTextFile));
			break;
		case "quit":
			gameRunning = false;
			break;
		default:
			if (level == null) {
				return;
			}
			String response = level.interact(input);
			if (response != null) {
				delayedTextOutput(response);
				return;
			}
			System.out.println("I dont understand");
			break;
		}
	}

	/**
	 * The text is given out delayd symbol by symbol to give a better reading
	 * experience.
	 * 
	 * @param output. The text to give out on the console
	 * @throws Can throw any exception.
	 */
	private static void delayedTextOutput(String output) throws Exception {
		String[] simbols = output.split("");
		for (String simbol : simbols) {
			Thread.currentThread();
			Thread.sleep(2);
			System.out.print(simbol);
		}
	}
}
