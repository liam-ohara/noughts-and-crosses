package liamohara.exception;

public class GlobalExceptionHandler {

    public String handleGameIdAlreadyAssignedException (GameIdAlreadyAssignedException ge) {

        return "Game ID: " + ge.getMessage() + " has already been assigned.";
    }
}
