package junior.books.dto.book;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookUpdateRequest {

    private String title;

    private String description;

    private String isbn;

    private LocalDate publicationDate;
}
