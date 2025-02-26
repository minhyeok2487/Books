package junior.books.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Schema(description = "도서 목록 필터링 Request DTO")
public class BookGetAllRequest {

    @Schema(description = "도서 제목", example = "테스트북")
    private String title;

    @Schema(description = "출판일 이후", example = "2024-02-27")
    private LocalDate afterPublicationDate;

    @Schema(description = "저자 이름", example = "이민혁")
    private String authorName;
}