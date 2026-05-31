package miralhas.github.imagestoragesystem.api.book.dto;

import miralhas.github.imagestoragesystem.api.image.dto.ImageSummaryDTO;

import java.time.OffsetDateTime;
import java.util.List;

public record BookDTO(
		Long id,
		String title,
		String slug,
		String isbn,
		String description,
		OffsetDateTime publishedAt,
		OffsetDateTime createdAt,
		OffsetDateTime updatedAt,
		List<ImageSummaryDTO> images
) { }
