package junior.books.exhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    OK(HttpStatus.OK, "응답 성공"),

    //Author
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "가입된 이메일 입니다."),
    AUTHOR_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 저자 ID 입니다."),
    AUTHOR_DELETION_BLOCKED_BY_BOOKS(HttpStatus.BAD_REQUEST, "연관 도서가 있기 때문에 저자를 삭제할 수 없습니다."),

    //Book
    BOOK_ISBN_ALREADY_EXISTS(HttpStatus.CONFLICT,"중복된 ISBN 입니다."),
    BOOK_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 도서 ID 입니다."),

    //Validate
    INVALID_ISBN_LENGTH(HttpStatus.NOT_FOUND, "ISBN은 정확히 10자리여야 합니다."),
    INVALID_ISBN_FORMAT(HttpStatus.NOT_FOUND, "ISBN은 숫자로만 구성되어야 합니다."),
    INVALID_ISBN_COUNTRY_CODE(HttpStatus.NOT_FOUND, "국가/언어 식별 번호는 10에서 90 사이여야 합니다."),
    INVALID_ISBN_CHECK_DIGIT(HttpStatus.NOT_FOUND, "체크 디지트(마지막 자리)는 0이어야 합니다.");

    private final HttpStatus status;
    private final String message;
}
