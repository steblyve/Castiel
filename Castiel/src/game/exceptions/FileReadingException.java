package game.exceptions;

/**
 * Exception for when an Error while reading a game File occurs.
 * @author Yves Stebler
 * @version 1.0
 */
public class FileReadingException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileReadingException() {
		super();
	}

	public FileReadingException(String message) {
		super(message);
	}
}
