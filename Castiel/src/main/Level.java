package main;

import java.util.ArrayList;

/**
 * A Level contains a List of rooms and the user inventory. If you interact with
 * a level you interact with the current active room. The level switches the
 * active room accoriding to the response of the room.
 * 
 * @author Yves Stebler
 * @version 1.0
 */
public class Level {

	private ArrayList<Room> rooms = new ArrayList<>();

	private ArrayList<Item> inventory = new ArrayList<>();

	private String brk = System.getProperty("line.separator");

	// always start with the room at index 0
	public Room currentRoom;

	/**
	 * builds a level with given rooms.
	 * 
	 * @param rooms a list of rooms from the level. the room with index 0 is the
	 *              start room!
	 */
	public Level(ArrayList<Room> rooms) {
		this.rooms = rooms;
		currentRoom = rooms.get(0);
	}

	/**
	 * interact with the current active room
	 * 
	 * @param input input to interact with the active room.
	 * @return returns response from room.When none found returns null;
	 */
	public String interact(String input) {
		if (input.equals("check inventory")) {
			String response = "Inventory:";
			for (Item item : inventory) {
				response += brk;
				response += item.getDescription();
			}
			return response;
		}

		String response = currentRoom.interact(input, inventory);
		if (response == null) {
			return null;
		}

		for (Room room : rooms) {
			if (room.enteredRoom(response)) { // if you entered the room with that response change it
				currentRoom = room;
				return response + "\n" + room.getRoomDescription();
			}
		}

		return response;
	}

	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}
}
