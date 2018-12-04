package main;
import java.util.ArrayList;

public class Decoration extends Interactable{
	
	public Decoration(ArrayList<String> namesForObject, ArrayList<Interaction> interactions) {
		this.generalInteractions = interactions;
		this.namesForObject = namesForObject;
	}
	
	@Override
	public String interact(String input, ArrayList<Item> inventory) {
		return this.tryAllgeneralInteractions(input);
	}

}
