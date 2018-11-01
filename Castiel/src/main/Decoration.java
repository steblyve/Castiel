package main;
import java.util.ArrayList;

public class Decoration extends Interactable{

	public Decoration(ArrayList<String> validNamesForDecoration, ArrayList<Interaction> generalInteractions) {
		this.generalInteractions = generalInteractions;
		this.validNamesForInteractable = validNamesForDecoration;
	}
	
	@Override
	public String interact(String input, ArrayList<Item> inventory) {
		return this.tryAllgeneralInteractions(input);
	}

}
