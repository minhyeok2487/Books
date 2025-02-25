package junior.books.service;

import jakarta.persistence.EntityNotFoundException;
import junior.books.domain.Author;
import junior.books.dto.author.*;
import junior.books.exhandler.exceptions.ConflictException;
import junior.books.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junior.books.exhandler.constants.AuthorErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    @Transactional(readOnly = true)
    public Author get(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(AUTHOR_ID_NOT_FOUND));
    }

    @Transactional
    public void crate(AuthorCreateRequest request) {
        Optional<Author> exist = repository.findByEmail(request.getEmail());
        validateCreateRequest(exist);
        Author build = Author.builder()
                .email(request.getEmail())
                .name(request.getName())
                .books(new ArrayList<>())
                .build();
        repository.save(build);
    }

    private void validateCreateRequest(Optional<Author> exist) {
        if (exist.isPresent()) {
            throw new ConflictException(EMAIL_ALREADY_EXISTS, exist.get().getEmail());
        }
    }

    @Transactional(readOnly = true)
    public List<AuthorGetAllResponse> getAll() {
        return repository.findAll().stream().map(AuthorGetAllResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public AuthorGetResponse getAuthorResponse(Long id) {
        return new AuthorGetResponse(get(id));
    }

    @Transactional
    public AuthorUpdateResponse update(Long id, AuthorUpdateRequest request) {
        Author author = get(id);
        author.update(request);
        return new AuthorUpdateResponse(author);
    }

    @Transactional
    public void delete(Long id) {
        Author author = get(id);
        validateDeleteRequest(author);
        repository.delete(author);
    }

    private void validateDeleteRequest(Author author) {
        if (!author.getBooks().isEmpty()) {
            throw new IllegalStateException(AUTHOR_DELETION_BLOCKED_BY_BOOKS);
        }
    }
}
