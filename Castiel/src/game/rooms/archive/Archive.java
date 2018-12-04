package game.rooms.archive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import game.GameFileReader;
import main.Container;
import main.Decoration;
import main.Door;
import main.Interactable;
import main.Interaction;
import main.Item;
import main.Room;

public class Archive {

	private HashMap<String, ArrayList<String>> inputWords = new HashMap<>();

	private HashMap<String, String> responses = new HashMap<>();

	private HashMap<String, ArrayList<String>> names = new HashMap<>();

	private HashMap<String, Item> gameItems = new HashMap<>();
	
	private GameFileReader fileReader = new GameFileReader();

	private HashMap<String, String> roomChangeResponses = new HashMap<>();
	
	public Archive(HashMap<String, ArrayList<String>> inputWords, HashMap<String, Item> worldItems) throws IOException {
		names = fileReader.getAllNames("archive");
		responses = fileReader.getAllResponses("archive");
		this.inputWords = inputWords;
		this.gameItems = worldItems;
		this.roomChangeResponses = this.fileReader.getAllRoomChangeResponses();
	}
	
	public Room create() throws IOException {
		return new Room(CreateObjectsInOffice(), responses.get("roomDescription"), roomChangeResponses.get("Archive"));
	}
	
	private ArrayList<Interactable> CreateObjectsInOffice() throws IOException {
		ArrayList<Interactable> Interactables = new ArrayList<>();
		
		//---------------Table--------------//
		ArrayList<String> tableNames = names.get("table");
		ArrayList<Interaction> tableInteractions = new ArrayList<>();
		tableInteractions.add(new Interaction(responses.get("inspectTable"), inputWords.get("inspecting")));
		Decoration table = new Decoration(tableNames, tableInteractions);
		//---------------drawer--------------//
		ArrayList<String> drawerNames = names.get("drawer");
		ArrayList<Interaction> drawerInteractions = new ArrayList<>();
		drawerInteractions.add(new Interaction(responses.get("inspectDrawer"), inputWords.get("inspecting")));
		Interaction drawerGetItemInteraction = new Interaction(responses.get("openDrawer"), inputWords.get("open"));
		Container.Builder drawerBuilder = new Container.Builder().addGetItemInteraction(drawerGetItemInteraction);
		drawerBuilder.addInteractions(drawerInteractions).addItem(gameItems.get("laborKey")).addNamesForObject(drawerNames);
		Container drawer = drawerBuilder.build();
		
		//---------------candle--------------//
		ArrayList<String> candleNames = names.get("candle");
		ArrayList<Interaction> candleInteractions = new ArrayList<>();
		candleInteractions.add(new Interaction(responses.get("inspectCandel"), inputWords.get("inspecting")));
		Decoration candle = new Decoration(candleNames, candleInteractions);
		
		//---------------documents--------------//
		ArrayList<String> documentNames = names.get("documents");
		ArrayList<Interaction> documentsInteractions = new ArrayList<>();
		documentsInteractions.add(new Interaction(responses.get("inspectDocuments"), inputWords.get("inspecting")));
		Decoration documents = new Decoration(documentNames, documentsInteractions);
		
		//---------------shelf--------------//
		ArrayList<String> shelfNames = names.get("shelf");
		ArrayList<Interaction> shelfInteractions = new ArrayList<>();
		shelfInteractions.add(new Interaction(responses.get("inspectShelf"), inputWords.get("inspecting")));
		Decoration shelf = new Decoration(shelfNames, shelfInteractions);
		
		//----------------Door--------------//
		ArrayList<String> doorNames = names.get("door");
		ArrayList<Interaction> doorInteractions = new ArrayList<>();
		Interaction openDoor = new Interaction(roomChangeResponses.get("Hallway"), inputWords.get("pass"));
		Door door = new Door(doorNames, null, null, openDoor, null, doorInteractions);
		
		Interactables.add(door);
		Interactables.add(table);
		Interactables.add(documents);
		Interactables.add(candle);
		Interactables.add(drawer);
		Interactables.add(shelf);
		
		return Interactables;
	}
}
