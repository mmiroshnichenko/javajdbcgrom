package hibernate.lesson4.exceptions;

public class AuthException extends BadRequestException {
    public AuthException(String message) {
        super(message);
    }
}
