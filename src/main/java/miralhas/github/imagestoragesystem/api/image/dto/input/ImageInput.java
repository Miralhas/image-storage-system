package miralhas.github.imagestoragesystem.api.image.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.With;
import miralhas.github.imagestoragesystem.config.validation.FileContentType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@With
public record ImageInput(
		@NotEmpty
		@FileContentType
		List<MultipartFile> files
) {
}