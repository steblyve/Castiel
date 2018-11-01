package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Decoration;
import main.Interaction;
import main.Item;

class DecorationTest {

	private ArrayList<String> validNamesForDecoration = new ArrayList<String>() {{
		add("picture");
		add("painting");
	}};
	
	private ArrayList<String> validLookInteractionInputs = new ArrayList<String>() {{
		add("look");
		add("inspect");
	}};
	
	private ArrayList<String> validHitInteractionInputs = new ArrayList<String>() {{
		add("hit");
		add("destroy");
	}};
	
	private Decoration picture;
	private String responseOnLookingAtPicture = "a picture of a familiy. they look happy";
	private String responseOnHittingPicture = "I dont want to destroy this beautifull picture";
	private ArrayList<Item> inventory = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		ArrayList<Interaction> generalInteractions = new ArrayList<>();
		Interaction lookAtInteraction = new Interaction(responseOnLookingAtPicture, validLookInteractionInputs);
		Interaction hitInteraction = new Interaction(responseOnHittingPicture, validHitInteractionInputs);
		generalInteractions.add(lookAtInteraction);
		generalInteractions.add(hitInteraction);
		picture = new Decoration(validNamesForDecoration, generalInteractions);
	}
	
	@Test
	void interactTest_validInput_validResponse() {	
		String lookResponse = picture.interact(validLookInteractionInputs.get(0), inventory);
		String hitResponse = picture.interact(validHitInteractionInputs.get(0), inventory);
		assertTrue(lookResponse.equals(responseOnLookingAtPicture));	
		assertTrue(hitResponse.equals(responseOnHittingPicture));	
	}
	

}
