package liamohara.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleGameIdAlreadyAssignedException() {

        String geMessage = "1";
        GameIdAlreadyAssignedException ge = new GameIdAlreadyAssignedException("1");
        String expectedResult = "Game ID: 1 has already been assigned.";

        String result = globalExceptionHandler.handleGameIdAlreadyAssignedException(ge);

        assertEquals(expectedResult, result);

    }

    @Test
    void testHandleGridNotFoundException() {

        String gneMessage = "1";
        GridNotFoundException gne = new GridNotFoundException("1");
        String expectedResult = "No grid with game ID: 1 was found.";

        String result = globalExceptionHandler.handleGridNotFoundException(gne);

        assertEquals(expectedResult, result);

    }

    @Test
    void testHandleIllegalMoveException() {

        String ieMessage = "Position: row 0 by column 0";
        IllegalMoveException ie = new IllegalMoveException(ieMessage);
        String expectedResult = "Illegal Move at Position: row 0 by column 0";

        String result = globalExceptionHandler.handleIllegalMoveException(ie);

        assertEquals(expectedResult, result);

    }

    @Test
    void testHandleNoGridsException() {

        String ngeMessage = "No grids created yet.";
        NoGridsException nge = new NoGridsException(ngeMessage);

        String result = globalExceptionHandler.handleNoGridsException(nge);

        assertEquals(ngeMessage, result);

    }

    @Test
    void testHandlePlayerNameTakenException() {

        String peMessage = "Player One";
        PlayerNameTakenException pe = new PlayerNameTakenException(peMessage);
        String expectedResult = "The player name \"Player One\" is already taken.";

        String result = globalExceptionHandler.handlePlayerNameTakenException(pe);

        assertEquals(expectedResult, result);

    }

}