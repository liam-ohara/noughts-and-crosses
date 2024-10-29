package liamohara.controller;

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

}