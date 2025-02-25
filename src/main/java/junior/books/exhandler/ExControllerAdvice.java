package junior.books.exhandler;

import jakarta.persistence.EntityNotFoundException;
import junior.books.exhandler.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class ExControllerAdvice {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        log.warn(ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.CONFLICT.value(),
                ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.warn(ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.NOT_FOUND.value(),
                ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleDefaultException(Exception ex) {
        log.warn(ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorListResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> message = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String builder = fieldError.getField() + ": " + fieldError.getDefaultMessage() +
                    " / 입력된 값: [" + fieldError.getRejectedValue() + "]";
            message.add(builder);
        }
        log.warn(message.toString());
        ErrorListResponse errorListResponse = ErrorListResponse.of(HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getSimpleName(), message);
        return new ResponseEntity<>(errorListResponse, HttpStatus.BAD_REQUEST);
    }
}
