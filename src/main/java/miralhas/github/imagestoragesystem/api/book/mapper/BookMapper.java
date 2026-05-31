package miralhas.github.imagestoragesystem.api.book.mapper;

import miralhas.github.imagestoragesystem.api.book.dto.BookDTO;
import miralhas.github.imagestoragesystem.api.book.dto.BookSummaryDTO;
import miralhas.github.imagestoragesystem.api.book.dto.input.BookInput;
import miralhas.github.imagestoragesystem.api.image.mapper.ImageMapper;
import miralhas.github.imagestoragesystem.domain.book.model.Book;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = IGNORE,
		nullValueCheckStrategy = ALWAYS,
		uses = {ImageMapper.class}
)
public interface BookMapper {
	Book fromInput(BookInput input);
	BookDTO toResponse(Book book);
	List<BookSummaryDTO> toSummaryCollectionResponse(List<Book> book);
}
