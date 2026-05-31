package miralhas.github.imagestoragesystem.config.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miralhas.github.imagestoragesystem.config.properties.StorageProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class R2Config {
	private final StorageProperties storageProperties;

	@Bean
	public S3Client s3Client() {
		AwsBasicCredentials credentials = AwsBasicCredentials.create(
				storageProperties.r2().accessKey(),
				storageProperties.r2().secretKey()
		);

		return S3Client.builder()
				.endpointOverride(URI.create(storageProperties.r2().endpoint()))
				// R2 does not use real AWS regions; “auto” is accepted by the SDK
				.region(Region.of("auto"))
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				// Required for R2: virtual-hosted style is not supported
				.forcePathStyle(true)
				.build();
	}
}
