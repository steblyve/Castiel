package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Container;
import main.Interaction;
import main.Item;

class ContainerTest {

	private Container container;
	
	private ArrayList<String> namesForObject = new ArrayList<String>() {{
		add("drawer");
		add("the drawer");
	}};

	private ArrayList<String> lookInteractionInput = new ArrayList<String>() {{
		add("look");
		add("inspect");
	}};
	
	private ArrayList<String> getItemInteractionInput = new ArrayList<String>() {{
		add("open");
		add("pull");
	}};
	
	private String responseOnGettingItem = "i found a key int he locker";
	private String responseOnLookingAtContainer = "it is a drawer. doesnt seem to be closed";
	private Item item = new Item("key", null);
	private ArrayList<Item> inventory = new ArrayList<>();
	private Interaction getItemInteraction;
	private Interaction lookAtPicture;
	private ArrayList<Interaction> interactions = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		createTestInteractions();
		createTestContainer();
	}
	
	@Test
	void interactTest_validInput_noItem() {
		String response = container.interact(lookInteractionInput.get(0), inventory);
		assertTrue(response.equals(responseOnLookingAtContainer));
	}

	@Test
	void interactTest_validInput_getItem() {
		String response = container.interact(getItemInteractionInput.get(0), inventory);
		assertTrue(response.equals(responseOnGettingItem));
		assertTrue(inventory.size() == 1);
	}
	
	@Test
	void getNameOfObjectsTest_returnsAllNames() {
		ArrayList<String> returnedNamesOfContainer = container.getNamesOfObject();
		assertTrue(returnedNamesOfContainer.equals(namesForObject));
	}
	
	private void createTestInteractions() {
		getItemInteraction = new Interaction(responseOnGettingItem, getItemInteractionInput);
		lookAtPicture = new Interaction(responseOnLookingAtContainer, lookInteractionInput);
		interactions.add(lookAtPicture);
	}

	private void createTestContainer() {
		main.Container.Builder containerBuilder = new Container.Builder();
		containerBuilder.addGetItemInteraction(getItemInteraction);
		containerBuilder.addInteractions(interactions);
		containerBuilder.addItem(item);
		containerBuilder.addNamesForObject(namesForObject);
		container = containerBuilder.build();
	}
}
