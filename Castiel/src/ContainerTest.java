import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContainerTest {

	private Container container;
	
	private ArrayList<String> validNamesForContainer = new ArrayList<String>() {{
		add("drawer");
		add("the drawer");
	}};

	private ArrayList<String> validLookInteractionInputs = new ArrayList<String>() {{
		add("look");
		add("inspect");
	}};
	
	private ArrayList<String> validGetItemInteractionInputs = new ArrayList<String>() {{
		add("open");
		add("pull");
	}};
	
	private String responseOnGettingItem = "i found a key int he locker";
	private String responseOnLookingAtContainer = "it is a drawer. doesnt seem to be closed";
	private Item itemInContainer = new Item("key", null);
	private ArrayList<Item> inventory = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		Interaction getItemInDrawer = new Interaction(responseOnGettingItem, validGetItemInteractionInputs);
		Interaction lookAtPicture = new Interaction(responseOnLookingAtContainer, validLookInteractionInputs);
		ArrayList<Interaction> generalInteractions = new ArrayList<>();
		generalInteractions.add(lookAtPicture);
		container = new Container(validNamesForContainer, itemInContainer, getItemInDrawer, generalInteractions);
	}
	
	@Test
	void interactTest_validInput_noItem() {
		String response = container.interact(validLookInteractionInputs.get(0), inventory);
		assertTrue(response.equals(responseOnLookingAtContainer));
	}

	@Test
	void interactTest_validInput_getItem() {
		String response = container.interact(validGetItemInteractionInputs.get(0), inventory);
		assertTrue(response.equals(responseOnGettingItem));
		assertTrue(inventory.size() == 1);
	}
	
	@Test
	void getNameOfObjectsTest_returnsAllNames() {
		ArrayList<String> returnedNamesOfContainer = container.getNamesOfObject();
		assertTrue(returnedNamesOfContainer.equals(validNamesForContainer));
	}
}
