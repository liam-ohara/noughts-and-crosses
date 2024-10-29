package liamohara.controller;

import liamohara.model.Player;
import liamohara.service.GameManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Mock
    GameManagerServiceImpl mockGameManagerServiceImpl;

    @InjectMocks
    GameController gameController;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Returns 1 when new game is the first game")
    void testStartNewGame_WhenNewGameIsFirstGame() {

        when(mockGameManagerServiceImpl.startNewGame()).thenReturn(1);

        int result = gameController.startNewGame();

        verify(mockGameManagerServiceImpl, times(1)).startNewGame();
        assertEquals(1, result);

    }

    @Test
    @DisplayName("Returns 6 when at the start of new game when no players have moved")
    void testGetMovesRemaining_WhenNoPlayersHaveMoved() {

        int gameId = 1;
        int expectedMovesRemaining = 6;

        when(mockGameManagerServiceImpl.getMovesRemaining(gameId)).thenReturn(6);

        int result = gameController.getMovesRemaining(gameId);

        verify(mockGameManagerServiceImpl, times(1)).getMovesRemaining(gameId);
        assertEquals(expectedMovesRemaining, result);

    }

    @Test
    @DisplayName("Calls updateMovesRemaining method in Service layer once")
    void testUpdateMovesRemaining() {

        int gameId = 1;

        gameController.updateMovesRemaining(gameId);

        verify(mockGameManagerServiceImpl, times(1)).updateMovesRemaining(gameId);

    }

    @Test
    @DisplayName("Calls setWinner method in Service layer once")
    void testSetWinner() {

        int gameId = 1;
        Player player = new Player("Player One", true, false);

        gameController.setWinner(gameId, player);

        verify(mockGameManagerServiceImpl, times(1)).setWinner(gameId, player);

    }
}