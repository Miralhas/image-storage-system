package miralhas.github.imagestoragesystem.api.image.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miralhas.github.imagestoragesystem.api.image.dto.ImageDTO;
import miralhas.github.imagestoragesystem.api.image.dto.input.ImageInput;
import miralhas.github.imagestoragesystem.api.image.mapper.ImageMapper;
import miralhas.github.imagestoragesystem.domain.image.model.Image;
import miralhas.github.imagestoragesystem.domain.image.service.ImageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

	private final ImageService imageService;
	private final ImageMapper imageMapper;

	@GetMapping("/{id}")
	public ResponseEntity<InputStreamResource> findById(@PathVariable UUID id) {
		Image image = imageService.findByIdOrException(id);
		MediaType imageMediaType = MediaType.parseMediaType(image.getContentType());
		return ResponseEntity.ok().contentType(imageMediaType).body(imageService.getImage(image));
	}

	@GetMapping
	public ResponseEntity<InputStreamResource> findByName(@RequestParam("fileName") String fileName) {
		Image image = imageService.findByNameOrException(fileName);
		MediaType imageMediaType = MediaType.parseMediaType(image.getContentType());
		return ResponseEntity.ok().contentType(imageMediaType).body(imageService.getImage(image));
	}

	@PutMapping("/{id}")
	public ImageDTO updateById(@PathVariable UUID id, @Valid ImageInput input) throws IOException {
		var image = imageService.findByIdOrException(id);
		var newImage = imageMapper.fromInput(input, image.getImageRelativePath()).getFirst();
		return imageMapper.toResponse(imageService.update(image, newImage));
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable UUID id) {
		imageService.delete(id);
	}

}
