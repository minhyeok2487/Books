package junior.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import junior.books.dto.author.*;
import junior.books.service.AuthorService;
import junior.books.utils.ApiErrorCodeExamples;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static junior.books.exhandler.ErrorCode.*;


@Tag(name = "저자 API", description = "저자와 관련한 CRUD API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("")
    @Operation(summary = "저자 생성")
    @ApiResponse(responseCode = "201", description = "저자 생성 성공")
    @ApiErrorCodeExamples({EMAIL_ALREADY_EXISTS})
    public ResponseEntity<Void> create(@RequestBody @Valid AuthorCreateRequest request) {
        authorService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "모든 저자 목록 반환")
    @ApiResponse(
            responseCode = "200",
            description = "저자 목록 반환 성공",
            content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = AuthorGetAllResponse.class)
                    )
            )
    )
    public ResponseEntity<List<AuthorGetAllResponse>> getAll() {
        return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 저자의 상세 정보를 반환")
    @ApiResponse(
            responseCode = "200",
            description = "특정 저자의 상세 정보를 반환 성공",
            content = @Content(
                    schema = @Schema(implementation = AuthorGetResponse.class)
            )
    )
    @ApiErrorCodeExamples({AUTHOR_ID_NOT_FOUND})
    public ResponseEntity<AuthorGetResponse> get(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.getAuthorResponse(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "요청 본문을 통해 저자 정보 수정")
    @ApiResponse(
            responseCode = "200",
            description = "특정 저자의 상세 정보를 반환 성공",
            content = @Content(
                    schema = @Schema(implementation = AuthorUpdateResponse.class)
            )
    )
    @ApiErrorCodeExamples({AUTHOR_ID_NOT_FOUND})
    public ResponseEntity<AuthorUpdateResponse> update(@PathVariable Long id,
                                                       @RequestBody AuthorUpdateRequest request) {
        return new ResponseEntity<>(authorService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "저자를 삭제", description = "연관 도서 있으면 삭제 불가")
    @ApiResponse(responseCode = "200", description = "저자 삭제 성공")
    @ApiErrorCodeExamples({AUTHOR_ID_NOT_FOUND, AUTHOR_DELETION_BLOCKED_BY_BOOKS})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
