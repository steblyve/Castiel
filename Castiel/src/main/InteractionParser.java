package main;

import java.util.ArrayList;

/**
 * Parse methods for interactions
 * 
 * @author Yves Stebler
 * @version 1.0
 */
public class InteractionParser {

	/**
	 * checks if an given input is inside a possibleInput
	 * 
	 * @param input         given input
	 * @param possibleInput given possibleinput
	 * @return
	 */
	public Boolean inputValid(String input, ArrayList<String> possibleInput) {

		for (String expectedInput : possibleInput) {
			if (input.contains(expectedInput)) {
				return true;
			}
		}

		return false;
	}

}
