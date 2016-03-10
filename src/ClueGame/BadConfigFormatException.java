package clueGame;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException (){}

	public BadConfigFormatException(String message) {
		super("There is an error with the following file: " + message);

	}

}
