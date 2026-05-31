package miralhas.github.imagestoragesystem.domain.image.service.contract;

import miralhas.github.imagestoragesystem.api.image.dto.NewImage;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface ImageStorageService {
	InputStream retrieve(Path filePath);

	void save(NewImage newImage);

	void remove(Path filePath);

	default void saveAll(List<NewImage> newImages) {
		newImages.forEach(this::save);
	}

	default void replace(NewImage newImage, Path oldFilePath) {
		save(newImage);
		if (oldFilePath != null) remove(oldFilePath);
	}

}
