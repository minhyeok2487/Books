package junior.books.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static junior.books.exhandler.constants.ValidateErrorMessage.*;
import static junior.books.utils.ValidateMethods.validateIsbn;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidateMethodsTest {

    @Test
    @DisplayName("ISBN 검증 성공")
    void validateIsbn_success() {
        assertDoesNotThrow(() -> validateIsbn("1234567890"));
    }

    @Test
    @DisplayName("ISBN 검증 실패 - 길이 오류")
    void validateIsbn_fail_invalid_length() {
        // 10자리가 아닌 경우
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> validateIsbn("102345678"));  // 9자리
        assertEquals(INVALID_ISBN_LENGTH, e1.getMessage());

        Exception e2 = assertThrows(IllegalArgumentException.class,
                () -> validateIsbn("10234567890"));  // 11자리
        assertEquals(INVALID_ISBN_LENGTH, e2.getMessage());
    }

    @Test
    @DisplayName("ISBN 검증 실패 - 문자 포함")
    void validateIsbn_fail_invalid_format() {
        // 숫자가 아닌 문자가 포함된 경우
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> validateIsbn("10A3456780"));
        assertEquals(INVALID_ISBN_FORMAT, e.getMessage());
    }

    @Test
    @DisplayName("ISBN 검증 실패 - 국가/언어 식별 번호 오류")
    void validateIsbn_fail_invalid_country_code() {
        // 국가 코드가 10~90 범위를 벗어나는 경우
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> validateIsbn("0023456780"));
        assertEquals(INVALID_ISBN_COUNTRY_CODE, e1.getMessage());

        Exception e2 = assertThrows(IllegalArgumentException.class,
                () -> validateIsbn("9123456780"));
        assertEquals(INVALID_ISBN_COUNTRY_CODE, e2.getMessage());
    }

    @Test
    @DisplayName("ISBN 검증 실패 - 체크 디지트(마지막 자리) 오류")
    void validateIsbn_fail_invalid_check_digit() {
        // 마지막 자리가 0이 아닌 경우
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> validateIsbn("1023456781"));
        assertEquals(INVALID_ISBN_CHECK_DIGIT, e.getMessage());
    }
}