package junior.books.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import junior.books.domain.Book;
import junior.books.dto.author.AuthorGetResponse;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(description = "특정 도서의 상세 정보를 반환 Response DTO")
public class BookGetResponse {

    @Schema(description = "도서 id", example = "0")
    private final Long id;

    @Schema(description = "도서 제목", example = "테스트북")
    private final String title;

    @Schema(description = "도서 설명", example = "테스트북")
    private final String description;

    @Schema(description = "국제 표준 도서번호", example = "1234567890")
    private final String isbn;

    @Schema(description = "출판일", example = "2024-02-27")
    private final LocalDate publicationDate;

    @Schema(description = "책의 저자")
    private final AuthorGetResponse authorGetResponse;

    public BookGetResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.isbn = book.getIsbn();
        this.publicationDate = book.getPublicationDate();
        this.authorGetResponse = new AuthorGetResponse(book.getAuthor());
    }
}