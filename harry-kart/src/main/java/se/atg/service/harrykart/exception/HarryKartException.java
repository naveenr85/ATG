package se.atg.service.harrykart.exception;

/*
 * Exception class to throw the message to user on validation failure.
 */
public class HarryKartException extends Exception {

	private static final long serialVersionUID = 1L;

	public HarryKartException(String message) {
		super(message);
	}

	public HarryKartException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
