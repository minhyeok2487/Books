package junior.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import junior.books.domain.Author;
import junior.books.dto.book.*;
import junior.books.exhandler.codes.ErrorCodeGroup;
import junior.books.service.AuthorService;
import junior.books.service.BookService;
import junior.books.utils.ApiErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static junior.books.exhandler.codes.ErrorCode.*;

@Tag(name = "도서 API", description = "도서와 관련한 CRUD API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @PostMapping("")
    @Operation(summary = "도서 생성", description = "요청 값들을 받아 도서를 생성합니다. 저자가 이미 등록되어 있어야합니다.")
    @ApiResponse(responseCode = "201", description = "저자 생성 성공")
    @ApiErrorCodes(codes = {AUTHOR_ID_NOT_FOUND, BOOK_ISBN_ALREADY_EXISTS}, groups = {ErrorCodeGroup.ISBN_ERRORS})
    public ResponseEntity<?> create(@RequestBody @Valid BookCreateRequest request) {
        Author author = authorService.get(request.getAuthorId());
        bookService.crate(request, author);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "모든 도서 목록 반환")
    @ApiResponse(
            responseCode = "200",
            description = "도서 목록 반환 성공",
            content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = BookGetAllResponse.class)
                    )
            )
    )
    public ResponseEntity<List<BookGetAllResponse>> getAll() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 도서의 상세 정보를 반환")
    @ApiResponse(
            responseCode = "200",
            description = "특정 도서의 상세 정보를 반환 성공",
            content = @Content(
                    schema = @Schema(implementation = BookGetResponse.class)
            )
    )
    @ApiErrorCodes(codes = {BOOK_ID_NOT_FOUND})
    public ResponseEntity<BookGetResponse> get(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookResponse(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "요청 본문을 통해 도서 정보 수정")
    @ApiResponse(
            responseCode = "200",
            description = "특정 저자의 도서 정보를 반환 성공",
            content = @Content(
                    schema = @Schema(implementation = BookUpdateResponse.class)
            )
    )
    @ApiErrorCodes(codes = {BOOK_ID_NOT_FOUND, BOOK_ISBN_ALREADY_EXISTS}, groups = {ErrorCodeGroup.ISBN_ERRORS})
    public ResponseEntity<BookUpdateResponse> update(@PathVariable Long id,
                                                     @RequestBody BookUpdateRequest request) {
        return new ResponseEntity<>(bookService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "도서 삭제")
    @ApiResponse(responseCode = "200", description = "저자 삭제 성공")
    @ApiErrorCodes(codes = {BOOK_ID_NOT_FOUND})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}