package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.InteractionParser;

class InteractionParserTest {

	private InteractionParser parser = new InteractionParser();
	private String validInput = "test";
	private String invalidInput = "invalidanswer";
	private ArrayList<String> possibleInput = new ArrayList<String>() {
		{
			add("testing");
			add("test");
		}
	};

	@Test
	void inputValidTest_falseWithWrongInput() {
		assertFalse(parser.inputValid(invalidInput, possibleInput));
	}

	@Test
	void inputValidTest_trueWithRightInput() {
		assertTrue(parser.inputValid(validInput, possibleInput));
	}

}
