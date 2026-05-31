package miralhas.github.imagestoragesystem.domain.author.service;

import lombok.RequiredArgsConstructor;
import miralhas.github.imagestoragesystem.api.author.dto.input.AuthorInput;
import miralhas.github.imagestoragesystem.api.author.mapper.AuthorMapper;
import miralhas.github.imagestoragesystem.api.image.dto.NewImage;
import miralhas.github.imagestoragesystem.domain.author.model.Author;
import miralhas.github.imagestoragesystem.domain.author.repository.AuthorRepository;
import miralhas.github.imagestoragesystem.domain.image.model.Image;
import miralhas.github.imagestoragesystem.domain.image.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorService {

	private final AuthorRepository authorRepository;
	private final ImageService imageService;
	private final AuthorMapper authorMapper;

	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	public Author findByIdOrException(Long id) {
		return authorRepository.findById(id).orElseThrow(RuntimeException::new);
	}

	public List<Image> findAllImages(Author author) {
		return authorRepository.findAllImagesById(author.getId());
	}

	@Transactional
	public Author save(AuthorInput input) {
		var author = authorMapper.fromInput(input);
		author.generateSlug();
		return authorRepository.save(author);
	}

	@Transactional
	public List<Image> saveImage(Author author, List<NewImage> newImages) {
		var images = imageService.save(newImages);
		author.setImages(images);
		authorRepository.save(author);
		return images;
	}

}
