package liamohara.view;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainActivityTest {

    MainActivity mainActivity = new MainActivity();

    @Test
    @DisplayName("Returns an ArrayList<String> of size 4 when called")
    void testWelcome() {

        int expectedArrayListSize = 4;

        int result = mainActivity.welcome().size();

        assertEquals(expectedArrayListSize, result);

    }


}