package liamohara.view;

import liamohara.controller.PlayerController;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainActivityTest {


    @Mock
    PlayerController mockPlayerController;

    @InjectMocks
    private MainActivity mainActivity = new MainActivity();

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Mock
    BufferedReader mockBufferedReader;

    void provideSingleInput(String data) {

        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);

    }

    String provideMultipleInputs(ArrayList<String> data) {

        String mockConsoleInput;
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < data.size(); i++) {
            stringBuilder.append(data.get(i)).append("\n");

        }

        mockConsoleInput = stringBuilder.toString();
        ByteArrayInputStream testIn = new ByteArrayInputStream(mockConsoleInput.getBytes());
        System.setIn(testIn);
        return mockConsoleInput;

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

    @Test
    @DisplayName("Nothing printed to console when passed an empty ArrayList")
    void testPrintMessage_WhenPassedEmptyArrayList() {

        ArrayList<String> messages = new ArrayList<>();

        mainActivity.printMessages(messages);

        assertEquals("", outContent.toString());

    }

    @Test
    @DisplayName("Calls the getPlayerNames and addNewPlayer methods once and twice respectively in PlayerController when PlayersRespository is empty and inputs are valid")
    void testSetup_WhenPlayersRepositoryIsEmpty() throws IOException {

        String playerOneName = "Player One";
        String playerOneRole = "O";
        String playerTwoName = "Player Two";
        String playerTwoRole = "X";

        ArrayList<String> listOfPlayerNames = new ArrayList<>();
        ArrayList<String> mockConsoleInputs = new ArrayList<>();
        mockConsoleInputs.add(playerOneName);
        mockConsoleInputs.add(playerOneRole);
        mockConsoleInputs.add(playerTwoName);
        mockConsoleInputs.add(playerTwoRole);

        when(mockPlayerController.getPlayerNames()).thenReturn(listOfPlayerNames);

        when(mockBufferedReader.readLine()).thenReturn(provideMultipleInputs(mockConsoleInputs));

        mainActivity.setup();

        verify(mockPlayerController, times(1)).getPlayerNames();
        verify(mockPlayerController, times(1)).addNewPlayer(playerOneName, true, false);
        verify(mockPlayerController, times(1)).addNewPlayer(playerTwoName, false, true);

    }
}