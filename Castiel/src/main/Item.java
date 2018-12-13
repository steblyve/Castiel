package main;

/**
 * An item containing a description and possible interactions to be executed
 * with the item.
 * 
 * @author Yves Stebler
 * @version 1.0
 */
public class Item {

	private String description;
	
	// will be used in further versions of the Projekt.
	private Interaction[] interactions = new Interaction[20];
	
	/**
	 * builds an Item object
	 * 
	 * @param description  item description
	 * @param interactions item interations
	 */
	public Item(String description, Interaction[] interactions) {
		this.description = description;
		if (interactions != null) {
			this.interactions = interactions;
		}
	}
	
	public String getDescription() {
		return description;
	}
}
