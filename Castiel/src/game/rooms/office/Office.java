package game.rooms.office;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import game.GameFileReader;
import main.Container;
import main.Decoration;
import main.Door;
import main.Interactable;
import main.Interaction;
import main.Item;
import main.Locker;
import main.Room;
import main.Container.Builder;

public class Office {

	private HashMap<String, ArrayList<String>> inputWords = new HashMap<>();

	private HashMap<String, String> responses = new HashMap<>();

	private HashMap<String, ArrayList<String>> names = new HashMap<>();

	private HashMap<String, Item> gameItems = new HashMap<>();
	
	private GameFileReader fileReader = new GameFileReader();

	private HashMap<String, String> roomChangeResponses = new HashMap<>();
	
	public Office(HashMap<String, ArrayList<String>> inputWords, HashMap<String, Item> worldItems) throws IOException {
		names = fileReader.getAllNames("office");
		responses = fileReader.getAllResponses("office");
		this.inputWords = inputWords;
		this.gameItems = worldItems;
		this.roomChangeResponses = this.fileReader.getAllRoomChangeResponses();
	}

	public Room create() throws IOException {
		return new Room(CreateObjectsInOffice(), responses.get("roomDescription"), roomChangeResponses.get("Office"));
	}

	private ArrayList<Interactable> CreateObjectsInOffice() throws IOException {
		ArrayList<Interactable> Interactables = new ArrayList<>();

		// --------------------create chair ------------------//
		ArrayList<String> chairNames = names.get("chair");
		ArrayList<Interaction> chairInteractions = new ArrayList<>();
		chairInteractions.add(new Interaction(responses.get("inspectOfficeChair"), inputWords.get("inspecting")));
		chairInteractions.add(new Interaction(responses.get("standOnOfficeChair"), inputWords.get("climb")));
		Decoration chair = new Decoration(chairNames, chairInteractions);

		// --------------------desk----------------------------///
		ArrayList<String> deskNames = names.get("desk");
		ArrayList<Interaction> deskInteractions = new ArrayList<>();
		deskInteractions.add(new Interaction(responses.get("inspectOfficeDesk"), inputWords.get("inspecting")));
		deskInteractions.add(new Interaction(responses.get("tablehit"), inputWords.get("forceOpen")));
		Decoration desk = new Decoration(deskNames, deskInteractions);

		// ---------------picture---------------------------//
		ArrayList<String> pictureNames = names.get("picture");				
		ArrayList<Interaction> frameInteractions = new ArrayList<>();
		frameInteractions.add(new Interaction(responses.get("inspectOfficePicture"), inputWords.get("inspecting")));
		frameInteractions.add(new Interaction(responses.get("tablehit"), inputWords.get("forceOpen")));
		Builder pictureBuilder = new Container.Builder().addItem(gameItems.get("windowKey"));
		pictureBuilder.addInteractions(frameInteractions).addNamesForObject(pictureNames);
		pictureBuilder.addGetItemInteraction(new Interaction(responses.get("openPicture"), inputWords.get("openback")));
		Container picture = pictureBuilder.build();

		// -------------documents----------------------//
		ArrayList<String> documentsName = names.get("documents");
		ArrayList<Interaction> documentsInteractions = new ArrayList<>();
		documentsInteractions.add(new Interaction(responses.get("inspectOfficeDocuments"), inputWords.get("read")));
		Decoration documents = new Decoration(documentsName, documentsInteractions);

		// ------------ plant ----------------------------//
		ArrayList<String> plantNames = names.get("plant");
		Interaction getKeyInteraction = new Interaction(responses.get("takeOfficeKey"), inputWords.get("pickingUpKey"));
		ArrayList<Interaction> plantInteractions = new ArrayList<Interaction>();
		plantInteractions.add(new Interaction(responses.get("inspectOfficePlant"), inputWords.get("inspecting")));
		Builder plantBuilder = new Container.Builder().addGetItemInteraction(getKeyInteraction);
		plantBuilder.addItem(gameItems.get("officeKey")).addInteractions(plantInteractions).addNamesForObject(plantNames);
		Container plant = plantBuilder.build();

		// ---------------OfficeDoor---------------------------//
		ArrayList<String> doorNames = names.get("door");
		Interaction passDorrInteraction = new Interaction(roomChangeResponses.get("Hallway"), inputWords.get("pass"));
		Interaction unlockInteraction = new Interaction(responses.get("unlockOfficeDoor"), inputWords.get("unlock"));
		ArrayList<Interaction> doorInteractions = new ArrayList<>();
		doorInteractions.add(new Interaction( responses.get("inspectOfficeDoor"), inputWords.get("inspecting")));
		Door officeDoor = new Door(doorNames, gameItems.get("officeKey"), unlockInteraction, 
				passDorrInteraction,responses.get("noKeyOfficeDoor"),doorInteractions);
		
		// --------------Window----------------------//
		ArrayList<String> windowNames = names.get("window");
		Interaction passWindowInteraction = new Interaction(responses.get("passWindow"), inputWords.get("pass"));
		Interaction unlockWindow = new Interaction(responses.get("unlockWindow"), inputWords.get("unlock"));
		ArrayList<Interaction> windowInteractions = new ArrayList<>();
		windowInteractions.add(new Interaction(responses.get("inspectWindow"), inputWords.get("inspecting")));
		windowInteractions.add(new Interaction(responses.get("smashWindow"), inputWords.get("smash")));
		Door window = new Door(windowNames, gameItems.get("windowKey"), unlockWindow, passWindowInteraction,
				responses.get("noKeyWindow"), windowInteractions);

		// ---------------BookShelf--------------------//
		ArrayList<String> bookShelf = names.get("shelf");
		// interactions
		Interaction inspectShelf = new Interaction(responses.get("inspectingShelf"), inputWords.get("inspecting"));
		// build
		ArrayList<Interaction> shelfInteractions = new ArrayList<>();
		shelfInteractions.add(inspectShelf);
		Decoration shelf = new Decoration(bookShelf, shelfInteractions);

		// ------------Books---------------------------//
		ArrayList<String> booksNames = names.get("books");
		ArrayList<Interaction> booksInteractions = new ArrayList<>();
		booksInteractions.add(new Interaction(responses.get("inspectingBooks"), inputWords.get("inspecting")));
		Decoration books = new Decoration(booksNames, booksInteractions);

		// ------------bableBook---------------------//
		ArrayList<String> namesBookBable = names.get("bookBable");
		ArrayList<Interaction> babelInteractions = new ArrayList<>();
		babelInteractions.add(new Interaction(responses.get("inspectDestructionOfBabel"), inputWords.get("read")));
		Decoration bableBook = new Decoration(namesBookBable, babelInteractions);
		
		//-----------darkKnightBook-----------------//
		ArrayList<String> namesBookBlack = names.get("bookBlack");
		ArrayList<Interaction> blackInteractions = new ArrayList<>();
		blackInteractions.add(new Interaction(responses.get("inspectBlackKnight"), inputWords.get("read")));
		Decoration blackBook = new Decoration(namesBookBlack, blackInteractions);
		
		//-----------storiesBook---------------------//
		ArrayList<String> namesBookStories = names.get("bookStories");
		ArrayList<Interaction> storiesInteractions = new ArrayList<>();
		storiesInteractions.add(new Interaction(responses.get("inspectOldStories"), inputWords.get("read")));
		Decoration bookStories = new Decoration(namesBookStories, storiesInteractions);

		// -------------drawer-------------------------//
		ArrayList<String> namesDrawer = names.get("drawer");			
		ArrayList<Interaction> drawerInteractions = new ArrayList<>();
		Interaction unlockDrawerInteraction = new Interaction(responses.get("unlockdrawer"), inputWords.get("unlock"));
		drawerInteractions.add(new Interaction(responses.get("drawerHit"), inputWords.get("forceOpen")));
		drawerInteractions.add(new Interaction(responses.get("inpsectDrawer"), inputWords.get("inspecting")));
		Locker.Builder drawerbuilder = new main.Locker.Builder().addInteractions(drawerInteractions);
		drawerbuilder.addIteminLocker(gameItems.get("ritualDagger")).addNoKeyResponse(responses.get("drawerNoKey"));
		drawerbuilder.addNamesForObject(namesDrawer).addUnlockInteraction(unlockDrawerInteraction);
		Locker drawer = drawerbuilder.build();

		// ------------all objects -------------------//
		Interactables.add(drawer);
		Interactables.add(blackBook);
		Interactables.add(bableBook);
		Interactables.add(bookStories);
		Interactables.add(books);
		Interactables.add(shelf);
		Interactables.add(window);
		Interactables.add(officeDoor);
		Interactables.add(plant);
		Interactables.add(documents);
		Interactables.add(picture);
		Interactables.add(desk);
		Interactables.add(chair);
		return Interactables;
	}

}
