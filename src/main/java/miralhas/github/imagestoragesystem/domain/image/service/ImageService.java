package miralhas.github.imagestoragesystem.domain.image.service;

import lombok.RequiredArgsConstructor;
import miralhas.github.imagestoragesystem.api.image.dto.NewImage;
import miralhas.github.imagestoragesystem.api.image.mapper.ImageMapper;
import miralhas.github.imagestoragesystem.domain.image.exception.ImageNotFoundException;
import miralhas.github.imagestoragesystem.domain.image.model.Image;
import miralhas.github.imagestoragesystem.domain.image.repository.ImageRepository;
import miralhas.github.imagestoragesystem.domain.image.service.contract.ImageStorageService;
import miralhas.github.imagestoragesystem.shared.interfaces.MessageResolver;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
	private final ImageStorageService imageStorageService;
	private final ImageMapper imageMapper;
	private final ImageRepository imageRepository;
	private final MessageResolver messageResolver;

	public Image findByIdOrException(UUID id) {
		return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(
				messageResolver.get("image.id.notFound", id)
		));
	}

	public Image findByNameOrException(String fileName) {
		return imageRepository.findByName(fileName).orElseThrow(() -> new ImageNotFoundException(
				messageResolver.get("image.fileName.notFound", fileName)
		));
	}

	public InputStreamResource getImage(Image image) {
		var imageStream = imageStorageService.retrieve(image.getFilePath());
		return new InputStreamResource(imageStream);
	}

	@Transactional
	public List<Image> save(List<NewImage> newImages) {
		var images = imageMapper.fromNewImages(newImages);
		imageStorageService.saveAll(newImages);
		return imageRepository.saveAllAndFlush(images);
	}

	@Transactional
	public Image update(Image image, NewImage newImage) {
		imageStorageService.replace(newImage, image.getFilePath());
		imageMapper.update(newImage, image);
		return imageRepository.saveAndFlush(image);
	}

	@Transactional
	public void delete(UUID id) {
		var image = findByIdOrException(id);
		imageRepository.deleteById(id);
		imageStorageService.remove(image.getFilePath());
	}

}
