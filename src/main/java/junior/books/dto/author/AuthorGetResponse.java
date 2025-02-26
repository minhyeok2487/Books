package junior.books.dto.author;

import io.swagger.v3.oas.annotations.media.Schema;
import junior.books.domain.Author;
import junior.books.dto.book.BookGetAllResponse;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "특정 저자 상세 정보 반환 Response DTO")
public class AuthorGetResponse {

    @Schema(description = "저자 id", example = "0")
    private final Long id;

    @Schema(description = "저자 이름", example = "이민혁")
    private final String name;

    @Schema(description = "저자 이메일", example = "minhyeok@example.com")
    private final String email;

    @Schema(description = "연관된 책 리스트")
    private final List<BookGetAllResponse> books;

    public AuthorGetResponse(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
        this.books = author.getBooks().stream().map(BookGetAllResponse::new).toList();
    }
}
