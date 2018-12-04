package game.rooms.labor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import game.GameFileReader;
import main.Decoration;
import main.Door;
import main.Interactable;
import main.Interaction;
import main.Item;
import main.Room;

public class Labor {

	private HashMap<String, ArrayList<String>> inputWords = new HashMap<>();

	private HashMap<String, String> responses = new HashMap<>();

	private HashMap<String, ArrayList<String>> names = new HashMap<>();

	private GameFileReader fileReader = new GameFileReader();

	private HashMap<String, String> roomChangeResponses = new HashMap<>();

	private HashMap<String, Item> worldItems = new HashMap<>();

	public Labor(HashMap<String, ArrayList<String>> inputWords, HashMap<String, Item> worldItems) throws IOException {
		names = fileReader.getAllNames("labor");
		responses = fileReader.getAllResponses("labor");
		this.inputWords = inputWords;
		this.roomChangeResponses = this.fileReader.getAllRoomChangeResponses();
		this.worldItems = worldItems;
	}

	public Room create() {
		return new Room(CreateObjectsInOffice(), responses.get("roomDescription"), roomChangeResponses.get("labor"));
	}

	private ArrayList<Interactable> CreateObjectsInOffice(){
		ArrayList<Interactable> Interactables = new ArrayList<>();
		
		//----------------door---------------//
		ArrayList<String> doorNames = names.get("door");
		ArrayList<Interaction> doorInteractions = new ArrayList<>();
		doorInteractions.add(new Interaction(responses.get("inspectDoor"), inputWords.get("inspecting")));
		Interaction openDoor = new Interaction(roomChangeResponses.get("Hallway"), inputWords.get("pass"));
		Door door = new Door(doorNames, null, null, openDoor, null, doorInteractions);
		
		//------------------desk-----------------------------------//
		ArrayList<String> deskNames = names.get("desk");
		ArrayList<Interaction> deskInteractions = new ArrayList<>();
		deskInteractions.add(new Interaction(responses.get("inspectTable"), inputWords.get("inspecting")));
		Decoration desk = new Decoration(deskNames, deskInteractions);
		
		//shelf 
		ArrayList<String> shelfNames = names.get("shelf");
		ArrayList<Interaction> shelfInteractions = new ArrayList<>();
		shelfInteractions.add(new Interaction(responses.get("inspectShelf"), inputWords.get("inspecting")));
		Decoration shelf = new Decoration(shelfNames, shelfInteractions);
		
		
		
		return Interactables;
	}
}

