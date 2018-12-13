package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Door;
import main.Interaction;
import main.Item;

class DoorTest {

	private Door door;

	private ArrayList<String> validNamesForDoor = new ArrayList<String>() {
		{
			add("door");
			add("portal");
		}
	};

	private ArrayList<String> possibleUnlockInput = new ArrayList<String>() {
		{
			add("unlock");
			add("open");
		}
	};

	private ArrayList<String> possibleInputLooking = new ArrayList<String>() {
		{
			add("look");
			add("inspect");
		}
	};

	private ArrayList<String> possibleWalkTroughInput = new ArrayList<String>() {
		{
			add("walk");
			add("pass");
		}
	};

	private String unlockResponse = "the door is unlocked";
	private String noKeyRepsonse = "the door is locked";
	private String lookAtResponse = "the door looks like it is locked";
	private String passResponse = "you walk into the other room";

	private Item neededKey;
	private Interaction unlockInteraction;
	private ArrayList<Interaction> generalInteractions = new ArrayList<>();
	private ArrayList<Item> inventory = new ArrayList<>();

	@BeforeEach
	void setup() {
		Interaction passDoorInteraction = new Interaction(passResponse, possibleWalkTroughInput);
		Interaction lookAtInteraction = new Interaction(lookAtResponse, possibleInputLooking);
		generalInteractions.add(lookAtInteraction);
		neededKey = new Item("keyForDoor", null);
		unlockInteraction = new Interaction(unlockResponse, possibleUnlockInput);
		door = new Door(validNamesForDoor, neededKey, unlockInteraction, passDoorInteraction, noKeyRepsonse,
				generalInteractions);
		inventory = new ArrayList<>();
	}

	@Test
	void interactTest_noKeyToOpenDoor_NoKeyResponse() {
		String response = door.interact(possibleUnlockInput.get(0), inventory);
		assertTrue(response.equals(noKeyRepsonse));
	}

	@Test
	void interactTest_keyInInventory_DoorOpenResponse() {
		inventory.add(neededKey);
		String response = door.interact(possibleUnlockInput.get(1), inventory);
		assertTrue(response.equals(unlockResponse));
	}

	@Test
	void interactTest_doorAllreadyUnlockedButTryingToUnlock_NoResponse() {
		inventory.add(neededKey);
		door.interact(possibleUnlockInput.get(1), inventory);
		String response = door.interact(possibleUnlockInput.get(1), inventory);
		assertTrue(response == null);
	}

	@Test
	void interactTest_walkTroughLookedDoor_NoResponse() {
		String response = door.interact(possibleWalkTroughInput.get(1), inventory);
		assertTrue(response.equals(noKeyRepsonse));
	}
}
