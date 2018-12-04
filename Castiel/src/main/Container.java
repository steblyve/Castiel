package main;

import java.util.ArrayList;

public class Container extends Interactable {

	public static class Builder {

		private ArrayList<Interaction> interactions = new ArrayList<>();
		private ArrayList<String> namesForObject = new ArrayList<>();
		private Item item = null;
		private Interaction getItemInteraction = null;

		public Container build() {
			return new Container(namesForObject, item, getItemInteraction, interactions);
		}

		public Builder addGetItemInteraction(Interaction getItemInteraction) {
			this.getItemInteraction = getItemInteraction;
			return this;
		}

		public Builder addNamesForObject(ArrayList<String> namesForObject) {
			this.namesForObject = namesForObject;
			return this;
		}

		public Builder addItem(Item item) {
			this.item = item;
			return this;
		}

		public Builder addInteractions(ArrayList<Interaction> interactions) {
			this.interactions = interactions;
			return this;
		}
	}

	private Item itemInContainer;
	private Interaction interactionToGetItem;

	private Container(ArrayList<String> namesForObject, Item item, Interaction getItemInteraction,
			ArrayList<Interaction> interactions) {

		this.generalInteractions = interactions;
		this.itemInContainer = item;
		this.namesForObject = namesForObject;
		this.interactionToGetItem = getItemInteraction;
	}

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
