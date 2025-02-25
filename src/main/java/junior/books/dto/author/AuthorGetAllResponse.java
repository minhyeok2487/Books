package junior.books.dto.author;

import junior.books.domain.Author;
import lombok.Getter;

@Getter
public class AuthorGetAllResponse {

    private final Long id;

    private final String name;

    private final String email;

    public AuthorGetAllResponse(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
    }
}
