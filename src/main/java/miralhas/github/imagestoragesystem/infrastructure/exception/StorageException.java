package miralhas.github.imagestoragesystem.infrastructure.exception;

public class StorageException extends InternalServerError {
	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
