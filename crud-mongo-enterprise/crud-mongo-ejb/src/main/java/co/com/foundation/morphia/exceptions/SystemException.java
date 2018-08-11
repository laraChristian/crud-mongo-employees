package co.com.foundation.morphia.exceptions;

public class SystemException extends Exception {

	private static final long serialVersionUID = 1L;

	public SystemException(String message) {
		super(message);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

}
