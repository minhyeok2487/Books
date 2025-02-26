package junior.books.exhandler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "에러 응답 객체")
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "400")
    private int errorCode;

    @Schema(description = "에러 이름", example = "ConflictException")
    private String errorName;

    @Schema(description = "에러 메시지", example = "잘못된 요청 데이터입니다.")
    private String errorMessage;

    public static ErrorResponse of(int errorCode, String errorName, String errorMessage) {
        return new ErrorResponse(errorCode, errorName, errorMessage);
    }
}