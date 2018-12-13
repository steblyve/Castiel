package main;

import java.util.ArrayList;

/**
 * Door is an Interactable with the the special interactions
 * "passDoorInteraction" and unlockInteraction. When created the door gets a
 * keyID. Is the Key in the users inventory then the door can be unlocked. is
 * the door unlocked the user can pass the door. The response of Passing the
 * Door should be used to switch between rooms. Does the user not have a key the
 * NoKeyResponse will be given back when passing or unlocking the door.
 * Additionaly general interactions can be added to the door. If no key is used
 * the door is unlocked per default.
 * 
 * @author Yves Stebler
 * @version 1.0
 */
public class Door extends Interactable {

	private Item keyToOpenDoor;
	private Interaction unlockInteraction;
	private String noKeyResponse;
	private Interaction passDoorInteraction;
	private Boolean isLocked = true;

	/**
	 * builds a Door object
	 * 
	 * @param validNamesForDoor   list of names for the door
	 * @param neededKey           key needed for unlocking interactions, when null
	 *                            the door is unlocked per default.
	 * @param unlockInteraction   unlock interaction
	 * @param passDoorInteraction passing door interaction
	 * @param noKeyResponse       response when key not in inventory of user
	 * @param generalInteractions general Interactions for the Door
	 */
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

	/**
	 * interacts with the door through the user input. check if the user wants to
	 * pass the door. is the door locked return noKeyRepsonses. Does the user want
	 * to unlock the door but does not have the key noKeyResponse is returned. Does
	 * the user unlock the door it stays unlocked and the user can pass it.
	 * 
	 * @param input user input
	 * @param user  inventory
	 * @return returns response or null if nothing found
	 */
	@Override
	public String interact(String input, ArrayList<Item> inventory) {

		boolean wantedToPassDoor = false;

		// go trough door
		String response = passDoorInteraction.execute(input);
		if (response != null) {
			if (isLocked == false) {
				return response;
			}
			// set flag that we allready have a possible response
			else {
				wantedToPassDoor = true;
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
				return this.noKeyResponse;
			}
		}

		if (wantedToPassDoor) {
			return noKeyResponse;
		}

		return this.tryAllgeneralInteractions(input);
	}

}
