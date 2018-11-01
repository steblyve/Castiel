package main;
import java.util.ArrayList;

public class Interaction {
	private ArrayList<String> possibleInput = new ArrayList<>();
	private String response;
	private InteractionParser parser = new InteractionParser();
	
	public Interaction(String response, ArrayList<String> possibleWords) {
		this.response = response;
		this.possibleInput = possibleWords;
	}
	
	public String execute(String input) {
		if(parser.inputValid(input, possibleInput)) {
			return response;
		}
		
		return null;
	}
}
