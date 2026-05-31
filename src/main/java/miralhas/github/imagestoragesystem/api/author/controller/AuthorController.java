package miralhas.github.imagestoragesystem.api.author.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miralhas.github.imagestoragesystem.api.author.dto.AuthorDTO;
import miralhas.github.imagestoragesystem.api.author.dto.input.AuthorInput;
import miralhas.github.imagestoragesystem.api.author.mapper.AuthorMapper;
import miralhas.github.imagestoragesystem.domain.author.service.AuthorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
	private final AuthorService authorService;
	private final AuthorMapper authorMapper;

	@GetMapping("/{id}")
	public AuthorDTO findById(@PathVariable Long id) {
		return authorMapper.toResponse(authorService.findByIdOrException(id));
	}

	@PostMapping
	public AuthorDTO save(@RequestBody @Valid AuthorInput input) {
		return authorMapper.toResponse(authorService.save(input));
	}

}
