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

    public String handlePlayerNameTakenException (PlayerNameTakenException pe) {

        return "The player name \"" + pe.getMessage() + "\" is already taken.";
    }

    public String handlePlayerRoleTakenException (PlayerRoleTakenException pre) {

        return pre.getMessage();
    }
}
