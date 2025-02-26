package junior.books.dto.author;

import io.swagger.v3.oas.annotations.media.Schema;
import junior.books.domain.Author;
import lombok.Getter;

@Getter
@Schema(description = "저자 목록 반환 Response DTO")
public class AuthorGetAllResponse {

    @Schema(description = "저자 id", example = "0")
    private final Long id;

    @Schema(description = "저자 이름", example = "이민혁")
    private final String name;

    @Schema(description = "저자 이메일", example = "minhyeok@example.com")
    private final String email;

    public AuthorGetAllResponse(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
    }
}
