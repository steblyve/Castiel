
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InteractionTest {
	
	Interaction interaction;
	String expectedResponse = "expectedResponse";
	ArrayList<String> possibleCommands =  new ArrayList<String>() {{
		add("test");
		add("testing");
	}};
	
    @BeforeEach
	void setup() {
		interaction = new Interaction(expectedResponse, possibleCommands);
	}
	
	@Test
	void executeTest_validInput_EmptyString() {		
		String input = "test";
		String response = interaction.execute(input);	
		assertTrue(response.equals(expectedResponse));
	}

	@Test
	void executeTest_invalidInput_EmptyString() {
		String input = "invalidInput";
		String response = interaction.execute(input);
		assertTrue(response == null);
	}
}
