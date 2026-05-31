package miralhas.github.imagestoragesystem.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "storage")
public record StorageProperties(
		DataSize multipartMaxFileSize,
		LocalStorage local
) {
	public record LocalStorage(Path directory) {}

	public Path getLocalDirectory() {
		return this.local.directory;
	}
}

