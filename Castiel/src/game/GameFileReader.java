package game;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import main.Container;
import main.Container.Builder;
import main.Decoration;
import main.Door;
import main.Interactable;
import main.Interaction;
import main.Item;
import main.Locker;
import main.Room;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


public class GameFileReader {

	public Room createRoomWithXML(String room) throws ParserConfigurationException, SAXException, IOException {
		java.net.URL url = getClass().getResource("rooms/"+ room + "/" +room +".xml");
		File fXmlFile = new File(url.getPath());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("Decoration");
		ArrayList<Interactable> Interactables = new ArrayList<>();

		// getting All Decorations
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				NodeList interactions = eElement.getElementsByTagName("Interactions");
				
				ArrayList<String> names = this.toArrayList(eElement.getElementsByTagName("names").item(0).getTextContent().split(","));
				
				ArrayList<Interaction> allInteractions = createInteractions(interactions);
				Interactables.add(new Decoration(names, allInteractions));
			}
		}

		// getting All Containers
		NodeList cList = doc.getElementsByTagName("Container");
		for (int temp = 0; temp < cList.getLength(); temp++) {

			Node cNode = cList.item(temp);

			if (cNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) cNode;
				NodeList interactions = eElement.getElementsByTagName("Interactions");
				
				ArrayList<String> names = this.toArrayList(eElement.getElementsByTagName("names").item(0).getTextContent().split(","));
				Item item = new Item(eElement.getElementsByTagName("ItemID").item(0).getTextContent().replace("\n", "").replace("\t", "").replace("\r", ""),null);
				
				ArrayList<Interaction> allInteractions = createInteractions(interactions);
				Interaction getItem = createSpecificInteraction(eElement, "GetItemInteraction");			
				
				if(item.description.equals("")) {
					item = null;
				}
				
				Container.Builder builder = new Builder();
				builder.addGetItemInteraction(getItem).addInteractions(allInteractions).addItem(item)
						.addNamesForObject(names);
				Interactables.add(builder.build());
			}
		}

		// getting All Doors
		NodeList dList = doc.getElementsByTagName("Door");
		for (int temp = 0; temp < dList.getLength(); temp++) {

			Node dNode = dList.item(temp);

			if (dNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) dNode;
				NodeList interactions = eElement.getElementsByTagName("Interactions");
				
				ArrayList<String> names = this.toArrayList(eElement.getElementsByTagName("names").item(0).getTextContent().replace("\n", "").replace("\t", "").replace("\r", "").split(","));
				Item key = new Item(eElement.getElementsByTagName("KeyID").item(0).getTextContent().replace("\n", "").replace("\t", "").replace("\r", ""),null);
				String noKeyResponse = eElement.getElementsByTagName("NoKeyResponse").item(0).getTextContent().replace("\t", "").replace("\n", "").replace("\r", "");
				
				ArrayList<Interaction> allInteractions = createInteractions(interactions);
				Interaction passDoor = createSpecificInteraction(eElement, "PassInteraction");			
				Interaction unlockDoor = createSpecificInteraction(eElement, "UnlockInteraction");
				
				if(key.description.equals("")) {
					key = null;
				}
				
				Door door = new Door(names, key, unlockDoor, passDoor, noKeyResponse, allInteractions);
				Interactables.add(door);
			}
		}

		// getting All Lockers
		NodeList lList = doc.getElementsByTagName("Locker");
		for (int temp = 0; temp < lList.getLength(); temp++) {

			Node lNode = lList.item(temp);

			if (lNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) lNode;
				NodeList interactions = eElement.getElementsByTagName("Interactions");
				
				ArrayList<String> names = this.toArrayList(eElement.getElementsByTagName("names").item(0).getTextContent().split(","));
				Item key = new Item(eElement.getElementsByTagName("KeyID").item(0).getTextContent().replace("\n", "").replace("\t", "").replace("\r", ""),null);
				String noKeyResponse = eElement.getElementsByTagName("NoKeyResponse").item(0).getTextContent().replace("\t", "").replace("\n", "").replace("\r", "");
				Item item = new Item(eElement.getElementsByTagName("ItemID").item(0).getTextContent().replace("\n", "").replace("\t", "").replace("\r", ""),null);
				
				ArrayList<Interaction> allInteractions = createInteractions(interactions);
				Interaction unlock = createSpecificInteraction(eElement, "UnlockInteraction");

				if(key.description.equals("")) {
					key = null;
				}
				
				if(item.description.equals("")) {
					item = null;
				}
				
				Locker.Builder builder = new Locker.Builder();
				builder.addInteractions(allInteractions).addIteminLocker(item).addKey(key).addNamesForObject(names)
						.addNoKeyResponse(noKeyResponse).addUnlockInteraction(unlock);

			}
		}
		
		String enterRoomResponse = doc.getElementsByTagName("EnterResponse").item(0).getTextContent().replace("\n", "").replace("\t", "").replace("\r", "");
		String description = doc.getElementsByTagName("Description").item(0).getTextContent().replace("\n", "").replace("\t", "").replace("\r", "");
		
		return new Room(Interactables,description, enterRoomResponse);
	}

	private Interaction createSpecificInteraction(Element eElement, String interactionType) {
		NodeList unlockInteraction = eElement.getElementsByTagName(interactionType);
		Node unlockInteractions = unlockInteraction.item(0);
		Element unlock = (Element) unlockInteractions;
		String response2 = unlock.getElementsByTagName("response").item(0).getTextContent().replace("\n", "").replace("\t", "").replace("\r", "");
		
		if(response2.replace("\n", "").replace("\t", "").replace("\r", "").equals("")) {
			return null;
		}
	
		ArrayList<String> possibleWords2 = toArrayList(
				unlock.getElementsByTagName("inputword").item(0).getTextContent().replace("\n", "").replace("\t", "").replace("\r", "").split(","));
		Interaction unlockDoor = new Interaction(response2, possibleWords2);
		return unlockDoor;
	}

	private ArrayList<Interaction> createInteractions(NodeList interactions) {
		ArrayList<Interaction> allInteractions = new ArrayList<>();
		for (int i = 0; i < interactions.getLength(); i++) {
			Node interaction = interactions.item(i);
			Element action = (Element) interaction;
			String response = action.getElementsByTagName("response").item(0).getTextContent().replace("\n", "").replace("\t", "").replace("\r", "");
			ArrayList<String> possibleWords = toArrayList(
					action.getElementsByTagName("inputword").item(0).getTextContent().split(","));
			allInteractions.add(new Interaction(response, possibleWords));
		}
		return allInteractions;
	}
	
	public HashMap<String, String> getAllResponses(String roomName) throws IOException {
		HashMap<String, String> responses = new HashMap<>();
		String text = readFile("/game/rooms/" + roomName + "/responses.txt");

		String[] chapters = text.split("#");
		for (String chapter : chapters) {
			if (!chapter.equals("")) {
				String name = chapter.split(":")[0];
				String respons = chapter.split(":")[1];
				responses.put(name, respons);
			}
		}

		return responses;
	}

	public HashMap<String, String> getAllRoomChangeResponses() throws IOException {
		HashMap<String, String> responses = new HashMap<>();
		String text = readFile("/game/gameFiles/changeRoomResponses.txt");

		String[] chapters = text.split("#");
		for (String chapter : chapters) {
			if (!chapter.equals("")) {
				String name = chapter.split(":")[0];
				String respons = chapter.split(":")[1];
				responses.put(name, respons);
			}
		}

		return responses;
	}

	public HashMap<String, ArrayList<String>> getAllInputWords() throws IOException {
		String text = readFile("/game/gameFiles/inputWords.txt");
		HashMap<String, ArrayList<String>> inputwords = new HashMap<>();
		String[] lines = text.split("\\r?\\n");
		for (String line : lines) {
			String[] parts = line.split(":");
			ArrayList<String> words = new ArrayList<>();
			for (String word : parts[1].split(",")) {
				words.add(word);
			}

			inputwords.put(parts[0], words);
		}
		return inputwords;
	}

	public HashMap<String, ArrayList<String>> getAllNames(String roomName) throws IOException {
		String text = readFile("/game/rooms/" + roomName + "/names.txt");
		return splitGameFileContent(text);
	}

	public String readFile(String path) throws IOException {
		String text = "";
		InputStream reader = getClass().getResourceAsStream(path);
		int c = 0;
		while ((c = reader.read()) != -1) {
			text += ((char) c);
		}
		return text;
	}

	private HashMap<String, ArrayList<String>> splitGameFileContent(String text) {
		HashMap<String, ArrayList<String>> names = new HashMap<>();
		String[] lines = text.split("\\r?\\n");
		for (String line : lines) {
			String[] parts = line.split(":");
			ArrayList<String> words = new ArrayList<>();
			for (String word : parts[1].split(",")) {
				words.add(word);
			}
			names.put(parts[0], words);
		}
		return names;
	}

	private ArrayList<String> toArrayList(String[] array) {
		ArrayList<String> list = new ArrayList<>();
		for (String word : array) {
			list.add(word.replace("\t", "").replace("\n", ""));
		}

		return list;
	}
}
