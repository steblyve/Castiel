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
		this.validNamesForInteractable = validNamesForDoor;
		this.passDoorInteraction = passDoorInteraction;
	}

	@Override
	public String interact(String input, ArrayList<Item> inventory) {
		
		// opening the door
		String response = unlockInteraction.execute(input);
		if (inventory.contains(keyToOpenDoor) && response != null && isLocked) {
			isLocked = false;
			return response;
		}
		
		// opening door but not the right key
		if(!inventory.contains(keyToOpenDoor) && response != null && isLocked){
			return noKeyResponse;
		}

		// go trough door
		response = passDoorInteraction.execute(input);
		if(response != null) {
			if(isLocked == false) {
				return noKeyResponse;
			}
			else {
				return noKeyResponse;
			}
		}
		
			
		return this.tryAllgeneralInteractions(input);
	}

}
