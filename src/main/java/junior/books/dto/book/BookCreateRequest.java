package junior.books.dto.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookCreateRequest {

    @NotEmpty
    private String title;

    private String description;

    @NotEmpty
    private String isbn;

    private LocalDate publicationDate;

    @NotNull
    private Long authorId;
}
