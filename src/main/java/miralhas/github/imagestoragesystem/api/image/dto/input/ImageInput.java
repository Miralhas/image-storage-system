package miralhas.github.imagestoragesystem.api.image.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import miralhas.github.imagestoragesystem.config.validation.EnumPattern;
import miralhas.github.imagestoragesystem.config.validation.FileContentType;
import miralhas.github.imagestoragesystem.domain.image.model.enums.StorageProvider;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ImageInput {
		@NotEmpty
		@FileContentType
		private List<MultipartFile> files;

		@EnumPattern(enumClass = StorageProvider.class)
		private String storageProvider = StorageProvider.LOCAL.name();
}