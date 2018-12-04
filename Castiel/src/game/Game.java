package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import game.rooms.archive.Archive;
import game.rooms.hallway.Hallway;
import game.rooms.office.Office;
import main.Item;
import main.Room;

public class Game {

	private GameFileReader fileReader = new GameFileReader();	

	public main.Level create() throws IOException, ParserConfigurationException, SAXException {
		
		// adding rooms
		ArrayList<Room> rooms = new ArrayList<>();
		rooms.add(fileReader.createRoomWithXML("office"));
		rooms.add(fileReader.createRoomWithXML("hallway"));
	    rooms.add(fileReader.createRoomWithXML("archive"));
	    rooms.add(fileReader.createRoomWithXML("labor"));
		// give back level 
		main.Level level = new main.Level(rooms);
		return level;
	}
}
