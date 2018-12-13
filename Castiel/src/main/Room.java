package main;

import java.util.ArrayList;

/**
 * A room contains interactables. A room has a description which is given out to
 * the user when looking around and has an enter response which indicates which
 * response is needed to ENTER this room (make it active).
 * 
 * @author Yves
 * @version 1.0
 */
public class Room {

	private ArrayList<Interactable> objectsInRoom = new ArrayList<>();

	private String roomDescription;
	private ObjectParser objectParser = new ObjectParser();
	private String responseToEnterRoom;

	/**
	 * creates a room
	 * 
	 * @param objectsInRoom       Interactables inside the room
	 * @param roomDescription     description of the room. given out when looking
	 *                            around
	 * @param responseToEnterRoom response which indecates the user has entered the
	 *                            room.
	 */
	public Room(ArrayList<Interactable> objectsInRoom, String roomDescription, String responseToEnterRoom) {
		this.objectsInRoom = objectsInRoom;
		this.roomDescription = roomDescription;
		this.responseToEnterRoom = responseToEnterRoom;
	}

	/**
	 * With the given input the right Interactable is searched and the response with
	 * the interaction with that Interactable is returned
	 * 
	 * @param input     input to interact with
	 * @param inventory inventory of user
	 * @return returns the response of the interactable or null if no match has been
	 *         found.
	 */
	public String interact(String input, ArrayList<Item> inventory) {

		if (input.contains("look around")) {
			return roomDescription;
		}

		Interactable interactable = objectParser.findSelectedObject(input, objectsInRoom);
		if (interactable == null) {
			return null;
		}

		String response = interactable.interact(input, inventory);
		return response;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	/**
	 * check if the given response indicates that the room has been entered.
	 * 
	 * @param response response to check.
	 * @return true if the room was entered with the given response.
	 */
	public Boolean enteredRoom(String response) {
		if (response.replace("\n", "").replace("\t", "").replace("\r", "")
				.equals(responseToEnterRoom.replace("\n", "").replace("\t", "").replace("\r", ""))) {
			return true;
		}
		return false;
	}
}
