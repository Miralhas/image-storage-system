package miralhas.github.imagestoragesystem.domain.author.repository;

import miralhas.github.imagestoragesystem.domain.author.model.Author;
import miralhas.github.imagestoragesystem.domain.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	@Query("SELECT i from Author a LEFT JOIN a.images i where a.id = :id")
	List<Image> findAllImagesById(Long id);
}