package junior.books.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import junior.books.domain.Book;
import lombok.Getter;

@Getter
@Schema(description = "도서 목록 반환 Response DTO")
public class BookGetAllResponse {

    @Schema(description = "도서 id", example = "0")
    private final Long id;

    @Schema(description = "도서 제목", example = "테스트북")
    private final String title;

    @Schema(description = "저자 이름", example = "이민혁")
    private final String name;

    public BookGetAllResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.name = book.getAuthor().getName();
    }
}