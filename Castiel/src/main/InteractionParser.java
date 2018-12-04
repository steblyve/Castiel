package main;
import java.util.ArrayList;

public class InteractionParser {
	
	public Boolean inputValid(String input, ArrayList<String> possibleInput) {
		
		for(String expectedInput : possibleInput) {
			if(input.contains(expectedInput)) {
				return true;
			}
		}
		
		return false;
	}
	
}
