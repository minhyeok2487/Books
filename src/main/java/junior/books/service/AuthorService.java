package junior.books.service;

import junior.books.domain.Author;
import junior.books.dto.author.CreateAuthorRequest;
import junior.books.dto.author.UpdateAuthorRequest;
import junior.books.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    @Transactional
    public void crate(CreateAuthorRequest request) {
        Optional<Author> exist = repository.findByEmail(request.getEmail());
        if (exist.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        } else {
            Author build = Author.builder()
                    .email(request.getEmail())
                    .name(request.getName())
                    .books(new ArrayList<>())
                    .build();
            repository.save(build);
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
