package miralhas.github.imagestoragesystem.domain.book.exception;

import miralhas.github.imagestoragesystem.domain.exception.ResourceNotFoundException;

public class BookNotFoundException extends ResourceNotFoundException {
	public BookNotFoundException(String message) {
		super(message);
	}

	public BookNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
