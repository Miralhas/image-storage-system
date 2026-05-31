package miralhas.github.imagestoragesystem.api.author.dto;

import miralhas.github.imagestoragesystem.api.image.dto.ImageSummaryDTO;

import java.time.OffsetDateTime;
import java.util.List;

public record AuthorDTO(
		Long id,
		String name,
		String description,
		OffsetDateTime dateOfBirth,
		OffsetDateTime createdAt,
		OffsetDateTime updatedAt,
		List<ImageSummaryDTO> images
) {
}
