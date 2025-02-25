package junior.books.service;

import junior.books.dto.author.CreateAuthorRequest;
import junior.books.dto.author.UpdateAuthorRequest;
import junior.books.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    @Transactional
    public void crate(CreateAuthorRequest request) {

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
