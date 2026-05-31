package miralhas.github.imagestoragesystem.api.book.dto.input;

import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;

public record BookInput(
		@NotBlank
		String title,

		@NotBlank
		String isbn,

		@NotBlank
		String description,

		OffsetDateTime publishedAt
) {}
