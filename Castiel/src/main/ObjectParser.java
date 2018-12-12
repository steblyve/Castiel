package main;

import java.util.ArrayList;

public class ObjectParser {

	public Interactable findSelectedObject(String input, ArrayList<Interactable> interactables) {
		if(interactables.isEmpty()) {
			return null;
		}
		
		String[] words = input.split(" ");
		for(String word : words) {
			for(Interactable interactable : interactables) {
				if(interactable != null && interactable.namesForObject.contains(word)) {
					return interactable;
				}
			}
		}
		
		return null;
	}
}
