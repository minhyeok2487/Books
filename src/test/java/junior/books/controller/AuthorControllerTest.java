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

import static junior.books.exhandler.constants.AuthorErrorMessage.EMAIL_ALREADY_EXISTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        perform.andExpect(status().isCreated());
        System.out.println(perform.andReturn().getResponse().getContentAsString());
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
                        .value(EMAIL_ALREADY_EXISTS + " / " + email));
        System.out.println(perform.andReturn().getResponse().getContentAsString());
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
        perform.andExpect(status().isBadRequest());
        System.out.println(perform.andReturn().getResponse().getContentAsString());
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
        perform.andExpect(status().isBadRequest());
        System.out.println(perform.andReturn().getResponse().getContentAsString());
    }
}