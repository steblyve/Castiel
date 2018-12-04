package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Decoration;
import main.Interactable;
import main.Interaction;
import main.Item;
import main.Room;
import main.Level;
import main.Locker; 

class LevelTest {

	private Level building;
	
	///---------------------create office---------------------------
	private Room office;
	private String description = "you enter an office room with a table in the middle. On the wall is a painting";
	private String lookAtPictureResponse = "a nice picture";
	private String lookAtDeskResponse = "a desk with a drawer";
	private String destroyDeskResponse = "you want to destroy it with bare hands?";
	private ArrayList<Item> inventory = new ArrayList<>();
	private ArrayList<String> possibleLookingInput = new ArrayList<String>() {{
		add("look");
		add("check");
	}};
	private ArrayList<String> possibleDestroyingInput = new ArrayList<String>() {{
		add("destroy");
		add("kick");
	}};
	private ArrayList<String> deskNames = new ArrayList<String>() {{
		add("desk");
		add("table");
	}};
	private ArrayList<String> pictureNames = new ArrayList<String>() {{
		add("picture");
		add("painting");
	}};
	///------------------------------------------------
	
	private Room nextRoom;
	
	private Locker CreateLocker(String[] description, String response, Item containedItem,
			Item neededKey, Interaction UnlockInteraction, Interaction[] generalInteractions) {
		return null;
	}
	
	
	@BeforeEach
	void setup() {
		//----------------------- create office ----------------------------
		Interaction lookAtPictureInteraction = new Interaction(lookAtPictureResponse, possibleLookingInput);
		Interaction lookAtDeskInteraction = new Interaction(lookAtDeskResponse, possibleLookingInput);
		Interaction destroyDeskInteraction = new Interaction(destroyDeskResponse, possibleDestroyingInput);
		ArrayList<Interaction> deskInteractions = new ArrayList<>();
		deskInteractions.add(lookAtDeskInteraction);
		deskInteractions.add(destroyDeskInteraction);
		Decoration desk = new Decoration(deskNames, deskInteractions);
		ArrayList<Interaction> pictureInteraction = new ArrayList<>();
		pictureInteraction.add(lookAtPictureInteraction);
		Decoration picture = new Decoration(pictureNames, pictureInteraction);
		
		ArrayList<Interactable> objectsInRoom = new ArrayList<>();
		objectsInRoom.add(picture);
		objectsInRoom.add(desk);
		office = new Room(objectsInRoom, description, "enter office");
		// ------------------------ create next room -----------------------------
		
		nextRoom = new Room(objectsInRoom, "a small chamber", lookAtPictureResponse);
	}
	
	@Test
	void test() {
		ArrayList<Room> rooms = new ArrayList<>();
		rooms.add(office);
		rooms.add(nextRoom);
		building = new Level(rooms);
		String response = building.interact("look at picture");
		assertTrue(building.currentRoom.equals(nextRoom));
	}

}
