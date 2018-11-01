package main;
import java.util.ArrayList;

public abstract class Interactable {
	
	protected ArrayList<String> validNamesForInteractable = new ArrayList<>();
    protected ArrayList<Interaction> generalInteractions = new ArrayList<>();
	
	public abstract String interact(String input, ArrayList<Item> inventory);
	
	public ArrayList<String> getNamesOfObject() {
		return validNamesForInteractable;
	}
	
	protected String tryAllgeneralInteractions(String input) {
		
		String response;
		for(Interaction interaction : generalInteractions) {
			response = interaction.execute(input);
			if(response != null) {
				return response;
			}
		}	
		return null;
	}
}
