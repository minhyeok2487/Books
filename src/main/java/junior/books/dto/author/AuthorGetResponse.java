package junior.books.dto.author;

import junior.books.domain.Author;
import junior.books.dto.book.BookGetAllResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class AuthorGetResponse {

    private final Long id;

    private final String name;

    private final String email;

    private final List<BookGetAllResponse> books;

    public AuthorGetResponse(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
        this.books = author.getBooks().stream().map(BookGetAllResponse::new).toList();
    }
}
