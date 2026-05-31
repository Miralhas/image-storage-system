package miralhas.github.imagestoragesystem.infrastructure.image.factory;

import miralhas.github.imagestoragesystem.domain.image.model.enums.StorageProvider;
import miralhas.github.imagestoragesystem.domain.image.service.contract.ImageStorageService;
import miralhas.github.imagestoragesystem.infrastructure.exception.InternalServerError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ImageStorageFactory {
	private final Map<StorageProvider, ImageStorageService> storageServiceMap;

	public ImageStorageFactory(List<ImageStorageService> services) {
		this.storageServiceMap = services.stream().collect(
				Collectors.toMap(ImageStorageService::getProvider, Function.identity())
		);
	}

	public ImageStorageService getStorage(StorageProvider provider) {
		var service = storageServiceMap.get(provider);
		if (Objects.isNull(service)) throw new InternalServerError();
		return service;
	}
}
