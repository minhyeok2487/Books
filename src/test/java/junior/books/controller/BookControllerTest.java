package junior.books.controller;

import junior.books.domain.Author;
import junior.books.domain.Book;
import junior.books.repository.AuthorRepository;
import junior.books.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;

import static junior.books.exhandler.constants.BookErrorMessage.BOOK_ISBN_ALREADY_EXISTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    void setUp() {
        // 테스트 전 데이터 초기화
        authorRepository.deleteAll();
        bookRepository.deleteAll();

        String name = "minhyeok";
        String email = "minhyeok@gmail.com";
        author = Author.builder()
                .email(email)
                .name(name)
                .books(new ArrayList<>())
                .build();
        authorRepository.save(author);
    }

    @Test
    @DisplayName("도서 생성 성공")
    void create_success() throws Exception {
        // given
        String title = "books";
        String description = "books description";
        String isbn = "1234567890";
        LocalDate publicationDate = LocalDate.of(2020, 1, 1);

        // when
        // JSON content 생성
        String content = String.format(
                "{\"title\":\"%s\",\"description\":\"%s\",\"isbn\":\"%s\",\"publicationDate\":\"%s\",\"authorId\":\"%s\"}",
                title, description, isbn, publicationDate, author.getId()
        );

        ResultActions perform = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        perform.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("도서 생성 실패 - 중복된 isbn")
    void create_fail_isbn_email() throws Exception {
        //given
        String title = "books";
        String description = "books description";
        String isbn = "1234567890";
        LocalDate publicationDate = LocalDate.of(2020, 1, 1);

        Book book = Book.builder()
                .title(title)
                .description(description)
                .isbn(isbn)
                .publicationDate(publicationDate)
                .author(author)
                .build();
        bookRepository.save(book);

        //when
        //JSON content 생성
        String content = String.format(
                "{\"title\":\"%s\",\"description\":\"%s\",\"isbn\":\"%s\",\"publicationDate\":\"%s\",\"authorId\":\"%s\"}",
                title, description, isbn, publicationDate, author.getId()
        );

        ResultActions perform = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage")
                        .value(BOOK_ISBN_ALREADY_EXISTS + " / " + isbn))
                .andDo(print());
    }

    @Test
    @DisplayName("저자 생성 실패 - 필수 값 null")
    void create_fail_not_null() throws Exception {
        //given
        String description = "books description";
        String isbn = "1234567890";
        LocalDate publicationDate = LocalDate.of(2020, 1, 1);

        //when
        //JSON content 생성
        String content = String.format(
                "{\"description\":\"%s\",\"isbn\":\"%s\",\"publicationDate\":\"%s\",\"authorId\":\"%s\"}",
                description, isbn, publicationDate, author.getId()
        );

        ResultActions perform = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @DisplayName("저자 생성 실패 - 필수 값 empty")
    void create_fail_not_empty() throws Exception {
        //given
        String title = "";
        String description = "books description";
        String isbn = "1234567890";
        LocalDate publicationDate = LocalDate.of(2020, 1, 1);

        //when
        //JSON content 생성
        String content = String.format(
                "{\"title\":\"%s\",\"description\":\"%s\",\"isbn\":\"%s\",\"publicationDate\":\"%s\",\"authorId\":\"%s\"}",
                title, description, isbn, publicationDate, author.getId()
        );

        ResultActions perform = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isBadRequest()).andDo(print());
    }
}