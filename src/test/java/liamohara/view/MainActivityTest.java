package liamohara.view;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainActivityTest {

    private MainActivity mainActivity = new MainActivity();
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Returns an ArrayList<String> of size 4 when called")
    void testWelcome() {

        int expectedArrayListSize = 4;

        int result = mainActivity.welcome().size();

        assertEquals(expectedArrayListSize, result);

    }

    @Test
    @DisplayName("Prints messages, each appended with a new line, to the console when passed and ArrayList of strings")
    void testPrintMessages_WhenPassedArrayListOfStrings() {

        ArrayList<String> messages = new ArrayList<>();
        messages.add("Test line one");
        messages.add("Test line two");

        mainActivity.printMessages(messages);

        assertEquals("Test line one\nTest line two\n", outContent.toString());
    }


}