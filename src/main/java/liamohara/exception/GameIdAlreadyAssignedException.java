package liamohara.exception;

public class GameIdAlreadyAssignedException extends RuntimeException {
    public GameIdAlreadyAssignedException(String message) {
        super(message);
    }
}
