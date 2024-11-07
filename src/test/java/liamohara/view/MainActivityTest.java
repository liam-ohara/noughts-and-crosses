package liamohara.view;

import liamohara.controller.PlayerController;
import liamohara.exception.PlayerRoleTakenException;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainActivityTest {


    @Mock
    PlayerController mockPlayerController;

    @InjectMocks
    private MainActivity mainActivity = new MainActivity();

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

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ArrayList<String> messages = new ArrayList<>();
        messages.add("Test line one");
        messages.add("Test line two");

        mainActivity.printMessages(messages);

        assertEquals("Test line one\nTest line two\n", outContent.toString());

        System.setOut(originalOut);

    }

    @Test
    @DisplayName("Nothing printed to console when passed an empty ArrayList")
    void testPrintMessage_WhenPassedEmptyArrayList() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ArrayList<String> messages = new ArrayList<>();

        mainActivity.printMessages(messages);

        assertEquals("", outContent.toString());

        System.setOut(originalOut);

    }

    @Test
    @DisplayName("Calls the getPlayerNames and addNewPlayer methods once and twice respectively in PlayerController when PlayersRespository is empty and inputs are valid")
    void testSetup_WhenPlayersRepositoryIsEmptyAndValidInputsProvidedInFirstAttempt() throws IOException {

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

    @Test
    @DisplayName("Calls the getPlayerNames and addNewPlayer methods once and twice respectively in PlayerController when PlayersRespository is empty and valid player name provided on second attempt")
    void testSetup_WhenPlayersRepositoryIsEmptyAndNonDuplicatePlayerNameProvidedOnSecondAttempt() throws IOException {

        String playerOneName = "Player One";
        String playerOneRole = "O";
        String playerTwoInvalidName = "Player One";
        String playerTwoRole = "X";
        String playerTwoValidName = "PlayerTwo";

        ArrayList<String> listOfPlayerNames = new ArrayList<>();
        ArrayList<String> mockConsoleInputs = new ArrayList<>();
        mockConsoleInputs.add(playerOneName);
        mockConsoleInputs.add(playerOneRole);
        mockConsoleInputs.add(playerTwoInvalidName);
        mockConsoleInputs.add(playerTwoRole);
        mockConsoleInputs.add(playerOneName);
        mockConsoleInputs.add(playerTwoValidName);

        when(mockPlayerController.getPlayerNames()).thenReturn(listOfPlayerNames);

        when(mockBufferedReader.readLine()).thenReturn(provideMultipleInputs(mockConsoleInputs));

        mainActivity.setup();

        verify(mockPlayerController, times(1)).getPlayerNames();
        verify(mockPlayerController, times(1)).addNewPlayer(playerOneName, true, false);
        verify(mockPlayerController, times(1)).addNewPlayer(playerTwoValidName, false, true);

    }

    @Test
    @DisplayName("Calls the getPlayerNames and addNewPlayer methods once and twice respectively in PlayerController when PlayersRespository is empty and valid player roles provided on second attempt")
    void testSetup_WhenPlayersRepositoryIsEmptyAndNonDuplicatePlayerRolesProvidedOnSecondAttempt() throws IOException {

        String playerOneName = "Player One";
        String playerOneRole = "O";
        String playerTwoName = "Player Two";
        String playerTwoInvalidRole = "O";

        ArrayList<String> listOfPlayerNames = new ArrayList<>();
        ArrayList<String> mockConsoleInputs = new ArrayList<>();
        mockConsoleInputs.add(playerOneName);
        mockConsoleInputs.add(playerOneRole);
        mockConsoleInputs.add(playerTwoName);
        mockConsoleInputs.add(playerTwoInvalidRole);

        String expectedExceptionMessage = "Nought role is taken by Player One. Player Two has thus been assigned the role of cross.";

        when(mockPlayerController.getPlayerNames()).thenReturn(listOfPlayerNames);

        PlayerRoleTakenException pre = new PlayerRoleTakenException(expectedExceptionMessage);

        doThrow(pre).when(mockPlayerController).addNewPlayer(playerTwoName, true, false);

        when(mockBufferedReader.readLine()).thenReturn(provideMultipleInputs(mockConsoleInputs));

        mainActivity.setup();

        verify(mockPlayerController, times(1)).getPlayerNames();
        verify(mockPlayerController, times(1)).addNewPlayer(playerOneName, true, false);
        assertThrowsExactly(pre.getClass(), () -> mockPlayerController.addNewPlayer(playerTwoName, true, false), expectedExceptionMessage);

    }

    @Test
    @DisplayName("Returns empty String[][] when players repository is empty.")
    void testGetPlayersData_WhenPlayersRepositoryIsEmpty() {

        ArrayList<String> emptyPlayersNameList = new ArrayList<>();

        when(mockPlayerController.getPlayerNames()).thenReturn(emptyPlayersNameList);

        String[][] result = mainActivity.getPlayersData();

        verify(mockPlayerController, times(1)).getPlayerNames();
        verify(mockPlayerController, times(0)).getPlayerRole(Mockito.any());
        verify(mockPlayerController, times(0)).getPlayerMovesRemaining(Mockito.any());
        verify(mockPlayerController, times(0)).getPlayerScore(Mockito.any());
        assertNull(result[0][0]);

    }


    @Test
    @DisplayName("Returns table with no players when provided with null String[][]")
    void testDrawPlayerTable_WhenProvidedWithEmptyStringArray() {

        ArrayList<String> playerTable = new ArrayList<>();
        playerTable.add("| Name | Role | Moves Remaining | Score |");
        playerTable.add("-----------------------------------------");

        String[][] tableData = new String[0][0];

        ArrayList<String> result = mainActivity.drawPlayerTable(tableData);

        assertEquals(playerTable, result);

    }

    @Test
    @DisplayName("Returns table with two names, role, moves remaining and score of current players when provide valid data")
    void testDrawPlayerTable_WhenProvidedWithValidDataInStringArray() {

        ArrayList<String> playerTable = new ArrayList<>();
        playerTable.add("| Name       | Role | Moves Remaining | Score |");
        playerTable.add("-----------------------------------------------");
        playerTable.add("| Player One | O    | 3               | 0     |");
        playerTable.add("| Player Two | X    | 3               | 0     |");


        String[][] tableData = new String[2][4];
        tableData[0][0] = "Player One";
        tableData[0][1] = "O";
        tableData[0][2] = "3";
        tableData[0][3] = "0";
        tableData[1][0] = "Player Two";
        tableData[1][1] = "X";
        tableData[1][2] = "3";
        tableData[1][3] = "0";

        ArrayList<String> result = mainActivity.drawPlayerTable(tableData);

        assertEquals(playerTable, result);

    }
}