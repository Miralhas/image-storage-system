package miralhas.github.imagestoragesystem.api.image.dto;

import miralhas.github.imagestoragesystem.domain.image.model.enums.StorageProvider;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ImageDTO(
		UUID id,
		Long size,
		String fileName,
		String relativeFolder,
		String contentType,
		OffsetDateTime createdAt,
		OffsetDateTime updatedAt,
		StorageProvider storageProvider
) {}
