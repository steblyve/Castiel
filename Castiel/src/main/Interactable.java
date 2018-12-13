package main;

import java.util.ArrayList;

/**
 * A Interactable can check if a key is in the inventory of the user and also
 * has a method to test all general interactions with a given user input.
 * 
 * @author Yves Stebler
 * @version 1.0
 */
public abstract class Interactable {

	protected ArrayList<String> namesForObject = new ArrayList<>();
	protected ArrayList<Interaction> generalInteractions = new ArrayList<>();

	public abstract String interact(String input, ArrayList<Item> inventory);

	/**
	 * @return returns list with names of the object.
	 */
	public ArrayList<String> getNamesOfObject() {
		return namesForObject;
	}

	/**
	 * try all interactions with the user input and check for a response
	 * 
	 * @param input user input to be tested.
	 * @return returns response or null if nothing found.
	 */
	protected String tryAllgeneralInteractions(String input) {

		String response;
		for (Interaction interaction : generalInteractions) {
			response = interaction.execute(input);
			if (response != null) {
				return response;
			}
		}
		return null;
	}

	/**
	 * check if the given key is in the inventory
	 * 
	 * @param inventory inventory to be tested
	 * @param key       key tested if is in inventory
	 * @return
	 */
	protected boolean rightKeyInInventory(ArrayList<Item> inventory, Item key) {
		for (Item item : inventory) {
			if (item.getDescription().equals(key.getDescription())) {
				return true;
			}
		}

		return false;
	}
}
