package junior.books.dto.author;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CreateAuthorRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;
}
