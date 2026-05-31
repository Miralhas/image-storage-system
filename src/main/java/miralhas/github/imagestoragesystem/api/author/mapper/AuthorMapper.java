package miralhas.github.imagestoragesystem.api.author.mapper;

import miralhas.github.imagestoragesystem.api.author.dto.AuthorDTO;
import miralhas.github.imagestoragesystem.api.author.dto.input.AuthorInput;
import miralhas.github.imagestoragesystem.api.image.mapper.ImageMapper;
import miralhas.github.imagestoragesystem.domain.author.model.Author;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = IGNORE,
		nullValueCheckStrategy = ALWAYS,
		uses = {ImageMapper.class}
)
public interface AuthorMapper {
	Author fromInput(AuthorInput input);
	AuthorDTO toResponse(Author author);
}
