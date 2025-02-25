package junior.books.dto.book;

import junior.books.domain.Book;
import lombok.Getter;

@Getter
public class BookGetAllResponse {

    private final Long id;

    private final String title;

    public BookGetAllResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
    }
}