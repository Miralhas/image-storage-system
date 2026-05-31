package miralhas.github.imagestoragesystem.domain.image.service.contract;

import java.io.InputStream;
import java.nio.file.Path;

public interface StorableImage {
	InputStream inputStream();
	Path filePath();
	String contentType();
	Long size();
}
