package miralhas.github.imagestoragesystem.infrastructure.service;

import lombok.RequiredArgsConstructor;
import miralhas.github.imagestoragesystem.api.image.dto.NewImage;
import miralhas.github.imagestoragesystem.config.properties.StorageProperties;
import miralhas.github.imagestoragesystem.domain.image.service.contract.ImageStorageService;
import miralhas.github.imagestoragesystem.infrastructure.exception.StorageException;
import miralhas.github.imagestoragesystem.shared.interfaces.MessageResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class ImageLocalStorageService implements ImageStorageService {

	private final MessageResolver messageResolver;
	private final StorageProperties storageProperties;

	@Override
	public InputStream retrieve(Path filePath) {
		try {
			return Files.newInputStream(resolvePath(filePath));
		} catch (IOException e) {
			throw new StorageException(messageResolver.get("imageStorage.retrieve", filePath), e);
		}
	}

	@Override
	public void save(NewImage image) {
		try {
			var path = resolvePath(image.filePath());
			FileCopyUtils.copy(image.inputStream(), Files.newOutputStream(path));
		} catch (IOException e) {
			throw new StorageException(messageResolver.get("imageStorage.store", image.filePath()), e);
		}
	}

	@Override
	public void remove(Path filePath) {
		try {
			Files.deleteIfExists(resolvePath(filePath));
		} catch (IOException e) {
			throw new StorageException(messageResolver.get("imageStorage.delete", filePath), e);
		}
	}

	private Path resolvePath(Path filePath) throws IOException {
		Path resolved = storageProperties.getLocalDirectory().resolve(filePath);
		Files.createDirectories(resolved.getParent());
		return resolved;
	}
}
