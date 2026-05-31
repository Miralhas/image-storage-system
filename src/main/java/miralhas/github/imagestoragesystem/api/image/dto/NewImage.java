package miralhas.github.imagestoragesystem.api.image.dto;

import miralhas.github.imagestoragesystem.domain.image.model.enums.StorageProvider;
import miralhas.github.imagestoragesystem.domain.image.service.contract.StorableImage;

import java.io.InputStream;
import java.nio.file.Path;

public record NewImage(
		InputStream inputStream,
		String contentType,
		Long size,
		Path relativeFolder,
		String fileName,
		StorageProvider storageProvider
) implements StorableImage {
	@Override
	public Path filePath() {
		return relativeFolder.resolve(fileName);
	}
}
