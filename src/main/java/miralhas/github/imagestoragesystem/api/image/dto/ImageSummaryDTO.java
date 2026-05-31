package miralhas.github.imagestoragesystem.api.image.dto;

import java.util.UUID;

public record ImageSummaryDTO(
		UUID id,
		String contentType,
		String fileName
) {}
