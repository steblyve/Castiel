package game.exceptions;

/**
 * Exception beeing thrown when the gamebuilding process fails.
 * @author Yves Stebler
 * @version 1.0	
 */
public class GameBuildingException extends Exception {

	private static final long serialVersionUID = 1L;

	public GameBuildingException() {
		super();
	}
	
	public GameBuildingException(String message) {
		super(message);
	}
}
