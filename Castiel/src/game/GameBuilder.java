package game;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import game.exceptions.FileReadingException;
import game.exceptions.GameBuildingException;
import main.Container;
import main.Decoration;
import main.Door;
import main.Interactable;
import main.Interaction;
import main.Item;
import main.Locker;
import main.Room;
import main.Container.Builder;

/**
 * The GameBuilder can create a Level with the names of the rooms
 * 
 * @author Yves Stebler
 * @version 1.0
 * 
 */
public class GameBuilder {

	private GameFileReader fileReader = new GameFileReader();

	/**
	 * Creates a level out of a list of rooms. The room files zBs. office.xml need
	 * to exist in the Directory: /game/room/ otherwise the level can not be created
	 * 
	 * @param roomNameList a list with the names of all rooms. zbs. "office" the
	 *                     room File for the given name zBs. office.xml needs to
	 *                     exist. Otherwise a exception is thrown
	 * @return returns a Level created by the given Room
	 * @throws GameBuildingException The creation of the Level failed.
	 */
	public main.Level createLevel(ArrayList<String> roomNameList) throws GameBuildingException {
		try {
			ArrayList<Room> rooms = new ArrayList<>();
			for (String roomName : roomNameList) {
				rooms.add(createRoom(roomName, fileReader.readXmlDataForRoom(roomName)));
			}
			main.Level level = new main.Level(rooms);
			return level;
		} catch (FileReadingException ex) {
			throw new GameBuildingException(ex.getMessage());
		}
	}

	/**
	 * create a room out of a XML doc object
	 * 
	 * @param room the name of the room
	 * @param doc  a XML document object.
	 * @return returns a room created out of the XML doc object
	 */
	private Room createRoom(String room, Document doc) {
		ArrayList<Interactable> Interactables = new ArrayList<>();
		Interactables.addAll(createInteractablesOfType(InteractableType.Decoration, doc));
		Interactables.addAll(createInteractablesOfType(InteractableType.Container, doc));
		Interactables.addAll(createInteractablesOfType(InteractableType.Locker, doc));
		Interactables.addAll(createInteractablesOfType(InteractableType.Door, doc));

		// create Room
		String enterRoomResponse = cleanResponses(
				doc.getElementsByTagName(XmlKeyWords.enterRoomResponse).item(0).getTextContent());
		String description = cleanResponses(doc.getElementsByTagName(XmlKeyWords.description).item(0).getTextContent());
		return new Room(Interactables, description, enterRoomResponse);
	}

	/**
	 * Creates a list of interactables from a specific type.
	 * 
	 * @param type
	 * @param doc
	 * @return
	 */
	private ArrayList<Interactable> createInteractablesOfType(InteractableType type, Document doc) {
		ArrayList<Interactable> interactables = new ArrayList<>();
		NodeList nodeList = doc.getElementsByTagName(type.toString());
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node node = nodeList.item(count);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				NodeList interactions = element.getElementsByTagName(XmlKeyWords.Interactions);
				ArrayList<String> names = toArrayList(
						element.getElementsByTagName(XmlKeyWords.names).item(0).getTextContent().split(","));

				// define Items,Responses and Interaction for Interactables.
				Item key = null;
				String noKeyResponse = null;
				Interaction unlockInteraction = null;
				Item item = null;
				Interaction getItemInteraction = null;
				Interaction passDoorInteraction = null;

				// Load Interactable elements of given type
				if (type.equals(InteractableType.Locker) || type.equals(InteractableType.Door)) {
					key = createItemOfElement(element, XmlKeyWords.keyID);
					noKeyResponse = cleanResponses(
							element.getElementsByTagName(XmlKeyWords.noKeyResponse).item(0).getTextContent());
					unlockInteraction = createInteractionOfType(element, InteractionType.UnlockInteraction);
				}
				if (type.equals(InteractableType.Locker) || type.equals(InteractableType.Container)) {
					item = createItemOfElement(element, XmlKeyWords.itemID);
				}
				if (type.equals(InteractableType.Container)) {
					getItemInteraction = createInteractionOfType(element, InteractionType.GetItemInteraction);
				}
				if (type.equals(InteractableType.Door)) {
					passDoorInteraction = createInteractionOfType(element, InteractionType.PassInteraction);
				}

				// build interactable of given type
				ArrayList<Interaction> allInteractions = createGeneralInteractions(interactions);
				Interactable interactable = null;
				switch (type) {
				case Container:
					Builder builder = new Container.Builder();
					builder.addGetItemInteraction(getItemInteraction);
					builder.addInteractions(allInteractions);
					builder.addItem(item);
					builder.addNamesForObject(names);
					interactable = builder.build();
					break;
				case Locker:
					main.Locker.Builder lockerBuilder = new Locker.Builder();
					lockerBuilder.addInteractions(allInteractions);
					lockerBuilder.addIteminLocker(item);
					lockerBuilder.addKey(key);
					lockerBuilder.addNamesForObject(names);
					lockerBuilder.addNoKeyResponse(noKeyResponse);
					lockerBuilder.addUnlockInteraction(unlockInteraction);
					interactable = lockerBuilder.build();
					break;
				case Decoration:
					Decoration decoration = new Decoration(names, allInteractions);
					interactable = decoration;
					break;
				case Door:
					Door door = new Door(names, key, unlockInteraction, passDoorInteraction, noKeyResponse,
							allInteractions);
					interactable = door;
				default:
					break;
				}
				interactables.add(interactable);
			}
		}

		return interactables;
	}

	/**
	 * Creates a interaction of a given type.
	 * 
	 * @param eElement        Xml element containing Interaction
	 * @param interactionType type of the interaction that should be created
	 * @return returns the created interaction.
	 */
	private Interaction createInteractionOfType(Element eElement, InteractionType interactionType) {
		NodeList specialinteractions = eElement.getElementsByTagName(interactionType.toString());
		Node specialInteraction = specialinteractions.item(0);
		Element specialInteractionAsElement = (Element) specialInteraction;
		String response = cleanResponses(
				specialInteractionAsElement.getElementsByTagName(XmlKeyWords.response).item(0).getTextContent());

		if (isEmptyField(response)) {
			return null;
		}

		ArrayList<String> possibleWords = toArrayList(cleanString(
				specialInteractionAsElement.getElementsByTagName(XmlKeyWords.inputWord).item(0).getTextContent())
						.split(","));
		Interaction interaction = new Interaction(response, possibleWords);
		return interaction;
	}

	/**
	 * creates all general interactions (KeyWord Interaction) from a nodeList and
	 * puts them into a list.
	 * 
	 * @param interactions NodeList containing all interactions
	 * @return list of all general Interactions of a given NodeList
	 */
	private ArrayList<Interaction> createGeneralInteractions(NodeList interactions) {
		ArrayList<Interaction> allInteractions = new ArrayList<>();
		for (int i = 0; i < interactions.getLength(); i++) {
			Node interaction = interactions.item(i);
			Element action = (Element) interaction;
			String response = cleanResponses(
					action.getElementsByTagName(XmlKeyWords.response).item(0).getTextContent());
			ArrayList<String> possibleWords = toArrayList(
					action.getElementsByTagName(XmlKeyWords.inputWord).item(0).getTextContent().split(","));
			allInteractions.add(new Interaction(response, possibleWords));
		}
		return allInteractions;
	}

	/**
	 * Creates an Item of a given type from a XML element.
	 * 
	 * @param element        xml element containing the Item
	 * @param identification the type of the item zBs. "KeyID"
	 * @return
	 */
	private Item createItemOfElement(Element element, String identification) {
		Item item = new Item(cleanString(element.getElementsByTagName(identification).item(0).getTextContent()), null);
		if (!isEmptyField(item.getDescription())) {
			return item;
		}
		return null;
	}

	/**
	 * checks if a field contains anything else than tabs linebreaks etc.
	 * 
	 * @param fieldContent String to be tested
	 * @return true if field is empty
	 */
	private boolean isEmptyField(String fieldContent) {
		fieldContent = cleanString(fieldContent);
		if (fieldContent.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * creates a Arraylist from a String array.
	 * 
	 * @param array to be converted
	 * @return returns a arraylist created from the array
	 */
	private ArrayList<String> toArrayList(String[] array) {
		ArrayList<String> list = new ArrayList<>();
		for (String word : array) {
			list.add(cleanString(word));
		}

		return list;
	}

	/**
	 * cleans a string of noise charakters (/n,/t,/r)
	 * 
	 * @param string to be tested
	 * @return cleaned string
	 */
	private String cleanString(String string) {
		return string.replace("\t", "").replace("\n", "").replace("\r", "");
	}

	/**
	 * cleans a string of noise charakters (/t,/r) note: /n is needed in the visual
	 * responses presented to the user herbay this method is needed additionaly
	 * 
	 * @param response to be cleaned
	 * @return cleaned response.
	 */
	private String cleanResponses(String response) {
		return response.replace("\t", "").replace("\r", "");
	}

	/**
	 * This class contains all used XmlKeyWords which are not used in Enums. In case
	 * of changes in the XML structure the user only needs to adjust these values
	 * hence having a easier time updating the code. Get methods where not added
	 * since the class is seen as a Datastructure (Clean Code)
	 * 
	 * @author Yves
	 * @version 1.0
	 */
	private static class XmlKeyWords {

		public static String description = "Description";

		public static String enterRoomResponse = "EnterResponse";

		public static String response = "response";

		public static String inputWord = "inputword";

		public static String itemID = "ItemID";

		public static String Interactions = "Interactions";

		public static String keyID = "KeyID";

		public static String names = "names";

		public static String noKeyResponse = "NoKeyResponse";
	}
}
