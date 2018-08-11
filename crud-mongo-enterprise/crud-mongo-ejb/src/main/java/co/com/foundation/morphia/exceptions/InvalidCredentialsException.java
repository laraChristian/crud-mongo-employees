package co.com.foundation.morphia.exceptions;

public class InvalidCredentialsException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException(String message) {
		super(message);
	}

	public InvalidCredentialsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCredentialsException(Throwable cause) {
		super(cause);
	}

}
