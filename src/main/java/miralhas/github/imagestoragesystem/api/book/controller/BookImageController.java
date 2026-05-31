package miralhas.github.imagestoragesystem.api.book.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miralhas.github.imagestoragesystem.api.image.dto.ImageDTO;
import miralhas.github.imagestoragesystem.api.image.dto.input.ImageInput;
import miralhas.github.imagestoragesystem.api.image.mapper.ImageMapper;
import miralhas.github.imagestoragesystem.domain.book.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books/{id}/images")
public class BookImageController {

	private final BookService bookService;
	private final ImageMapper imageMapper;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ImageDTO> findAllImages(@PathVariable Long id) {
		var book = bookService.findByIdOrException(id);
		return imageMapper.toResponseCollection(bookService.findAllImages(book));
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<ImageDTO> saveImage(@PathVariable Long id, @Valid ImageInput imageInput) throws IOException {
		var book = bookService.findByIdOrException(id);
		var images = imageMapper.fromInput(imageInput, book.getImageRelativePath());
		return imageMapper.toResponseCollection(bookService.saveImage(book, images));
	}

}
