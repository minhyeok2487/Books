package junior.books.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(description = "도서 정보 수정 Request DTO")
public class BookUpdateRequest {

    @Schema(description = "도서 제목", example = "테스트북")
    private String title;

    @Schema(description = "도서 설명", example = "테스트북")
    private String description;

    @Schema(description = "국제 표준 도서번호", example = "1234567890")
    private String isbn;

    @Schema(description = "출판일", example = "2024-02-27")
    private LocalDate publicationDate;
}
