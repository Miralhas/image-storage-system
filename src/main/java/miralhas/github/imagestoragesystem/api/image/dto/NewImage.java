package miralhas.github.imagestoragesystem.api.image.dto;

import java.io.InputStream;
import java.nio.file.Path;

public record NewImage(
		InputStream inputStream,
		String contentType,
		Long size,
		Path relativeFolder,
		String fileName
) {
	public Path filePath() {
		return relativeFolder.resolve(fileName);
	}
}
