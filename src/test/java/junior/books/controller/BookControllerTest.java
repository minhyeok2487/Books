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
    @DisplayName("도서 생성 실패 - 필수 값 null")
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
    @DisplayName("도서 생성 실패 - 필수 값 empty")
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

    @Test
    @DisplayName("모든 도서 목록 반환 성공")
    void get_all_success() throws Exception {
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

        String title2 = "books";
        String description2 = "books description";
        String isbn2 = "9876543210";
        LocalDate publicationDate2 = LocalDate.of(2020, 1, 1);

        Book book2 = Book.builder()
                .title(title2)
                .description(description2)
                .isbn(isbn2)
                .publicationDate(publicationDate2)
                .author(author)
                .build();
        bookRepository.save(book2);

        //when
        ResultActions perform = mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[1].title").value(title2))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 도서 상세 정보 반환 성공")
    void get_success() throws Exception {
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
        ResultActions perform = mockMvc.perform(get("/books/"+ book.getId()));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andDo(print());
    }

    @Test
    @DisplayName("도서 정보 수정 성공")
    void update_success() throws Exception {
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

        String reTitle = "minhyeok123";

        //when
        //JSON content 생성
        String content = String.format(
                "{\"title\":\"%s\",\"description\":\"%s\",\"isbn\":\"%s\",\"publicationDate\":\"%s\",\"authorId\":\"%s\"}",
                reTitle, description, isbn, publicationDate, author.getId()
        );

        ResultActions perform = mockMvc.perform(put("/books/"+ book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(reTitle))
                .andExpect(jsonPath("$.description").value(description))
                .andDo(print());
    }

    @Test
    @DisplayName("도서 정보 수정 실패 - 이미 존재 하는 ISBN")
    void update_fall_exist_isbn() throws Exception {
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

        String title2 = "books";
        String description2 = "books description";
        String isbn2 = "9876543210";
        LocalDate publicationDate2 = LocalDate.of(2020, 1, 1);

        Book book2 = Book.builder()
                .title(title2)
                .description(description2)
                .isbn(isbn2)
                .publicationDate(publicationDate2)
                .author(author)
                .build();
        bookRepository.save(book2);

        String reTitle = "minhyeok123";

        //when
        //JSON content 생성
        String content = String.format(
                "{\"title\":\"%s\",\"description\":\"%s\",\"isbn\":\"%s\",\"publicationDate\":\"%s\",\"authorId\":\"%s\"}",
                reTitle, description, isbn2, publicationDate, author.getId()
        );

        ResultActions perform = mockMvc.perform(put("/books/"+ book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage")
                        .value(BOOK_ISBN_ALREADY_EXISTS + " / " + isbn2))
                .andDo(print());
    }

}