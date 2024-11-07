package liamohara.controller;

import liamohara.exception.PlayerNameTakenException;
import liamohara.exception.PlayerRoleTakenException;
import liamohara.service.PlayerManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerControllerTest {

    @Mock
    PlayerManagerServiceImpl mockPlayerManagerServiceImpl;

    @InjectMocks
    PlayerController playerController;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Calls updatePlayerMovesRemaining method in Service layer once")
    void updatePlayerMovesRemaining() {

        String playerName = "Player One";

        playerController.updatePlayerMovesRemaining(playerName);

        verify(mockPlayerManagerServiceImpl, times(1)).updatePlayerMovesRemaining(playerName);

    }

    @Test
    @DisplayName("Returns 3 when at the start of the game the player has not moved")
    void testGetPlayerMovesRemaining_WhenPlayerHasNotMoved() {

        String playerName = "Player One";
        int expectedMovesRemaining = 3;

        when(mockPlayerManagerServiceImpl.getPlayerMovesRemaining(playerName)).thenReturn(3);

        int result = playerController.getPlayerMovesRemaining(playerName);

        verify(mockPlayerManagerServiceImpl, times(1)).getPlayerMovesRemaining(playerName);
        assertEquals(expectedMovesRemaining, result);

    }

    @Test
    @DisplayName("Returns 1 when player has won one game")
    void testGetPlayerScore_WhenPlayerHasWonOneGame() {

        String playerName = "Player One";
        int expectedPlayerScore = 1;

        when(mockPlayerManagerServiceImpl.getPlayerScore(playerName)).thenReturn(1);

        int result = playerController.getPlayerScore(playerName);

        verify(mockPlayerManagerServiceImpl, times(1)).getPlayerScore(playerName);
        assertEquals(expectedPlayerScore, result);

    }

    @Test
    @DisplayName("Calls updatePlayerScore method in Service layer once.")
    void testUpdatePlayerScore() {

        String playerName = "Player One";

        playerController.updatePlayerScore(playerName);

        verify(mockPlayerManagerServiceImpl, times(1)).updatePlayerScore(playerName);

    }

    @Test
    @DisplayName("Calls addNewPlayer method in Service layer once when name is unique")
    void testAddNewPlayer_WhenPlayerNameIsUnique() {

        String playerName = "Player One";
        boolean isNought = true;
        boolean isCross = false;

        playerController.addNewPlayer(playerName, isNought, isCross);

        verify(mockPlayerManagerServiceImpl, times(1)).addNewPlayer(playerName, isNought, isCross);

    }

    @Test
    @DisplayName("Throws PlayerNameTakenException when player name is not unique.")
    void testAddNewPlayer_WhenPlayerNameIsNotUnique() {

        String duplicatePlayerName = "Duplicate Player";
        boolean isNought = true;
        boolean isCross = false;

        doThrow(PlayerNameTakenException.class).when(mockPlayerManagerServiceImpl).addNewPlayer(duplicatePlayerName, isNought, isCross);

        assertThrowsExactly(PlayerNameTakenException.class, () ->  playerController.addNewPlayer(duplicatePlayerName, isNought, isCross));

    }

    @Test
    @DisplayName("Throws PlayerRoleTakenException when player role has already been assigned.")
    void testAddNewPlayer_WhenPlayerRoleIsTaken() {

        String playerName = "Player Two";
        boolean isNought = true;
        boolean isCross = false;
        String actualMessage = "";

        PlayerRoleTakenException pre = new PlayerRoleTakenException("Nought role is taken by Player One. Player Two has thus been assigned the role of cross.");

        doThrow(pre).when(mockPlayerManagerServiceImpl).addNewPlayer(playerName, isNought, isCross);

        try {
            playerController.addNewPlayer(playerName, isNought, isCross);
        } catch (PlayerRoleTakenException e) {
            actualMessage = e.getMessage();
        }

        assertThrowsExactly(PlayerRoleTakenException.class, () ->  playerController.addNewPlayer(playerName, isNought, isCross));
        verify(mockPlayerManagerServiceImpl, times(2)).addNewPlayer(playerName, isNought, isCross);
        assertEquals(pre.getMessage(), actualMessage);

    }

    @Test
    @DisplayName("Returns ArrayList of player names when PlayersRepository contains players.")
    void testGetPlayerNames_WhenPlayersRepositoryContainsPlayers() {

        ArrayList<String> listOfPlayerNames = new ArrayList<>();
        String playerName = "Player One";
        listOfPlayerNames.add(playerName);

        when(mockPlayerManagerServiceImpl.getPlayerNames()).thenReturn(listOfPlayerNames);

        ArrayList<String> result = playerController.getPlayerNames();

        assertEquals("Player One", result.getFirst());

    }

    @Test
    @DisplayName("Returns empty ArrayList when PlayersRepository is empty")
    void testGetPlayerNames_WhenPlayersRepositoryIsEmpty() {

        ArrayList<String> emptyList = new ArrayList<>();

        when(mockPlayerManagerServiceImpl.getPlayerNames()).thenReturn(emptyList);

        ArrayList<String> result = playerController.getPlayerNames();

        assertTrue(result.isEmpty());

    }

    @Test
    @DisplayName("Returns empty String when passed empty String")
    void testGetPlayerRole_WhenPassedEmptyString() {

        String emptyPlayerName = "";

        when(mockPlayerManagerServiceImpl.getPlayerRole(emptyPlayerName)).thenReturn("");

        String result = playerController.getPlayerRole(emptyPlayerName);

        verify(mockPlayerManagerServiceImpl, times(1)).getPlayerRole(emptyPlayerName);
        assertTrue(result.isEmpty());

    }
}