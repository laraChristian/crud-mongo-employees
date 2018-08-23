package co.com.foundation.morphia.exceptions;

public class EntityNotFoundException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}

}
