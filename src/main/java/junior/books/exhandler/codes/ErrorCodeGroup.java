package junior.books.exhandler.codes;

import lombok.Getter;

import static junior.books.exhandler.codes.ErrorCode.*;

@Getter
public enum ErrorCodeGroup {

    ISBN_ERRORS(
            INVALID_ISBN_LENGTH,
            INVALID_ISBN_FORMAT,
            INVALID_ISBN_COUNTRY_CODE,
            INVALID_ISBN_CHECK_DIGIT
    );

    private final ErrorCode[] errorCodes;

    ErrorCodeGroup(ErrorCode... errorCodes) {
        this.errorCodes = errorCodes;
    }
}
