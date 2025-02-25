package junior.books.controller;

import junior.books.domain.Author;
import junior.books.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static junior.books.exhandler.constants.AuthorErrorMessage.AUTHOR_ID_NOT_FOUND;
import static junior.books.exhandler.constants.AuthorErrorMessage.EMAIL_ALREADY_EXISTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll(); // 테스트 전 데이터 초기화
    }

    @Test
    @DisplayName("저자 생성 성공")
    void create_success() throws Exception {
        //given
        String name = "minhyeok";
        String email = "minhyeok@gmail.com";

        //when
        ResultActions perform = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\", \"email\":\"" + email + "\"}"));

        //then
        perform.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("저자 생성 실패 - 중복된 email")
    void create_fail_exist_email() throws Exception {
        //given
        String name = "minhyeok";
        String email = "minhyeok@gmail.com";

        Author author = Author.builder()
                .email(email)
                .name(name)
                .books(new ArrayList<>())
                .build();
        authorRepository.save(author);

        //when
        ResultActions perform = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\", \"email\":\"" + email + "\"}"));

        //then
        perform.andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage")
                        .value(EMAIL_ALREADY_EXISTS + " / " + email))
                .andDo(print());
    }

    @Test
    @DisplayName("저자 생성 실패 - 필수 값 null")
    void create_fail_not_null() throws Exception {
        //given
        String name = "minhyeok";

        //when
        ResultActions perform = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\"}"));

        //then
        perform.andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @DisplayName("저자 생성 실패 - 필수 값 empty")
    void create_fail_not_empty() throws Exception {
        //given
        String name = "minhyeok";
        String email = "";

        //when
        ResultActions perform = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\", \"email\":\"" + email + "\"}"));

        //then
        perform.andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @DisplayName("모든 저자 목록 반환 성공")
    void get_all_success() throws Exception {
        //given
        String name = "minhyeok";
        String email = "minhyeok@gmail.com";

        Author author = Author.builder()
                .email(email)
                .name(name)
                .books(new ArrayList<>())
                .build();
        authorRepository.save(author);

        String name2 = "minhyeok123";
        String email2 = "minhyeok123@gmail.com";

        Author author2 = Author.builder()
                .email(email2)
                .name(name2)
                .books(new ArrayList<>())
                .build();
        authorRepository.save(author2);

        //when
        ResultActions perform = mockMvc.perform(get("/authors")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].email").value(email))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].email").value(email2))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 저자 상세 정보 반환 성공")
    void get_success() throws Exception {
        //given
        String name = "minhyeok";
        String email = "minhyeok@gmail.com";
        Author author = Author.builder()
                .email(email)
                .name(name)
                .books(new ArrayList<>())
                .build();
        authorRepository.save(author);

        //when
        ResultActions perform = mockMvc.perform(get("/authors/"+ author.getId()));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.email").value(email))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 저자 상세 정보 반환 실패 - 없는 ID")
    void get_fall_not_exist() throws Exception {
        //given
        String name = "minhyeok";
        String email = "minhyeok@gmail.com";
        Author author = Author.builder()
                .email(email)
                .name(name)
                .books(new ArrayList<>())
                .build();
        authorRepository.save(author);

        //when
        ResultActions perform = mockMvc.perform(get("/authors/"+ author.getId()+1));

        //then
        perform.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage")
                        .value(AUTHOR_ID_NOT_FOUND))
                .andDo(print());
    }

    @Test
    @DisplayName("저자 정보 수정 성공")
    void update_success() throws Exception {
        //given
        String name = "minhyeok";
        String email = "minhyeok@gmail.com";
        Author author = Author.builder()
                .email(email)
                .name(name)
                .books(new ArrayList<>())
                .build();
        authorRepository.save(author);

        String reName = "minhyeok123";
        String reEmail = "minhyeok123@gmail.com";

        //when
        ResultActions perform = mockMvc.perform(put("/authors/"+ author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + reName + "\", \"email\":\"" + reEmail + "\"}"));
        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author.getId()))
                .andExpect(jsonPath("$.name").value(reName))
                .andExpect(jsonPath("$.email").value(reEmail))
                .andDo(print());
    }
}