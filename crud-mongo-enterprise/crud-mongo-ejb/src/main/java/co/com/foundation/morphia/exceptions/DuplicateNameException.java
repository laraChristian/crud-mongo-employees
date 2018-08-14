package co.com.foundation.morphia.exceptions;

public class DuplicateNameException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public DuplicateNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateNameException(String message) {
		super(message);
	}

	public DuplicateNameException(Throwable cause) {
		super(cause);
	}

}
