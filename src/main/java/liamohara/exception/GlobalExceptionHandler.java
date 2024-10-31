package liamohara.exception;

public class GlobalExceptionHandler {

    public String handleGameIdAlreadyAssignedException (GameIdAlreadyAssignedException ge) {

        return "Game ID: " + ge.getMessage() + " has already been assigned.";
    }

    public String handleGridNotFoundException (GridNotFoundException gne) {

        return "No grid with game ID: " + gne.getMessage() + " was found.";
    }

    public String handleIllegalMoveException (IllegalMoveException ie) {

        return "Illegal Move at " + ie.getMessage();
    }

    public String handleNoGridsException (NoGridsException nge) {

        return nge.getMessage();
    }
}
