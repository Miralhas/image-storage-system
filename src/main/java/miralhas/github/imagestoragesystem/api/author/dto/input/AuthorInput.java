package miralhas.github.imagestoragesystem.api.author.dto.input;

import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;

public record AuthorInput(
		@NotBlank
		String name,

		@NotBlank
		String description,

		OffsetDateTime dateOfBirth
) {
}
