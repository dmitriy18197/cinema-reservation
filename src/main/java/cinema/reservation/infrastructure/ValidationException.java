package cinema.reservation.infrastructure;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
