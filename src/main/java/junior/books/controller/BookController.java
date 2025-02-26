package junior.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import junior.books.domain.Author;
import junior.books.dto.book.BookCreateRequest;
import junior.books.dto.book.BookUpdateRequest;
import junior.books.service.AuthorService;
import junior.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "도서 API", description = "도서와 관련한 CRUD API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @PostMapping("")
    @Operation(summary = "도서 생성", description = "요청 값들을 받아 도서를 생성합니다. 저자가 이미 등록되어 있어야합니다.")
    public ResponseEntity<?> create(@RequestBody @Valid BookCreateRequest request) {
        Author author = authorService.get(request.getAuthorId());
        bookService.crate(request, author);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookResponse(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody BookUpdateRequest request) {
        return new ResponseEntity<>(bookService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}