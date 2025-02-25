package junior.books.controller;

import jakarta.validation.Valid;
import junior.books.domain.Author;
import junior.books.dto.book.BookCreateRequest;
import junior.books.service.AuthorService;
import junior.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid BookCreateRequest request) {
        Author author = authorService.get(request.getAuthorId());
        bookService.crate(request, author);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }
}