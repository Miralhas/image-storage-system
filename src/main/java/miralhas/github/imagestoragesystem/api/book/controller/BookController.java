package miralhas.github.imagestoragesystem.api.book.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miralhas.github.imagestoragesystem.api.book.dto.BookDTO;
import miralhas.github.imagestoragesystem.api.book.dto.BookSummaryDTO;
import miralhas.github.imagestoragesystem.api.book.dto.input.BookInput;
import miralhas.github.imagestoragesystem.api.book.mapper.BookMapper;
import miralhas.github.imagestoragesystem.domain.book.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

	private final BookMapper bookMapper;
	private final BookService bookService;

	@GetMapping
	public List<BookSummaryDTO> findAll() {
		return bookMapper.toSummaryCollectionResponse(bookService.findAll());
	}

	@GetMapping("/{id}")
	public BookDTO findBySlug(@PathVariable Long id) {
		return bookMapper.toResponse(bookService.findByIdOrException(id));
	}

	@PostMapping
	public BookDTO save(@RequestBody @Valid BookInput input) {
		return bookMapper.toResponse(bookService.save(input));
	}
}
