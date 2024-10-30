package liamohara.exception;

public class PlayerRoleTakenException extends RuntimeException {
    public PlayerRoleTakenException(String message) {
        super(message);
    }
}
