package liamohara.service;

import liamohara.exception.PlayerNameTakenException;
import liamohara.exception.PlayerRoleTakenException;
import liamohara.model.Player;
import liamohara.repository.GamesRepository;
import liamohara.repository.PlayersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerManagerServiceImplTest {

    @Mock
    PlayersRepository mockPlayersRepository;

    @InjectMocks
    PlayerManagerServiceImpl playerManagerServiceImpl;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

    }

    Player playerOne = new Player("Player One", true, false);
    Player playerTwo = new Player("Player Two", false, true);
    Player duplicatePlayerOne = new Player("Player One", true, false);
    Player duplicateRole = new Player("Player Two", true, false);
    List<Player> listOfPlayers = new ArrayList<>();

    @Test
    @DisplayName("Calls getListOfPlayers and addNewPlayer methods in PlayersRepository once each.")
    void testAddNewPlayer_WhenPlayersRepositoryIsEmpty() {

        playerManagerServiceImpl.addNewPlayer(playerOne.getPlayerName(), playerOne.isNought(), playerOne.isCross());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(1)).addNewPlayer(Mockito.any());

    }

    @Test
    @DisplayName("Throws PlayerNameTakenException when player name is not unique.")
    void testAddNewPlayer_WhenPlayerNameIsNotUnique() {

        listOfPlayers.add(playerOne);

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);
        doThrow(PlayerNameTakenException.class).when(mockPlayersRepository).addNewPlayer(duplicatePlayerOne);

        assertThrowsExactly(PlayerNameTakenException.class, () ->  playerManagerServiceImpl.addNewPlayer(duplicatePlayerOne.getPlayerName(), duplicatePlayerOne.isNought(), duplicatePlayerOne.isCross()));

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(0)).addNewPlayer(Mockito.any());

    }

    @Test
    @DisplayName("Throws PlayerRoleTakenException when player role has already been assigned")
    void testAddNewPlayer_WhenPlayerRoleIsTaken() {

        listOfPlayers.add(playerOne);
        String expectedMessage = "Nought role is taken by Player One. Player Two has thus been assigned the role of cross.";
        String actualMessage = "";

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        try {
            playerManagerServiceImpl.addNewPlayer(duplicateRole.getPlayerName(), duplicateRole.isNought(), duplicateRole.isCross());
        } catch (PlayerRoleTakenException pre) {
            actualMessage = pre.getMessage();
            assertThrowsExactly(PlayerRoleTakenException.class, () ->  playerManagerServiceImpl.addNewPlayer(duplicateRole.getPlayerName(), duplicateRole.isNought(), duplicateRole.isCross()));
        }

        verify(mockPlayersRepository, times(2)).getListOfPlayers();
        verify(mockPlayersRepository, times(2)).addNewPlayer(Mockito.any());
        assertEquals(expectedMessage, actualMessage);


    }

    @Test
    @DisplayName("Calls getListOfPlayers and addNewPlayer methods in PlayersRepository once each when adding second player.")
    void testAddNewPlayer_WhenAddingSecondPlayerWithUniqueNameAndRole() {

        listOfPlayers.add(playerOne);

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        playerManagerServiceImpl.addNewPlayer(playerTwo.getPlayerName(), playerTwo.isNought(), playerTwo.isCross());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(1)).addNewPlayer(Mockito.any());

    }

    @Test
    @DisplayName("Calls getListOfPlayers and updatePlayer methods in PlayersRepository once each when player has moves remaining")
    void testUpdatePlayerMovesRemaining_WhenPlayerHasMovesRemaining() {

        listOfPlayers.add(playerOne);
        listOfPlayers.add(playerTwo);
        int expectedMovesRemaining = 2;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        playerManagerServiceImpl.updatePlayerMovesRemaining(playerOne.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(1)).updatePlayer(Mockito.any());
        assertEquals(expectedMovesRemaining, playerOne.getMovesRemaining());

    }

    @Test
    @DisplayName("Calls getListOfPlayers method in PlayersRespository once only when player has no more moves remaining")
    void testUpdatePlayerMovesRemaining_WhenPlayerHasNoMovesRemaining() {

        playerOne.setMovesRemaining(0);
        playerTwo.setMovesRemaining(1);
        listOfPlayers.add(playerOne);
        listOfPlayers.add(playerTwo);
        int expectedMovesRemainingPlayerOne = 0;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        playerManagerServiceImpl.updatePlayerMovesRemaining(playerOne.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(0)).updatePlayer(Mockito.any());
        assertEquals(expectedMovesRemainingPlayerOne, playerOne.getMovesRemaining());

    }

    @Test
    @DisplayName("Returns 3 when player has not moved")
    void testGetPlayerMovesRemaining_WhenPlayerHasNotMoved() {

        listOfPlayers.add(playerOne);
        int expectedMovesRemaining = 5;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        int result = playerManagerServiceImpl.getPlayerMovesRemaining(playerOne.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertEquals(expectedMovesRemaining, result);

    }

    @Test
    @DisplayName("Returns 0 when player has no moves remaining")
    void testGetPlayerMovesRemaining_WhenPlayerHasNoMovesRemaining() {

        playerOne.setMovesRemaining(0);
        listOfPlayers.add(playerOne);
        int expectedMovesRemaining = 0;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        int result = playerManagerServiceImpl.getPlayerMovesRemaining(playerOne.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertEquals(expectedMovesRemaining, result);

    }

    @Test
    @DisplayName("Returns 0 when player is not found")
    void testGetPlayerMovesRemaining_WhenPlayerIsNotFound() {

        listOfPlayers.add(playerOne);
        int expectedMovesRemaining = 0;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        int result = playerManagerServiceImpl.getPlayerMovesRemaining(playerTwo.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertEquals(expectedMovesRemaining, result);

    }

    @Test
    @DisplayName("Returns 0 when player has not won a game")
    void testGetPlayerScore_WhenPlayerHasNotWonAGame() {

        listOfPlayers.add(playerOne);
        int expectedPlayerScore = 0;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        int result = playerManagerServiceImpl.getPlayerScore(playerOne.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertEquals(expectedPlayerScore, result);

    }

    @Test
    @DisplayName("Returns 1 when player has won a game")
    void testGetPlayerScore_WhenPlayerHasWonOneGame() {

        playerOne.setPlayerScore(1);
        listOfPlayers.add(playerOne);
        int expectedPlayerScore = 1;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        int result = playerManagerServiceImpl.getPlayerScore(playerOne.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertEquals(expectedPlayerScore, result);

    }

    @Test
    @DisplayName("Returns 0 when player not found.")
    void testGetPlayerScore_WhenPlayerNotFound() {

        playerOne.setPlayerScore(1);
        listOfPlayers.add(playerOne);
        int expectedPlayerScore = 0;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        int result = playerManagerServiceImpl.getPlayerScore(playerTwo.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertEquals(expectedPlayerScore, result);

    }

    @Test
    @DisplayName("Calls getListOfPlayers and updatePlayer methods in PlayersRepository once each when player has won a game")
    void testUpdatePlayerScore_WhenPlayerHasWonAGame() {

        playerOne.setMovesRemaining(0);
        listOfPlayers.add(playerOne);
        int expectedPlayerScore = 1;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        playerManagerServiceImpl.updatePlayerScore(playerOne.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(1)).updatePlayer(Mockito.any());
        assertEquals(expectedPlayerScore, playerManagerServiceImpl.getPlayerScore(playerOne.getPlayerName()));

    }

    @Test
    @DisplayName("Calls getListOfPlayers method in PlayersRepository once only when player still has moves remaining")
    void testUpdatePlayerScore_WhenPlayerHasMovesRemaining() {

        playerOne.setMovesRemaining(1);
        listOfPlayers.add(playerOne);
        int expectedPlayerScore = 0;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        playerManagerServiceImpl.updatePlayerScore(playerOne.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(0)).updatePlayer(Mockito.any());
        assertEquals(expectedPlayerScore, playerManagerServiceImpl.getPlayerScore(playerOne.getPlayerName()));

    }

    @Test
    @DisplayName("Returns ArrayList of player names when PlayersRepository contains players")
    void testGetPlayerNames_WhenPlayersRepositoryContainsPlayers() {

        listOfPlayers.add(playerOne);

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        ArrayList<String> result = playerManagerServiceImpl.getPlayerNames();

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertEquals(playerOne.getPlayerName(), result.getFirst());

    }

    @Test
    @DisplayName("Returns empty ArrayList when PlayersRepository is empty")
    void testGetPlayerName_WhenPlayersRepositoryIsEmpty() {

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(null);

        ArrayList<String> result = playerManagerServiceImpl.getPlayerNames();

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertTrue(result.isEmpty());

    }

    @Test
    @DisplayName("Returns empty String when passed empty String")
    void testGetPlayerRole_WhenPassedEmptyString() {

        String emptyPlayerName = "";
        listOfPlayers.add(playerOne);

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        String result = playerManagerServiceImpl.getPlayerRole(emptyPlayerName);

        verify(mockPlayersRepository, times(0)).getListOfPlayers();
        assertTrue(result.isEmpty());

    }

    @Test
    @DisplayName("Returns 'O' when passed name of player with role of nought")
    void testGetPlayerRole_WhenPassedValidPlayerName() {

        String playerName = "Player One";
        listOfPlayers.add(playerOne);
        String expectedResult = "O";

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        String result = playerManagerServiceImpl.getPlayerRole(playerName);

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertEquals(expectedResult, result);

    }

    @Test
    @DisplayName("Returns empty String when player name is not found")
    void testGetPlayerRole_WhenPlayerNameNotFound() {

        String emptyPlayerName = "Player Two";
        listOfPlayers.add(playerOne);

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        String result = playerManagerServiceImpl.getPlayerRole(emptyPlayerName);

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertTrue(result.isEmpty());

    }

    @Test
    @DisplayName("Returns empty string when player Repository is empty")
    void testGetPlayerRole_WhenPlayerRepositoryIsEmpty() {

        String playerName = "Player One";

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(null);

        String result = playerManagerServiceImpl.getPlayerRole(playerName);

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertTrue(result.isEmpty());

    }

    @Test
    @DisplayName("Calls getListOfPlayers and updatePlayer methods in PlayersRepository when player has less than 3 moves remaining.")
    void testResetPlayerMovesRemaining_WhenPlayerHasLessThanThreeMovesRemaining() {

        playerOne.setMovesRemaining(2);
        playerTwo.setMovesRemaining(3);
        listOfPlayers.add(playerOne);
        listOfPlayers.add(playerTwo);

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        playerManagerServiceImpl.resetPlayerMovesRemaining(playerOne.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(1)).updatePlayer(Mockito.any());
        assertEquals(5, playerOne.getMovesRemaining());

    }

    @Test
    @DisplayName("Calls getListOfPlayers method in PlayersRepository when player has 3 or more moves remaining.")
    void testResetPlayerMovesRemaining_WhenPlayerHasThreeOrMoreMovesRemaining() {

        playerOne.setMovesRemaining(2);
        playerTwo.setMovesRemaining(3);
        listOfPlayers.add(playerOne);
        listOfPlayers.add(playerTwo);

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        playerManagerServiceImpl.resetPlayerMovesRemaining(playerTwo.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(0)).updatePlayer(Mockito.any());
        assertEquals(3, playerTwo.getMovesRemaining());

    }

    @Test
    @DisplayName("Calls getListOfPlayers in PlayersRepository when players repository is empty.")
    void testResetPlayerMovesRemaining_WhenPlayersRepositoryIsEmpty() {

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        playerManagerServiceImpl.resetPlayerMovesRemaining(playerTwo.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(0)).updatePlayer(Mockito.any());

    }
}