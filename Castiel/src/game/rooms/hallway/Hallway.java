package game.rooms.hallway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import game.GameFileReader;
import main.Decoration;
import main.Door;
import main.Interactable;
import main.Interaction;
import main.Item;
import main.Room;

public class Hallway {

	private HashMap<String, ArrayList<String>> inputWords = new HashMap<>();

	private HashMap<String, String> responses = new HashMap<>();

	private HashMap<String, ArrayList<String>> names = new HashMap<>();

	private GameFileReader fileReader = new GameFileReader();

	private HashMap<String, String> roomChangeResponses = new HashMap<>();
	
	private HashMap<String, Item> worldItems = new HashMap<>();
	
	public Hallway(HashMap<String, ArrayList<String>> inputWords, HashMap<String, Item> worldItems) throws IOException {
		names = fileReader.getAllNames("hallway");
		responses = fileReader.getAllResponses("hallway");
		this.inputWords = inputWords;
		this.roomChangeResponses = this.fileReader.getAllRoomChangeResponses();
		this.worldItems = worldItems;
	}
	
	public Room create() {
		return new Room(CreateObjectsInOffice(), responses.get("roomDescription"), roomChangeResponses.get("Hallway"));
	}
	
	private ArrayList<Interactable> CreateObjectsInOffice(){
		ArrayList<Interactable> Interactables = new ArrayList<>();
		
		//------------------desk-----------------------------------//
		ArrayList<String> deskNames = names.get("desk");
		ArrayList<Interaction> deskInteractions = new ArrayList<>();
		deskInteractions.add(new Interaction(responses.get("inspectTable"), inputWords.get("inspecting")));
		Decoration desk = new Decoration(deskNames, deskInteractions);
		//------------------officeDoor-----------------------------//
		ArrayList<String> officeDoorNames = names.get("officeDoor");
		ArrayList<Interaction> officeDoorInteractions = new ArrayList<>();
		officeDoorInteractions.add(new Interaction(responses.get("inspectOfficeDoor"), inputWords.get("inspecting")));
		Interaction openInteractionOffice = new Interaction(roomChangeResponses.get("Office"), inputWords.get("pass"));
		Door officeDoor = new Door(officeDoorNames, null, null, openInteractionOffice, null, officeDoorInteractions);
		//-----------------archiveDoor----------------------------//
		ArrayList<String> archiveDoorNames = names.get("archiveDoor");
		ArrayList<Interaction> archiveDoorInteractions = new ArrayList<>();
		archiveDoorInteractions.add(new Interaction(responses.get("inspectArchiveDoor"),  inputWords.get("inspecting")));
		Interaction openInteractionArchive = new Interaction(roomChangeResponses.get("Archive"), inputWords.get("pass"));
		Door archiveDoor = new Door(archiveDoorNames, null, null, openInteractionArchive, null, archiveDoorInteractions);
		//-----------------laborDoor-----------------------------//
		ArrayList<String> laborDoorNames = names.get("laborDoor");
		ArrayList<Interaction> laborDoorInteractions = new ArrayList<>();
		laborDoorInteractions.add(new Interaction(responses.get("inspectLaborDoor"), inputWords.get("inspecting")));
		Interaction openInteractionLabor = new Interaction(roomChangeResponses.get("Labor"), inputWords.get("pass"));
		Interaction unlockLabor = new Interaction(responses.get("unlockLaborDoor"), inputWords.get("unlock"));
		Door laborDoor = new Door(laborDoorNames, worldItems.get("laborKey"),unlockLabor , openInteractionLabor,responses.get("noKeyLaborDoor") , laborDoorInteractions);
		//------------------mirror------------------------------//
		ArrayList<String> mirrorNames = names.get("mirror");
		ArrayList<Interaction> mirrorInteractions = new ArrayList<>();
		mirrorInteractions.add(new Interaction(responses.get("inspectMirror"), inputWords.get("inspecting")));
		Decoration mirror = new Decoration(mirrorNames, mirrorInteractions);
		//----------------stairs ------------------------------//
		ArrayList<String> stairNames = names.get("stairs");
		ArrayList<Interaction> stairInteractions = new ArrayList<>();
		stairInteractions.add(new Interaction(responses.get("inspectStairs"), inputWords.get("inspecting")));
		Interaction walkDownStairs= new Interaction(roomChangeResponses.get("DownStairs"), inputWords.get("pass"));
		Door stairs = new Door(stairNames, null, null, walkDownStairs, null, stairInteractions);

		Interactables.add(mirror);
		Interactables.add(stairs);
		Interactables.add(desk);
		Interactables.add(laborDoor);
		Interactables.add(officeDoor);
		Interactables.add(archiveDoor);
		return Interactables;
	}
}
