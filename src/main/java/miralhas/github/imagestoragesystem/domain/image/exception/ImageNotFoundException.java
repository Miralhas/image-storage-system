package miralhas.github.imagestoragesystem.domain.image.exception;

import miralhas.github.imagestoragesystem.domain.exception.ResourceNotFoundException;

public class ImageNotFoundException extends ResourceNotFoundException {
	public ImageNotFoundException(String message) {
		super(message);
	}

	public ImageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
