package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Interactable;
import main.Interaction;
import main.Item;
import main.Locker;
import main.Locker.Builder;

@SuppressWarnings("serial")
class LockerTest {

	private Locker locker;

	private ArrayList<String> namesForObject = new ArrayList<String>() {
		{
			add("safe");
			add("tresor");
		}
	};

	private ArrayList<String> openInteractionInput = new ArrayList<String>() {
		{
			add("open");
			add("unlock");
		}
	};

	private ArrayList<String> lookInteractionInput = new ArrayList<String>() {
		{
			add("look");
			add("inspect");
		}
	};

	private ArrayList<Interaction> interactions = new ArrayList<>();
	private ArrayList<Item> inventory = new ArrayList<>();
	private Item itemInLocker;
	private Item key;
	private Interaction unlockInteraction;
	private String unlockRepsonse = "you open the safe and find a diamond";
	private String noKeyResponse = "you need a key to open this safe";
	private String lookAtResponse = "the safe looks realy heavy and is closed";

	@BeforeEach
	void setup() {
		createTestItems();
		createTestInteractions();
		createTestLocker();
	}

	@Test
	void interactTest_Open_NoKey() {
		String response = locker.interact(openInteractionInput.get(0), inventory);
		assertTrue(response.equals(noKeyResponse));
		assertTrue(inventory.isEmpty());
		assertTrue(inventory.isEmpty());
	}

	@Test
	void interactTest_Open_Key() {
		inventory.add(key);
		String response = locker.interact(openInteractionInput.get(0), inventory);
		assertTrue(response.equals(unlockRepsonse));
		assertTrue(inventory.size() == 2);
	}

	@Test
	void interactTest_LookAt() {
		String response = locker.interact(lookInteractionInput.get(0), inventory);
		assertTrue(response.equals(lookAtResponse));
		assertTrue(inventory.isEmpty());
	}

	private void createTestInteractions() {
		unlockInteraction = new Interaction(unlockRepsonse, openInteractionInput);
		Interaction lookAtInteraction = new Interaction(lookAtResponse, lookInteractionInput);
		interactions.add(lookAtInteraction);
	}

	private void createTestItems() {
		itemInLocker = new Item("cristal", null);
		key = new Item("key", null);
		inventory = new ArrayList<>();

	}

	private void createTestLocker() {
		Builder lockerBuilder = new Locker.Builder();
		lockerBuilder.addNamesForObject(namesForObject);
		lockerBuilder.addInteractions(interactions);
		lockerBuilder.addIteminLocker(itemInLocker);
		lockerBuilder.addKey(key);
		lockerBuilder.addUnlockInteraction(unlockInteraction);
		lockerBuilder.addNoKeyResponse(noKeyResponse);
		locker = lockerBuilder.build();
	}
}
