package main;

import java.util.ArrayList;

public class Locker extends Interactable {

	public static class Builder {

		private ArrayList<Interaction> interactions = new ArrayList<>();
		private ArrayList<String> namesForObject = new ArrayList<>();
		private String noKeyResponse = null;
		private Item itemInLocker = null;
		private Item key = null;
		private Interaction unlockInteraction = null;

		public Locker build() {
			return new Locker(namesForObject, key, itemInLocker, unlockInteraction, noKeyResponse, interactions);
		}

		public Builder addNamesForObject(ArrayList<String> namesForObject) {
			this.namesForObject = namesForObject;
			return this;
		}

		public Builder addIteminLocker(Item itemInLocker) {
			this.itemInLocker = itemInLocker;
			return this;
		}

		public Builder addKey(Item key) {
			this.key = key;
			return this;
		}

		public Builder addUnlockInteraction(Interaction unlockInteraction) {
			this.unlockInteraction = unlockInteraction;
			return this;
		}

		public Builder addInteractions(ArrayList<Interaction> interactions) {
			this.interactions = interactions;
			return this;
		}

		public Builder addNoKeyResponse(String noKeyResponse) {
			this.noKeyResponse = noKeyResponse;
			return this;
		}
	}

	private Item keyToOpenLocker;
	private Item itemInLocker;
	private Interaction unlockInteraction;
	private String noKeyResponse;
	private Boolean isLocked = true;

	private Locker(ArrayList<String> namesForObject, Item key, Item itemInLocker, Interaction unlockInteraction,
			String noKeyResponse, ArrayList<Interaction> interactions) {

		this.keyToOpenLocker = key;
		this.itemInLocker = itemInLocker;
		this.unlockInteraction = unlockInteraction;
		this.generalInteractions = interactions;
		this.noKeyResponse = noKeyResponse;
		this.namesForObject = namesForObject;
	}

	public String interact(String input, ArrayList<Item> inventory) {

		String response = unlockInteraction.execute(input);
		if (this.rightKeyInInventory(inventory, keyToOpenLocker) && response != null && isLocked) {
			isLocked = false;
			inventory.add(itemInLocker);
			return response;
		}

		// opening door but not the right key
		if (!rightKeyInInventory(inventory, keyToOpenLocker) && response != null && isLocked) {
			return noKeyResponse;
		}

		return this.tryAllgeneralInteractions(input);
	}
}
