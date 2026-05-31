package miralhas.github.imagestoragesystem.domain.book.repository;

import miralhas.github.imagestoragesystem.domain.book.model.Book;
import miralhas.github.imagestoragesystem.domain.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
	@Query("SELECT b FROM Book b WHERE b.slug = :slug")
	Optional<Book> findBySlug(String slug);

	@Query("SELECT i from Book b LEFT JOIN b.images i where b.id = :id")
	List<Image> findAllImagesById(Long id);
}