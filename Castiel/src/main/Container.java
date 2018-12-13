package main;

import java.util.ArrayList;

/**
 * A container is an Interactable which can contain an item which can be given
 * out into the inventory by triggering the getItemInteraction.
 * 
 * @author Yves Stebler
 * @version 1.0
 */
public class Container extends Interactable {

	public static class Builder {

		private ArrayList<Interaction> interactions = new ArrayList<>();
		private ArrayList<String> namesForObject = new ArrayList<>();
		private Item item = null;
		private Interaction getItemInteraction = null;

		/**
		 * Builds the Container with the given information
		 * 
		 * @return returns container object
		 */
		public Container build() {
			return new Container(namesForObject, item, getItemInteraction, interactions);
		}

		/**
		 * adds a Interaction to the container which when triggers returns a the given
		 * item to the inventory.
		 * 
		 * @param getItemInteraction
		 * @return returns builder.
		 */
		public Builder addGetItemInteraction(Interaction getItemInteraction) {
			this.getItemInteraction = getItemInteraction;
			return this;
		}

		/**
		 * adds possible names for the container.
		 * 
		 * @param namesForObject possible names
		 * @return returns builder
		 */
		public Builder addNamesForObject(ArrayList<String> namesForObject) {
			this.namesForObject = namesForObject;
			return this;
		}

		/**
		 * adds a item to the container which is given back when triggering the
		 * getItemInteraction
		 * 
		 * @param item to be given back
		 * @return returns builder
		 */
		public Builder addItem(Item item) {
			this.item = item;
			return this;
		}

		/**
		 * adds general interactions for the container.
		 * 
		 * @param interactions list of general interactions.
		 * @return returns builder.
		 */
		public Builder addInteractions(ArrayList<Interaction> interactions) {
			this.interactions = interactions;
			return this;
		}
	}

	private Item itemInContainer;
	private Interaction interactionToGetItem;

	/**
	 * constructor for the building pattern. hence it is private.
	 * 
	 * @param namesForObject     list of names for object.
	 * @param item               item to be given back after triggering the
	 *                           getItemInteraction.
	 * @param getItemInteraction returns item to inventory when triggerd.
	 * @param interactions       general interactions for the container
	 */
	private Container(ArrayList<String> namesForObject, Item item, Interaction getItemInteraction,
			ArrayList<Interaction> interactions) {

		this.generalInteractions = interactions;
		this.itemInContainer = item;
		this.namesForObject = namesForObject;
		this.interactionToGetItem = getItemInteraction;
	}

	/**
	 * check if the input triggers the getItemInteraction. If not check all general
	 * interactions.
	 * 
	 * @param input user input
	 * @param user  inventory
	 * @return returns response or null if nothing found
	 */
	@Override
	public String interact(String input, ArrayList<Item> inventory) {

		// try the interaction which gives the item
		String response = interactionToGetItem.execute(input);
		if (response != null && itemInContainer != null) {
			inventory.add(itemInContainer);
			itemInContainer = null;
			return response;
		}

		return this.tryAllgeneralInteractions(input);
	}
}
