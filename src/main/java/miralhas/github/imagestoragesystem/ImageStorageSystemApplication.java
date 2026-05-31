package miralhas.github.imagestoragesystem;

import com.github.slugify.Slugify;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ImageStorageSystemApplication {

	public static final Slugify SLG = Slugify.builder().
			lowerCase(true)
			.customReplacement("'", "")
			.locale(Locale.ENGLISH).build();

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(ImageStorageSystemApplication.class, args);
	}

}
