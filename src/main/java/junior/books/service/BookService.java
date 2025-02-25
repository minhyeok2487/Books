package junior.books.service;

import jakarta.persistence.EntityNotFoundException;
import junior.books.domain.Author;
import junior.books.domain.Book;
import junior.books.dto.book.BookCreateRequest;
import junior.books.dto.book.BookGetAllResponse;
import junior.books.dto.book.BookGetResponse;
import junior.books.exhandler.exceptions.ConflictException;
import junior.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static junior.books.exhandler.constants.BookErrorMessage.BOOK_ID_NOT_FOUND;
import static junior.books.exhandler.constants.BookErrorMessage.BOOK_ISBN_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    @Transactional(readOnly = true)
    public Book get(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(BOOK_ID_NOT_FOUND));
    }

    @Transactional
    public void crate(BookCreateRequest request, Author author) {
        Optional<Book> exist = repository.findByIsbn(request.getIsbn());
        validateCreateRequest(exist);
        Book build = Book.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .isbn(request.getIsbn())
                .publicationDate(request.getPublicationDate())
                .author(author)
                .build();
        repository.save(build);
    }

    private void validateCreateRequest(Optional<Book> exist) {
        if (exist.isPresent()) {
            throw new ConflictException(BOOK_ISBN_ALREADY_EXISTS, exist.get().getIsbn());
        }
    }

    @Transactional(readOnly = true)
    public List<BookGetAllResponse> getAll() {
        return repository.findAll().stream().map(BookGetAllResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public BookGetResponse getBookResponse(Long id) {
        Book book = get(id);
        return new BookGetResponse(book);
    }
}
