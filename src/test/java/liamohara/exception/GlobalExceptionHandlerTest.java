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


}