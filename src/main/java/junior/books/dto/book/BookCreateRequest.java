package junior.books.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(description = "도서 생성 Request DTO")
public class BookCreateRequest {

    @NotEmpty(message = "제목은 필수입니다.")
    @Schema(description = "도서 제목", example = "테스트북")
    private String title;

    @Schema(description = "도서 설명", example = "테스트북")
    private String description;

    @NotEmpty(message = "국제 표준 도서번호는 필수입니다.")
    @Schema(description = "국제 표준 도서번호", example = "1234567890")
    private String isbn;

    @Schema(description = "출판일", example = "2024-02-27")
    private LocalDate publicationDate;

    @NotNull(message = "도서 저자는 필수입니다.")
    @Schema(description = "도서 저자 id", example = "1")
    private Long authorId;
}
