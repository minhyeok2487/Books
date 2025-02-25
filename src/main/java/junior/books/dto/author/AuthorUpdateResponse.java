package junior.books.dto.author;

import junior.books.domain.Author;
import lombok.Getter;

@Getter
public class AuthorUpdateResponse {

    private final Long id;

    private final String name;

    private final String email;

    public AuthorUpdateResponse(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
    }
}