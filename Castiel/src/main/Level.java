package main;

import java.util.ArrayList;

public class Level {

	private ArrayList<Room> rooms = new ArrayList<>();
	
	private ArrayList<Item> inventory = new ArrayList<>();
	
	private String brk = System.getProperty("line.separator"); 
	
	// always starts with index 0 room
	public Room currentRoom;
	
	public Level(ArrayList<Room> rooms) {
		this.rooms = rooms;
		currentRoom = rooms.get(0);
	}	
	
	public String interact(String input) {
		if(input.equals("check inventory")) {
			String response = "Inventory:";
			for(Item item : inventory) {
				response += brk;
				response += item.description;
			}
			return response;
		}
		
		String response = currentRoom.interact(input, inventory);
		if(response == null) {
			return null;
		}
		
		for(Room room : rooms) {
			if(room.enteredRoom(response)) { 	// if you entered the room with that response change it
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
