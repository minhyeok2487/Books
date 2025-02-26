package junior.books.utils;

import junior.books.exhandler.codes.ErrorCode;
import junior.books.exhandler.codes.ErrorCodeGroup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCodes {
    ErrorCode[] codes();
    ErrorCodeGroup[] groups() default {};
}
