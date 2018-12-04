package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Decoration;
import main.Interactable;
import main.Interaction;
import main.Item;
import main.Room;

class RoomTest {

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
	
	@BeforeEach
	void setup() {
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
		office = new Room(objectsInRoom, description, "enter room response");
	}
	
	@Test
	void interactTest_lookAtPicture() {
		String response = office.interact("look at picture", inventory);
		assertTrue(response.equals(lookAtPictureResponse));
	}
	
	@Test
	void interactTest_lookAtDesk() {
		String response = office.interact("look at desk", inventory);
		assertTrue(response.equals(lookAtDeskResponse));
	}

	@Test
	void interactTest_kickDesk() {
		String response = office.interact("kick table", inventory);
		assertTrue(response.equals(destroyDeskResponse));
	}
}
