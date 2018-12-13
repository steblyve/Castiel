package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.GameBuilder;
import game.exceptions.GameBuildingException;
import main.Item;

class GameBuilderTest {

	private GameBuilder gameBuilder = new GameBuilder();
	private ArrayList<String> testNameList = new ArrayList<>();
	private ArrayList<String> invalidNameList = new ArrayList<>();
	ArrayList<Item> inventory = new ArrayList<>();

	@BeforeEach
	void setup() {
		testNameList = new ArrayList<>();
		testNameList.add("test");
		invalidNameList = new ArrayList<>();
		invalidNameList.add("invalid");
		inventory.add(new Item("testKey", null));
	}

	@Test
	void CreateLevelTest_BuildTestLevel_NoBuildingError() {
		Exception exception = null;
		try {
			gameBuilder.createLevel(testNameList);
		} catch (GameBuildingException e) {
			exception = e;
		}

		assertTrue(exception == null);
	}

	@Test
	void CreateLevelTest_BuildTestInvalidLevel_BuildingError() {
		Exception exception = null;
		try {
			gameBuilder.createLevel(invalidNameList);
		} catch (GameBuildingException e) {
			exception = e;
		}

		assertTrue(exception instanceof GameBuildingException);
	}

	@Test
	void CreateLevelTest_BuildTestLevel_DecorationBuildCorrectly() throws GameBuildingException {
		String expectedResponseTest = "testResponse";
		main.Level level = gameBuilder.createLevel(testNameList);
		String response = level.interact("testDecoration test").replace("\n", "");

		assertTrue(response.equals(expectedResponseTest));
	}

	@Test
	void CreateLevelTest_BuildTestLevel_ContainterBuildCorrectly() throws GameBuildingException {
		String expectedResponseTest = "testResponse";
		String expectedResponseGetItem = "getItemResponse";
		String inputTest = "testContainer test";
		String inputGetItem = "testContainer getItem";
		main.Level level = gameBuilder.createLevel(testNameList);
		String responseTest = level.interact(inputTest).replace("\n", "");
		String responseGetItem = level.interact(inputGetItem).replace("\n", "");

		assertEquals(expectedResponseTest, responseTest);
		assertEquals(expectedResponseGetItem, responseGetItem);
	}

	@Test
	void CreateLevelTest_BuildTestLevel_DoorBuildCorrectly() throws GameBuildingException {
		String expectedResponseTest = "testResponse";
		String expectedResponsePass = "passResponse";
		String expectedResponseUnlock = "unlockResponse";
		String expectedResponseNoKey = "testNoKey";
		String inputTest = "testDoor test";
		String inputPass = "testDoor pass";
		String inputUnlock = "testDoor unlock";
		main.Level level = gameBuilder.createLevel(testNameList);

		String responseTest = level.interact(inputTest).replace("\n", "");
		assertEquals(expectedResponseTest, responseTest);

		// Tests without key in inventory
		String responseNoKey = level.interact(inputUnlock).replace("\n", "");
		assertEquals(expectedResponseNoKey, responseNoKey);
		responseNoKey = level.interact(inputPass).replace("\n", "");
		assertEquals(expectedResponseNoKey, responseNoKey);

		// Tests with key in inventory
		level.setInventory(inventory);
		String responseUnlock = level.interact(inputUnlock).replace("\n", "");
		String responsePass = level.interact(inputPass).replace("\n", "");
		assertEquals(expectedResponseUnlock, responseUnlock);
		assertEquals(expectedResponsePass, responsePass);
	}

	@Test
	void CreateLevelTest_BuildTestLevel_LockerBuildCorrectly() throws GameBuildingException {
		String expectedResponseTest = "testResponse";
		String expectedResponseUnlock = "unlockResponse";
		String expectedResponseNoKey = "testNoKey";
		String inputTest = "testDoor test";
		String inputUnlock = "testDoor unlock";
		main.Level level = gameBuilder.createLevel(testNameList);

		String responseTest = level.interact(inputTest).replace("\n", "");
		assertEquals(expectedResponseTest, responseTest);

		// Tests without key in inventory
		String responseNoKey = level.interact(inputUnlock).replace("\n", "");
		assertEquals(expectedResponseNoKey, responseNoKey);

		// Tests with key in inventory
		level.setInventory(inventory);
		String responseUnlock = level.interact(inputUnlock).replace("\n", "");
		assertEquals(expectedResponseUnlock, responseUnlock);
	}
}
