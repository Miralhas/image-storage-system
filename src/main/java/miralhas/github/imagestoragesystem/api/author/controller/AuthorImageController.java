package miralhas.github.imagestoragesystem.api.author.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miralhas.github.imagestoragesystem.api.image.dto.ImageDTO;
import miralhas.github.imagestoragesystem.api.image.dto.input.ImageInput;
import miralhas.github.imagestoragesystem.api.image.mapper.ImageMapper;
import miralhas.github.imagestoragesystem.domain.author.service.AuthorService;
import miralhas.github.imagestoragesystem.domain.image.model.Image;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors/{id}/images")
public class AuthorImageController {

	private final AuthorService authorService;
	private final ImageMapper imageMapper;

	@GetMapping
	public List<ImageDTO> findAllImages(@PathVariable Long id) {
		var author = authorService.findByIdOrException(id);
		return imageMapper.toResponseCollection(authorService.findAllImages(author));
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<ImageDTO> saveImage(@PathVariable Long id, @Valid ImageInput imageInput) throws IOException {
		var book = authorService.findByIdOrException(id);
		var images = imageMapper.fromInput(imageInput, book.getImageRelativePath());
		return imageMapper.toResponseCollection(authorService.saveImage(book, images));
	}
}
