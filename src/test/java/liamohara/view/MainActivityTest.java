package liamohara.view;

import liamohara.controller.GameController;
import liamohara.controller.GridController;
import liamohara.controller.PlayerController;
import liamohara.exception.IllegalMoveException;
import liamohara.exception.PlayerRoleTakenException;
import liamohara.model.Grid;
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

    @Mock
    GridController mockGridController;

    @Mock
    GameController mockGameController;

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
    @DisplayName("Returns String[][] of player data when players repository contains players.")
    void testGetPlayersData_WhenPlayersRepositoryContainsPlayers() {

        ArrayList<String> playersNameList = new ArrayList<>();
        playersNameList.add("Player One");
        playersNameList.add("Player Two");
        String[][] expectedResult = { {"Player One", "O", "3", "O"}, {"Player Two", "X", "3", "0"} };

        when(mockPlayerController.getPlayerNames()).thenReturn(playersNameList);
        when(mockPlayerController.getPlayerRole("Player One")).thenReturn("O");
        when(mockPlayerController.getPlayerRole("Player Two")).thenReturn("X");
        when(mockPlayerController.getPlayerMovesRemaining("Player One")).thenReturn(3);
        when(mockPlayerController.getPlayerMovesRemaining("Player Two")).thenReturn(3);
        when(mockPlayerController.getPlayerScore("Player One")).thenReturn(0);
        when(mockPlayerController.getPlayerScore("Player Two")).thenReturn(0);

        String[][] result = mainActivity.getPlayersData();

        verify(mockPlayerController, times(1)).getPlayerNames();
        verify(mockPlayerController, times(2)).getPlayerRole(Mockito.any());
        verify(mockPlayerController, times(2)).getPlayerMovesRemaining(Mockito.any());
        verify(mockPlayerController, times(2)).getPlayerScore(Mockito.any());
        assertEquals(expectedResult[0][1], result[0][1]);

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


    @Test
    @DisplayName("Returns empty grid when no players have moved")
    void testDrawGrid_WhenNoPlayersHaveMoved() {

        int gameId = 1;
        String[][] emptyGridData = new String[3][3];
        ArrayList<String> emptyGrid = new ArrayList<>();
        emptyGrid.add("    1   2   3  ");
        emptyGrid.add("  ╔═══╤═══╤═══╗");
        emptyGrid.add(" 1║   │   │   ║");
        emptyGrid.add("  ╟───┼───┼───╢");
        emptyGrid.add(" 2║   │   │   ║");
        emptyGrid.add("  ╟───┼───┼───╢");
        emptyGrid.add(" 3║   │   │   ║");
        emptyGrid.add("  ╚═══╧═══╧═══╝");

        when(mockGridController.getGrid(gameId)).thenReturn(emptyGridData);

        ArrayList<String> result = mainActivity.drawGrid(gameId);

        verify(mockGridController, times(1)).getGrid(gameId);
        assertEquals(emptyGrid, result);

    }

    @Test
    @DisplayName("Returns full grid when game is complete")
    void testDrawGrid_WhenGameIsComplete() {

        int gameId = 1;
        String[][] emptyGridData = {{"X", "X", "O"}, {"O", "O", "X"}, {"X", "X", "O"}};
        ArrayList<String> emptyGrid = new ArrayList<>();
        emptyGrid.add("    1   2   3  ");
        emptyGrid.add("  ╔═══╤═══╤═══╗");
        emptyGrid.add(" 1║ X │ X │ O ║");
        emptyGrid.add("  ╟───┼───┼───╢");
        emptyGrid.add(" 2║ O │ O │ X ║");
        emptyGrid.add("  ╟───┼───┼───╢");
        emptyGrid.add(" 3║ X │ X │ O ║");
        emptyGrid.add("  ╚═══╧═══╧═══╝");

        when(mockGridController.getGrid(gameId)).thenReturn(emptyGridData);

        ArrayList<String> result = mainActivity.drawGrid(gameId);

        verify(mockGridController, times(1)).getGrid(gameId);
        assertEquals(emptyGrid, result);

    }

    @Test
    @DisplayName("Returns partial grid when game is progress")
    void testDrawGrid_WhenGameIsInProgress() {

        int gameId = 1;
        String[][] emptyGridData = {{"X", null, null}, {"O", null, null}, {"X", null, null}};
        ArrayList<String> emptyGrid = new ArrayList<>();
        emptyGrid.add("    1   2   3  ");
        emptyGrid.add("  ╔═══╤═══╤═══╗");
        emptyGrid.add(" 1║ X │   │   ║");
        emptyGrid.add("  ╟───┼───┼───╢");
        emptyGrid.add(" 2║ O │   │   ║");
        emptyGrid.add("  ╟───┼───┼───╢");
        emptyGrid.add(" 3║ X │   │   ║");
        emptyGrid.add("  ╚═══╧═══╧═══╝");

        when(mockGridController.getGrid(gameId)).thenReturn(emptyGridData);

        ArrayList<String> result = mainActivity.drawGrid(gameId);

        verify(mockGridController, times(1)).getGrid(gameId);
        assertEquals(emptyGrid, result);

    }

    @Test
    @DisplayName("Returns integer between 0 and 1 when called")
    void testFlipCoin() {

        int result = mainActivity.flipCoin();

        assertTrue(result == 0 || result == 1);

    }

    @Test
    @DisplayName("Calls updateGrid in GridController once when user makes a valid move")
    void testPlayerMove_WhenPlayerMakesValidMove() throws IOException {

        int gameId = 1;
        int row = 0;
        int column = 0;
        String playerName = "Player One";
        String rowInput = "1";
        String columnInput = "1";
        ArrayList<String> mockConsoleInputs = new ArrayList<>();
        mockConsoleInputs.add(columnInput);
        mockConsoleInputs.add(rowInput);


        when(mockBufferedReader.readLine()).thenReturn(provideMultipleInputs(mockConsoleInputs));

        mainActivity.playerMove(gameId, playerName);

        verify(mockGridController, times(1)).updateGrid(gameId, row, column, playerName);
        verify(mockPlayerController, times(1)).updatePlayerMovesRemaining(playerName);
        verify(mockGameController, times(1)).updateMovesRemaining(gameId);

    }

    @Test
    @DisplayName("Calls updateGrid in GridController once when player makes a valid move after second attempt")
    void testPlayerMove_WhenPlayerMakesValidMoveAfterSecondAttempt() throws IOException {

        int gameId = 1;
        int row = 0;
        int column = 0;
        String playerName = "Player One";
        String rowInput = "1";
        String invalidColumnInput = "4";
        String validColumnInput = "1";
        ArrayList<String> mockConsoleInputs = new ArrayList<>();
        mockConsoleInputs.add(invalidColumnInput);
        mockConsoleInputs.add(validColumnInput);
        mockConsoleInputs.add(rowInput);

        when(mockBufferedReader.readLine()).thenReturn(provideMultipleInputs(mockConsoleInputs));

        mainActivity.playerMove(gameId, playerName);

        verify(mockGridController, times(1)).updateGrid(gameId, row, column, playerName);
        verify(mockPlayerController, times(1)).updatePlayerMovesRemaining(playerName);
        verify(mockGameController, times(1)).updateMovesRemaining(gameId);

    }

    @Test
    @DisplayName("Calls updateGrid in GridController once when player makes a legal after second attempt")
    void testPlayerMove_WhenPlayerMakesLegalMoveAfterSecondAttempt() throws IOException {

        int gameId = 1;
        int row = 0;
        int column = 1;
        String playerName = "Player Two";
        String rowInput = "1";
        String illegalColumnInput = "1";
        String validColumnInput = "2";
        ArrayList<String> mockConsoleInputs = new ArrayList<>();
        mockConsoleInputs.add(illegalColumnInput);
        mockConsoleInputs.add(rowInput);
        mockConsoleInputs.add(validColumnInput);
        mockConsoleInputs.add(rowInput);
        String ieMsg = "Position: row " + rowInput + " by column " + illegalColumnInput;
        IllegalMoveException ie = new IllegalMoveException(ieMsg);

        when(mockBufferedReader.readLine()).thenReturn(provideMultipleInputs(mockConsoleInputs));
        doThrow(ie).when(mockGridController).updateGrid(gameId, row, 0, playerName);

        mainActivity.playerMove(gameId, playerName);

        verify(mockGridController, times(1)).updateGrid(gameId, row, 0, playerName);
        verify(mockGridController, times(1)).updateGrid(gameId, row, column, playerName);
        verify(mockPlayerController, times(1)).updatePlayerMovesRemaining(playerName);
        verify(mockGameController, times(1)).updateMovesRemaining(gameId);

    }

    @Test
    @DisplayName("Returns String[] containing 'won' and 'Player One' when Player One has won game")
    void testAnalyseGame_WhenPlayerOneHasWonGame() {

        int gameId = 1;
        String playerOneName = "Player One";
        String playerOneRole = "X";
        String playerTwoName = "Player Two";
        String playerTwoRole = "O";
        String[][] gridData = {{"X", "X", "X"}, {"O", "O", "X"}, {"X", "O", "O"}};
        String[] expectedResults = {"won", "Player One"};

        when(mockGridController.getGrid(gameId)).thenReturn(gridData);
        when(mockPlayerController.getPlayerRole(playerOneName)).thenReturn(playerOneRole);
        when(mockPlayerController.getPlayerRole(playerTwoName)).thenReturn(playerTwoRole);

        String[] result = mainActivity.analyseGame(gameId, playerOneName, playerTwoName);

        verify(mockGridController, times(1)).getGrid(gameId);
        verify(mockPlayerController, times(1)).getPlayerRole(playerOneName);
        verify(mockPlayerController, times(1)).getPlayerRole(playerTwoName);
        assertAll(
                () -> assertEquals(expectedResults[0], result[0]),
                () -> assertEquals(expectedResults[1], result[1]));

    }

    @Test
    @DisplayName("Returns String[] containing 'draw' and 'null' when game was a draw.")
    void testAnalyseGame_WhenGameADraw() {

        int gameId = 1;
        String playerOneName = "Player One";
        String playerOneRole = "X";
        String playerTwoName = "Player Two";
        String playerTwoRole = "O";
        String[][] gridData = {{"X", "X", "O"}, {"O", "O", "X"}, {"X", "X", "O"}};
        String[] expectedResults = {"draw", ""};

        when(mockGridController.getGrid(gameId)).thenReturn(gridData);
        when(mockPlayerController.getPlayerRole(playerOneName)).thenReturn(playerOneRole);
        when(mockPlayerController.getPlayerRole(playerTwoName)).thenReturn(playerTwoRole);

        String[] result = mainActivity.analyseGame(gameId, playerOneName, playerTwoName);

        verify(mockGridController, times(1)).getGrid(gameId);
        verify(mockPlayerController, times(1)).getPlayerRole(playerOneName);
        verify(mockPlayerController, times(1)).getPlayerRole(playerTwoName);
        assertAll(
                () -> assertEquals(expectedResults[0], result[0]),
                () -> assertEquals(expectedResults[1], result[1]));

    }

    @Test
    @DisplayName("Returns String[] containing 'won' and 'Player One' when Player One has won game with moves remaining")
    void testAnalyseGame_WhenPlayerOneHasWonGameWithMovesRemaining() {

        int gameId = 1;
        String playerOneName = "Player One";
        String playerOneRole = "X";
        String playerTwoName = "Player Two";
        String playerTwoRole = "O";
        String[][] gridData = {{"X", "X", "X"}, {null, null, null}, {"O", "O", null}};
        String[] expectedResults = {"won", "Player One"};

        when(mockGridController.getGrid(gameId)).thenReturn(gridData);
        when(mockPlayerController.getPlayerRole(playerOneName)).thenReturn(playerOneRole);
        when(mockPlayerController.getPlayerRole(playerTwoName)).thenReturn(playerTwoRole);

        String[] result = mainActivity.analyseGame(gameId, playerOneName, playerTwoName);

        verify(mockGridController, times(1)).getGrid(gameId);
        verify(mockPlayerController, times(1)).getPlayerRole(playerOneName);
        verify(mockPlayerController, times(1)).getPlayerRole(playerTwoName);
        assertAll(
                () -> assertEquals(expectedResults[0], result[0]),
                () -> assertEquals(expectedResults[1], result[1]));

    }

    @Test
    @DisplayName("Calls setWinner and updatePlayerScore in GameController and PlayerController, respectively, once each when called")
    void testPostResult_WhenProvidedWithValidGameIdAndValidPlayerName () {

        int gameId = 1;
        String winnerName = "Player One";

        mainActivity.postResult(gameId, winnerName);

        verify(mockPlayerController, times(1)).updatePlayerScore(winnerName);
        verify(mockGameController, times(1)).setWinner(gameId, winnerName);

    }


}