package main;
import java.util.ArrayList;

public class InteractionParser {
	
	public Boolean inputValid(String input, ArrayList<String> possibleInput) {
		for(String expectedInput : possibleInput) {
			if(expectedInput.equalsIgnoreCase(input)) {
				return true;
			}
		}
		
		return false;
	}
	
}
