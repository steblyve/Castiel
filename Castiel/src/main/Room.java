package main;

import java.util.ArrayList;

public class Room {
	
	ArrayList<Interactable> objectsInRoom = new ArrayList<>();
	
	private String roomDescription;
	private ObjectParser objectParser = new ObjectParser();
	private String responseToEnterRoom;
	
	public Room(ArrayList<Interactable> objectsInRoom, String roomDescription, String responseToEnterRoom) {
		this.objectsInRoom = objectsInRoom;
		this.roomDescription = roomDescription;
		this.responseToEnterRoom = responseToEnterRoom;
	}	
	
	public String interact(String input, ArrayList<Item> inventory) {
		
		if(input.contains("look around")) {
			return roomDescription;
		}
		
		Interactable interactable = objectParser.findSelectedObject(input, objectsInRoom);
		if(interactable == null) {
			return null;
		}
		
		String response = interactable.interact(input, inventory);
		return response;
	}
	
	public String getRoomDescription() {
		return roomDescription;
	}
	
	public Boolean enteredRoom(String response) {
		if(response.replace("\n", "").replace("\t", "").replace("\r", "").equals(responseToEnterRoom.replace("\n", "").replace("\t", "").replace("\r", ""))) {
			return true;
		}
		return false;
	}
}
