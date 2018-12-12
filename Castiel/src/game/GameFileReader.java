package game;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import game.exceptions.FileReadingException;

/**
 * This class is able to read text Files and XML Files for the game.
 * @author Yves
 * @version 1.0
 */
public class GameFileReader {

	/**
	 * Reads a text file with the given path adn returns the content
	 * @param path  a relative path to the src example: /game/gamefiles/nameOfFile.txt
	 * @return a String with the File content 
	 * @throws FileReadingException thrown when File not found
	 */
	public String readTextFile(String path) throws FileReadingException {
		try {
			String text = "";
			InputStream reader = getClass().getResourceAsStream(path);
			int c = 0;
			while ((c = reader.read()) != -1) {
				text += ((char) c);
			}
			return text;
		} catch (Exception ex) {
			throw new FileReadingException("could not read File with path: " + path
					+ " \n make sure the File is at the given Path and in the correct Format");
		}
	}

	/**
	 * Reads a Xml file from the path /games/room/ and converts it into a xml Document
	 * @param room name off room to read example: office
	 * @return returns a xml Document of the given roomName
	 * @throws FileReadingException thrown when File is invalid or missing
	 */
	public Document readXmlDataForRoom(String room) throws FileReadingException {
		try {
			InputStream resource = getClass().getResourceAsStream("room/" + room + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(resource);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception ex) {
			throw new FileReadingException("could not read room XmlFile for room: " + room
					+ " \n make sure the File is at the given Path and in the correct Format");
		}
	}
}
