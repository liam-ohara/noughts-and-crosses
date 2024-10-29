package liamohara.service;

import liamohara.model.Game;
import liamohara.repository.GamesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameManagerServiceImplTest {

    @Mock
    GamesRepository mockGamesRepository;

    @InjectMocks
    GameManagerServiceImpl gameManagerServiceImpl;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

    }

    Game firstGame = new Game(1);
    List<Game> listOfGames = new ArrayList<>();


    @Test
    @DisplayName("Returns 1 when new game is the first game")
    void testStartNewGame_WhenNewGameIsFirstGame() {

        when(mockGamesRepository.getListOfGames()).thenReturn(listOfGames);

        int result = gameManagerServiceImpl.startNewGame();

        verify(mockGamesRepository, times(1)).getListOfGames();

        assertEquals(1, result);

    }
}