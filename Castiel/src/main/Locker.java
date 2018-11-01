package main;

import java.util.ArrayList;

public class Locker extends Interactable {

	private Item keyToOpenLocker;
	private Item itemInLocker;
	private Interaction unlockInteraction;
	private String noKeyResponse;
	private Boolean isLocked = true;

	public Locker(ArrayList<String> validNamesForLocker, Item neededKey, Item itemInLocker,
			Interaction unlockInteraction, String noKeyResponse,
			ArrayList<Interaction> generalInteractions) {
		this.keyToOpenLocker = neededKey;
		this.itemInLocker = itemInLocker;
		this.unlockInteraction = unlockInteraction;
		this.generalInteractions = generalInteractions;
		this.noKeyResponse = noKeyResponse;
		this.validNamesForInteractable = validNamesForLocker;
	}

	public String interact(String input, ArrayList<Item> inventory) {

		String response = unlockInteraction.execute(input);
		if (inventory.contains(keyToOpenLocker) && response != null && isLocked) {
			isLocked = false;
			inventory.add(itemInLocker);
			return response;
		}
		
		// opening door but not the right key
		if(!inventory.contains(keyToOpenLocker) && response != null && isLocked){
			return noKeyResponse;
		}

		return this.tryAllgeneralInteractions(input);
	}
}
