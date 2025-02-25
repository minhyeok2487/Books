package junior.books.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    }
}