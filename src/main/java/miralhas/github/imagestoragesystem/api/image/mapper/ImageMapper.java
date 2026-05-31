package miralhas.github.imagestoragesystem.api.image.mapper;

import miralhas.github.imagestoragesystem.api.image.dto.ImageDTO;
import miralhas.github.imagestoragesystem.api.image.dto.ImageSummaryDTO;
import miralhas.github.imagestoragesystem.api.image.dto.NewImage;
import miralhas.github.imagestoragesystem.api.image.dto.input.ImageInput;
import miralhas.github.imagestoragesystem.domain.image.model.Image;
import miralhas.github.imagestoragesystem.domain.image.model.enums.StorageProvider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = IGNORE,
		nullValueCheckStrategy = ALWAYS
)
public abstract class ImageMapper {

	@Mapping(target = "relativeFolder", source = "relativeFolder", qualifiedByName = "getRelativeFolder")
	public abstract Image fromNewImage(NewImage newImage);

	public abstract List<Image> fromNewImages(List<NewImage> newImages);

	public abstract ImageDTO toResponse(Image image);

	public abstract List<ImageSummaryDTO> toSummaryCollectionResponse(List<Image> images);

	public abstract List<ImageDTO> toResponseCollection(List<Image> image);

	@Mapping(target = "relativeFolder", source = "relativeFolder", qualifiedByName = "getRelativeFolder")
	public abstract void update(NewImage newImage, @MappingTarget Image image);

	@Named("getRelativeFolder")
	String getRelativeFolder(Path relativeFolder) {
		return relativeFolder.toString();
	}

	public List<NewImage> fromInput(ImageInput input, Path relativeFolder) throws IOException {
		List<NewImage> newImages = new ArrayList<>();
		for (MultipartFile file : input.getFiles()) {
			var newName = generateFileName(file.getOriginalFilename());
			var newImage = new NewImage(
					file.getInputStream(),
					file.getContentType(),
					file.getSize(),
					relativeFolder,
					newName,
					StorageProvider.valueOf(input.getStorageProvider())

			);
			newImages.add(newImage);
		}
		return newImages;
	}

	private String generateFileName(String originalName) {
		return UUID.randomUUID() + "_" + originalName;
	}
}
