package junior.books.utils;

import static junior.books.exhandler.constants.ValidateErrorMessage.*;

public class ValidateMethods {

    public static void validateIsbn(String isbn) {
        // 10자리 숫자인지 확인
        if (isbn.length() != 10) {
            throw new IllegalArgumentException(INVALID_ISBN_LENGTH);
        }

        // 숫자로만 구성되었는지 확인
        if (!isbn.matches("\\d{10}")) {
            throw new IllegalArgumentException(INVALID_ISBN_FORMAT);
        }

        // 국가, 언어 식별 번호: 첫 번째 두 자리 (10~90 사이)
        int countryCode = Integer.parseInt(isbn.substring(0, 2));
        if (countryCode < 10 || countryCode > 90) {
            throw new IllegalArgumentException(INVALID_ISBN_COUNTRY_CODE);
        }

        // 마지막 체크 디지트가 0인지 확인
        if (isbn.charAt(9) != '0') {
            throw new IllegalArgumentException(INVALID_ISBN_CHECK_DIGIT);
        }

        // 출판사 식별 번호: 다음 3~6자리
        // 책 식별 번호 : 다음 7~9자리
        // -> 숫자 구성 & 길이 검증으로 검증됨
    }
}