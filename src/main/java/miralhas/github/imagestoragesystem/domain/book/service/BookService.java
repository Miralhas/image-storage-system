package miralhas.github.imagestoragesystem.domain.book.service;

import lombok.RequiredArgsConstructor;
import miralhas.github.imagestoragesystem.api.book.dto.input.BookInput;
import miralhas.github.imagestoragesystem.api.book.mapper.BookMapper;
import miralhas.github.imagestoragesystem.api.image.dto.NewImage;
import miralhas.github.imagestoragesystem.api.image.mapper.ImageMapper;
import miralhas.github.imagestoragesystem.domain.book.exception.BookNotFoundException;
import miralhas.github.imagestoragesystem.domain.book.model.Book;
import miralhas.github.imagestoragesystem.domain.book.repository.BookRepository;
import miralhas.github.imagestoragesystem.domain.image.model.Image;
import miralhas.github.imagestoragesystem.domain.image.service.ImageService;
import miralhas.github.imagestoragesystem.shared.interfaces.MessageResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
	private final BookRepository bookRepository;
	private final MessageResolver messageResolver;
	private final BookMapper bookMapper;
	private final ImageService imageService;
	private final ImageMapper imageMapper;

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public List<Image> findAllImages(Book book) {
		return bookRepository.findAllImagesById(book.getId());
	}

	public Book findByIdOrException(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(
				messageResolver.get("book.id.notFound", id)
		));
	}

	public Book findBySlugOrException(String slug) {
		return bookRepository.findBySlug(slug).orElseThrow(() -> new BookNotFoundException(
				messageResolver.get("book.slug.notFound", slug)
		));
	}

	@Transactional
	public Book save(BookInput input) {
		var book = bookMapper.fromInput(input);
		book.generateSlug();
		return bookRepository.save(book);
	}

	@Transactional
	public List<Image> saveImage(Book book, List<NewImage> newImages) {
		var images = imageService.save(newImages);
		book.addImages(images);
		bookRepository.save(book);
		return images;
	}
}
