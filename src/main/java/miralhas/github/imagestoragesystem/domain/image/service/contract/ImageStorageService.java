package miralhas.github.imagestoragesystem.domain.image.service.contract;

import miralhas.github.imagestoragesystem.domain.image.model.enums.StorageProvider;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface ImageStorageService {
	InputStream retrieve(Path filePath);

	void save(StorableImage storableImage);

	void remove(Path filePath);

	default void saveAll(List<? extends StorableImage> storableImages) {
		storableImages.forEach(this::save);
	}

	default void replace(StorableImage storableImage, Path oldFilePath) {
		save(storableImage);
		if (oldFilePath != null) remove(oldFilePath);
	}

	StorageProvider getProvider();
}
