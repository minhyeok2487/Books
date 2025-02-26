package junior.books.exhandler.constants;

public class ValidateErrorMessage {
    public static final String INVALID_ISBN_LENGTH = "ISBN은 정확히 10자리여야 합니다.";
    public static final String INVALID_ISBN_FORMAT = "ISBN은 숫자로만 구성되어야 합니다.";
    public static final String INVALID_ISBN_COUNTRY_CODE = "국가/언어 식별 번호는 10에서 90 사이여야 합니다.";
    public static final String INVALID_ISBN_CHECK_DIGIT = "체크 디지트(마지막 자리)는 0이어야 합니다.";
}