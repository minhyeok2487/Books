package junior.books.service;

import junior.books.domain.Author;
import junior.books.dto.author.CreateAuthorRequest;
import junior.books.dto.author.UpdateAuthorRequest;
import junior.books.exhandler.exceptions.ConflictException;
import junior.books.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static junior.books.exhandler.constants.AuthorErrorMessage.EMAIL_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    @Transactional
    public void crate(CreateAuthorRequest request) {
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
    public void getAll() {
    }

    @Transactional(readOnly = true)
    public void get(Long id) {

    }

    @Transactional
    public void update(Long id, UpdateAuthorRequest request) {

    }

    @Transactional
    public void delete(Long id) {

    }
}
