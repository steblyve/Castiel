package main;

import java.util.ArrayList;

/**
 * An interaction can be executed with an given input. is the input valid the
 * response its returned.
 * 
 * @author Yves
 * @version 1.0
 */
public class Interaction {
	private ArrayList<String> possibleInput = new ArrayList<>();
	private String response;
	private InteractionParser parser = new InteractionParser();

	/**
	 * builds a Interaction
	 * 
	 * @param response      response to give back if input is valid
	 * @param possibleWords possible input words
	 */
	public Interaction(String response, ArrayList<String> possibleWords) {
		this.response = response;
		this.possibleInput = possibleWords;
	}

	/**
	 * test if a given input is part of the possiblewords.
	 * 
	 * @param input input to be tested
	 * @return returns response when input is valid.
	 */
	public String execute(String input) {
		if (parser.inputValid(input, possibleInput)) {
			return response;
		}

		return null;
	}
}
