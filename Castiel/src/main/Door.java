package main;

import java.util.ArrayList;

public class Door extends Interactable {

	private Item keyToOpenDoor;
	private Interaction unlockInteraction;
	private String noKeyResponse;
	private Interaction passDoorInteraction;
	private Boolean isLocked = true;

	public Door(ArrayList<String> validNamesForDoor, Item neededKey, Interaction unlockInteraction,
			Interaction passDoorInteraction, String noKeyResponse, ArrayList<Interaction> generalInteractions) {
		this.keyToOpenDoor = neededKey;
		this.unlockInteraction = unlockInteraction;
		this.generalInteractions = generalInteractions;
		this.noKeyResponse = noKeyResponse;
		this.namesForObject = validNamesForDoor;
		this.passDoorInteraction = passDoorInteraction;
		if (neededKey == null) {
			isLocked = false;
		}
	}

	@Override
	public String interact(String input, ArrayList<Item> inventory) {

		// go trough door
		String response = passDoorInteraction.execute(input);
		if (response != null) {
			if (isLocked == false) {
				return response;
			} else {
				return noKeyResponse;
			}
		}

		if (unlockInteraction != null) {

			// opening the door
			response = unlockInteraction.execute(input);
			if (isLocked && rightKeyInInventory(inventory, keyToOpenDoor) && response != null) {
				isLocked = false;
				return response;
			}

			// opening door but not the right key
			if (isLocked && !rightKeyInInventory(inventory, keyToOpenDoor) && response != null) {
				return noKeyResponse;
			}
		}

		return this.tryAllgeneralInteractions(input);
	}

}
