package miralhas.github.imagestoragesystem.api.book.dto;

import java.time.OffsetDateTime;

public record BookSummaryDTO(
		Long id,
		String title,
		String slug,
		String isbn,
		OffsetDateTime publishedAt,
		OffsetDateTime createdAt
) {
}
