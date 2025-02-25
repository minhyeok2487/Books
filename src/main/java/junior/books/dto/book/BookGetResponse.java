package junior.books.dto.book;

import junior.books.domain.Book;
import junior.books.dto.author.AuthorGetResponse;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookGetResponse {

    private final Long id;

    private final String title;

    private final String description;

    private final String isbn;

    private final LocalDate publicationDate;

    private final AuthorGetResponse authorGetResponse;

    public BookGetResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.isbn = book.getIsbn();
        this.publicationDate = book.getPublicationDate();
        this.authorGetResponse = new AuthorGetResponse(book.getAuthor());
    }
}