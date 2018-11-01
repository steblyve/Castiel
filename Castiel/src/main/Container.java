package main;
import java.util.ArrayList;

public class Container extends Interactable {

	private Item itemInContainer;
	private Interaction interactionToGetItem;

	public Container(ArrayList<String> validNamesForContainer, Item itemInContainer, Interaction getItemInteraction,
			ArrayList<Interaction> generalInteractions) {
		this.generalInteractions = generalInteractions;
		this.itemInContainer = itemInContainer;
		this.validNamesForInteractable = validNamesForContainer;
		this.interactionToGetItem = getItemInteraction;
	}

	@Override
	public String interact(String input, ArrayList<Item> inventory) {

		// try the interaction which gives the item
		String response = interactionToGetItem.execute(input);
		if (response != null) {
			inventory.add(itemInContainer);
			return response;
		}
		
		return this.tryAllgeneralInteractions(input);
	}
}
