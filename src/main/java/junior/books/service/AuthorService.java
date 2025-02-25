package junior.books.service;

import junior.books.domain.Author;
import junior.books.dto.author.AuthorCreateRequest;
import junior.books.dto.author.AuthorGetAllResponse;
import junior.books.dto.author.AuthorUpdateRequest;
import junior.books.exhandler.exceptions.ConflictException;
import junior.books.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junior.books.exhandler.constants.AuthorErrorMessage.EMAIL_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

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
    public void get(Long id) {

    }

    @Transactional
    public void update(Long id, AuthorUpdateRequest request) {

    }

    @Transactional
    public void delete(Long id) {

    }
}
