package junior.books.dto.book;

import junior.books.domain.Book;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookUpdateResponse {

    private final Long id;

    private final String title;

    private final String description;

    private final String isbn;

    private final LocalDate publicationDate;

    public BookUpdateResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.isbn = book.getIsbn();
        this.publicationDate = book.getPublicationDate();
    }
}