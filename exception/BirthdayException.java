package exception;

public class BirthdayException extends Exception {
	public BirthdayException() {
		super("Wrong birthday format. Birthday format is MM/dd/yyyy and year >= 1900.");
	}
}
