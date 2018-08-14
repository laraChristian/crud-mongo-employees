package co.com.foundation.morphia.exceptions;

public class AvailabilityException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public AvailabilityException(String message, Throwable cause) {
		super(message, cause);
	}

	public AvailabilityException(String message) {
		super(message);
	}

	public AvailabilityException(Throwable cause) {
		super(cause);
	}

}
