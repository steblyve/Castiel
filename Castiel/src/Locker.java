import java.util.ArrayList;

public class Locker extends Interactable {

	private Item neededKey;
	private Item itemInLocker;
	private Interaction unlockInteraction;
	private Interaction keyNeededInteraction;
	private Boolean isLocked = true;
	
	public Locker(ArrayList<String> namesOfobject, Item neededKey, Item itemInLocker, Interaction unlockInteraction, Interaction keyNeededInteraction, ArrayList<Interaction> generalInteractions) {
		this.neededKey = neededKey;
		this.itemInLocker = itemInLocker;
		this.unlockInteraction = unlockInteraction;
		this.generalInteractions = generalInteractions;
		this.keyNeededInteraction = keyNeededInteraction;
		this.validNamesForInteractable = namesOfobject;
	}
	
	public String interact(String input, ArrayList<Item> inventory) {
		
		String response;
		if(inventory.contains(neededKey)) {
			response = unlockInteraction.execute(input);
			isLocked = false;
		}
		else {
			response = keyNeededInteraction.execute(input);
		}
		
		if(response != null) {
			return response;
		}
		
		return this.tryAllgeneralInteractions(input);
	}
	
	public Item CollectItem() {
		if(isLocked) {
			return null;
		}
		
		return itemInLocker;
	}
}
