package main;

public class Item {
	
	public Item(String description, Interaction[] interactions) {
		this.description = description;
		if(interactions != null) {
			this.interactions = interactions;
		}
	}
	
	public String description;
	Interaction[] interactions = new Interaction[20];
}
