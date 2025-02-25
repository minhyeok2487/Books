package junior.books.exhandler.exceptions;

public class ConflictException extends RuntimeException {

    public ConflictException(String baseMessage, String dataMessage) {
        super(String.format("%s / %s", baseMessage, dataMessage));
    }
}
