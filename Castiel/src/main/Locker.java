package main;

import java.util.ArrayList;

/**
 * A Locker contains a given item. The item will be added to the user inventory
 * when the unlock interaction is triggerd AND the key item is inside said
 * inventory
 * 
 * @author Yves Stebler
 * @version 1.0
 */
public class Locker extends Interactable {

	public static class Builder {

		private ArrayList<Interaction> interactions = new ArrayList<>();
		private ArrayList<String> namesForObject = new ArrayList<>();
		private String noKeyResponse = null;
		private Item itemInLocker = null;
		private Item key = null;
		private Interaction unlockInteraction = null;

		/**
		 * Builder Pattern build method
		 * 
		 * @return returns a Locker object
		 */
		public Locker build() {
			return new Locker(namesForObject, key, itemInLocker, unlockInteraction, noKeyResponse, interactions);
		}

		/**
		 * adds a list of names for the locker
		 * 
		 * @param namesForObject list of names for the Locker
		 * @return builder
		 */
		public Builder addNamesForObject(ArrayList<String> namesForObject) {
			this.namesForObject = namesForObject;
			return this;
		}

		/**
		 * adds a item to the locker
		 * 
		 * @param itemInLocker item inside locker
		 * @return builder
		 */
		public Builder addIteminLocker(Item itemInLocker) {
			this.itemInLocker = itemInLocker;
			return this;
		}

		/**
		 * adds a key needed to open the locker to the locker
		 * 
		 * @param key key which is needed to open the locker
		 * @return builder
		 */
		public Builder addKey(Item key) {
			this.key = key;
			return this;
		}

		/**
		 * adds an interaction to unlock the locker and get the key
		 * 
		 * @param unlockInteraction interaction to unlock locker
		 * @return builder
		 */
		public Builder addUnlockInteraction(Interaction unlockInteraction) {
			this.unlockInteraction = unlockInteraction;
			return this;
		}

		/**
		 * adds a list of general interactions to the locker
		 * 
		 * @param interactions list of general interactions
		 * @return builder
		 */
		public Builder addInteractions(ArrayList<Interaction> interactions) {
			this.interactions = interactions;
			return this;
		}

		/**
		 * adds a no Key repsonse to the locker
		 * 
		 * @param noKeyResponse reponse when no key in inventory
		 * @return builder
		 */
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

	/**
	 * creates a locker object
	 * 
	 * @param namesForObject    names the locker can be called
	 * @param key               key to open locker
	 * @param itemInLocker      item which is inside the locker
	 * @param unlockInteraction interaction to obtain the item inside the locker
	 *                          when you have the right key
	 * @param noKeyResponse     response when no key and try to open
	 * @param interactions      general list of interactions with the locker
	 */
	private Locker(ArrayList<String> namesForObject, Item key, Item itemInLocker, Interaction unlockInteraction,
			String noKeyResponse, ArrayList<Interaction> interactions) {

		this.keyToOpenLocker = key;
		this.itemInLocker = itemInLocker;
		this.unlockInteraction = unlockInteraction;
		this.generalInteractions = interactions;
		this.noKeyResponse = noKeyResponse;
		this.namesForObject = namesForObject;
	}

	/**
	 * interact with the locker. If the input triggers the unlockInteraction and the
	 * right key is inside the inventory the item is added to said inventory. If no
	 * key is in the inventory the noKeyResponse is returned.
	 * 
	 * @param input    	 	input to trigger interactions
	 * @param inventory 	inventory of user
	 * @return returns the response of the Locker. null if no match has been found.
	 */
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
