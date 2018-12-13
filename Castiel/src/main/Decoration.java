package main;
import java.util.ArrayList;

/**
 * A Decoration contains a list of Interactabes which can be triggerd by user 
 * input.
 * @author Yves Stebler
 * @version 1.0
 */
public class Decoration extends Interactable{
	
	/**
	 * creates a decoration object
	 * @param namesForObject list with names for object
	 * @param interactions list with interactions
	 */
	public Decoration(ArrayList<String> namesForObject, ArrayList<Interaction> interactions) {
		this.generalInteractions = interactions;
		this.namesForObject = namesForObject;
	}
	
	/**
	 * check if any interaction has been triggerd and return response.
	 * @param input user input
	 * @param user inventory
	 * @return returns response or null if nothing found
	 */
	@Override
	public String interact(String input, ArrayList<Item> inventory) {
		return this.tryAllgeneralInteractions(input);
	}

}
