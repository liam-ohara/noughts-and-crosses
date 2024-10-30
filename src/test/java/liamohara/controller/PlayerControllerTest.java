package liamohara.controller;

import liamohara.service.PlayerManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
}