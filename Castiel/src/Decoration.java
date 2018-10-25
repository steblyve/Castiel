import java.util.ArrayList;

public class Decoration extends Interactable{

	public Decoration(ArrayList<String> namesOfobject, ArrayList<Interaction> generalInteractions) {
		this.generalInteractions = generalInteractions;
		this.validNamesForInteractable = namesOfobject;
	}
	
	@Override
	public String interact(String input, ArrayList<Item> inventory) {
		return this.tryAllgeneralInteractions(input);
	}

}
