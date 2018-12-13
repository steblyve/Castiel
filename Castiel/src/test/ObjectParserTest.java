package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Decoration;
import main.Door;
import main.Interactable;
import main.Interaction;
import main.Item;
import main.Locker;
import main.ObjectParser;

class ObjectParserTest {

	private ObjectParser parser = new ObjectParser();
	private ArrayList<Interactable> interactables = new ArrayList<>();
	private String lookAtResponse = "looks nice";
	private ArrayList<Item> inventory = new ArrayList<>();

	@BeforeEach
	void setup() {

		ArrayList<String> pictureNames = new ArrayList<String>() {
			{
				add("picture");
				add("image");
			}
		};

		ArrayList<String> plantNames = new ArrayList<String>() {
			{
				add("plant");
				add("growth");
			}
		};

		ArrayList<String> possibleLookAtInput = new ArrayList<String>() {
			{
				add("look");
				add("inspect");
			}
		};

		Interaction lookAtInteraction = new Interaction(lookAtResponse, possibleLookAtInput);
		ArrayList<Interaction> interactions = new ArrayList<>();
		interactions.add(lookAtInteraction);
		Decoration picture = new Decoration(pictureNames, interactions);
		Decoration plant = new Decoration(plantNames, interactions);
		interactables.add(plant);
		interactables.add(picture);
	}

	@Test
	void findSelectedObjectTest_ObjectFound() {
		String input = "look the plant";
		Interactable interactable = parser.findSelectedObject(input, interactables);
		assertTrue(interactable != null);
		String response = interactable.interact(input, inventory);
		assertTrue(response.equals(lookAtResponse));
	}

	@Test
	void findSelectedObjectTest_ObjectNotFound() {
		String input = "look at tresor";
		Interactable interactable = parser.findSelectedObject(input, interactables);
		assertTrue(interactable == null);
	}

}
