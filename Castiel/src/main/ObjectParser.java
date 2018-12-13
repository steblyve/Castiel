package main;

import java.util.ArrayList;

/**
 * Parser to find an interactable from a given input.
 * 
 * @author Yves
 * @version 1.0
 */
public class ObjectParser {

	/**
	 * Tries to find a Interactable which is inside the input String.
	 * 
	 * @param input         input of user
	 * @param interactables list of interactables to be tested
	 * @return returns the found Interactable. If none found null is returned
	 */
	public Interactable findSelectedObject(String input, ArrayList<Interactable> interactables) {
		if (interactables.isEmpty()) {
			return null;
		}

		String[] words = input.split(" ");
		for (String word : words) {
			for (Interactable interactable : interactables) {
				if (interactable != null && interactable.namesForObject.contains(word)) {
					return interactable;
				}
			}
		}

		return null;
	}
}
