package junior.books.dto.author;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "저자 정보 수정 Request DTO")
public class AuthorUpdateRequest {

    @Schema(description = "저자 이름", example = "이민혁")
    private String name;

    @Schema(description = "저자 이메일", example = "minhyeok@example.com")
    private String email;
}
