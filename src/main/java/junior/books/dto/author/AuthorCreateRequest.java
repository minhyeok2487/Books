package junior.books.dto.author;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Schema(description = "저자 생성 Request DTO")
public class AuthorCreateRequest {

    @NotEmpty(message = "이름은 필수입니다.")
    @Schema(description = "저자 이름", example = "이민혁")
    private String name;

    @NotEmpty(message = "이메일은 필수입니다.")
    @Schema(description = "저자 이메일", example = "minhyeok@example.com")
    private String email;
}
