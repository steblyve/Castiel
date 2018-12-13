package game;

import java.util.ArrayList;

/**
* Main class of the Projekt. It creates the game Castiel and starts it. 
* @author Yves Stebler
* @version 1.0
*/
public class Main {

	/**
	*Create and Starts the Game Castiel.
	* @param commandLine Argmunets
	*/
	public static void main(String[] args) throws Exception {
		ArrayList<String> castielRoomList = new ArrayList<>();
		castielRoomList.add("office");
		castielRoomList.add("archive");
		castielRoomList.add("entrance");
		castielRoomList.add("hallway");
		castielRoomList.add("labor");
		castielRoomList.add("security");
		castielRoomList.add("ending1");
		castielRoomList.add("ending2");
		castielRoomList.add("outside");
		castielRoomList.add("experimentChamber");
		
		String startTextFilePath = "/game/gameFiles/startText.txt";
		String helpFilePath = "/game/gameFiles/help.txt";
		String startMenuFilePath = "/game/gameFiles/startMenu.txt";
		Game castiel = new Game(castielRoomList, startTextFilePath, helpFilePath, startMenuFilePath);
		castiel.play();
	}
}
