package junior.books.service;

import junior.books.domain.Author;
import junior.books.domain.Book;
import junior.books.dto.book.*;
import junior.books.exhandler.ErrorCode;
import junior.books.exhandler.GlobalException;
import junior.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static junior.books.utils.ValidateMethods.validateIsbn;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    @Transactional(readOnly = true)
    public Book get(Long id) {
        return repository.findById(id).orElseThrow(() -> new GlobalException(ErrorCode.BOOK_ID_NOT_FOUND));
    }

    @Transactional
    public void crate(BookCreateRequest request, Author author) {
        validateCreate(request.getIsbn());
        Book build = Book.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .isbn(request.getIsbn())
                .publicationDate(request.getPublicationDate())
                .author(author)
                .build();
        repository.save(build);
    }

    private void validateCreate(String isbn) {
        validateIsbn(isbn);
        if (repository.findByIsbn(isbn).isPresent()) {
            throw new GlobalException(ErrorCode.BOOK_ISBN_ALREADY_EXISTS);
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

    @Transactional
    public BookUpdateResponse update(Long id, BookUpdateRequest request) {
        Book book = get(id);
        validateUpdate(book, request);
        book.update(request);
        return new BookUpdateResponse(book);
    }

    private void validateUpdate(Book book, BookUpdateRequest request) {
        validateIsbn(request.getIsbn());
        Optional<Book> exist = repository.findByIsbn(request.getIsbn());
        if (exist.isPresent()) {
            if (!exist.get().getId().equals(book.getId()) && exist.get().getIsbn().equals(request.getIsbn())) {
                throw new GlobalException(ErrorCode.BOOK_ISBN_ALREADY_EXISTS);
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        Book book = get(id);
        repository.delete(book);
    }
}
