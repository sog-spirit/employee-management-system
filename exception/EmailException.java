package exception;

public class EmailException extends Exception {
	public EmailException() {
		super("Wrong email format. Make sure email is valid before pressing Enter.");
	}
}
