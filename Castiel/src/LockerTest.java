import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LockerTest {

	Locker locker;
	
	private ArrayList<String> descriptions =new ArrayList<String>() {{
		add("safe");
		add("tresor");
	}};

	private ArrayList<String> possibleInputOpenInteractions =new ArrayList<String>() {{
		add("open");
		add("unlock");
	}};
	
	private ArrayList<String> possibleInputLooking =new ArrayList<String>() {{
		add("look");
		add("inspect");
	}};
	
	private Item itemInLocker;
	private Item neededKey;
	private Interaction unlockInteraction;
	private Interaction keyNeededInteraction;
	private ArrayList<Interaction> generalInteractions = new ArrayList<>();
	private String unlockRepsonse = "you open the safe and find a diamond";
	private String keyNeededResponse = "you need a key to open this safe";
	private String lookAtResponse = "the safe looks realy heavy and is closed";
	private ArrayList<Item> inventory = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		itemInLocker = new Item("cristal", null);
		neededKey = new Item("key", null);
		unlockInteraction = new Interaction(unlockRepsonse, possibleInputOpenInteractions);
		keyNeededInteraction = new Interaction(keyNeededResponse, possibleInputOpenInteractions);
		Interaction lookAtInteraction = new Interaction(lookAtResponse, possibleInputLooking);
		generalInteractions.add(lookAtInteraction);
		inventory = new ArrayList<>();
		locker = new Locker(descriptions, neededKey, itemInLocker, unlockInteraction, keyNeededInteraction, generalInteractions);
	}
	
	@Test
	void interactTest_Open_NoKey() {
		String response = locker.interact(possibleInputOpenInteractions.get(0), inventory);
		assertTrue(response.equals(keyNeededResponse));
		assertTrue(locker.CollectItem() == null);
	}

	@Test
	void interactTest_Open_Key() {
		inventory.add(neededKey);
		String response = locker.interact(possibleInputOpenInteractions.get(0), inventory);
		assertTrue(response.equals(unlockRepsonse));
		assertTrue(locker.CollectItem().equals(itemInLocker));
	}
	
	@Test
	void interactTest_LookAt() {
		String response = locker.interact(possibleInputLooking.get(0), inventory);
		assertTrue(response.equals(lookAtResponse));
		assertTrue(locker.CollectItem() == null);
	}
}
