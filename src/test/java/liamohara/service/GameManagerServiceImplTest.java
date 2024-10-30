package liamohara.service;

import liamohara.model.Game;
import liamohara.model.Player;
import liamohara.repository.GamesRepository;
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
    Game updatedFirstGame = new Game(1);
    List<Game> listOfGames = new ArrayList<>();
    Player playerOne = new Player("Player One", true, false);


    @Test
    @DisplayName("Returns 1 when new game is the first game")
    void testStartNewGame_WhenNewGameIsFirstGame() {

        when(mockGamesRepository.getListOfGames()).thenReturn(listOfGames);

        int result = gameManagerServiceImpl.startNewGame();

        verify(mockGamesRepository, times(1)).getListOfGames();

        assertEquals(1, result);

    }

    @Test
    @DisplayName("Returns 2 when new game is the second game")
    void testStartNewGame_WhenNewGameItSecondGame() {

        listOfGames.add(firstGame);

        when(mockGamesRepository.getListOfGames()).thenReturn(listOfGames);

        int result = gameManagerServiceImpl.startNewGame();

        verify(mockGamesRepository, times(1)).getListOfGames();

        assertEquals(2, result);

    }

    @Test
    @DisplayName("Returns 6 when at the start of new game when no players have moved")
    void testGetMovesRemaining_WhenNoPlayersHaveMoved() {

        listOfGames.add(firstGame);

        when(mockGamesRepository.getListOfGames()).thenReturn(listOfGames);

        int result = gameManagerServiceImpl.getMovesRemaining(firstGame.getId());

        verify(mockGamesRepository, times(1)).getListOfGames();

        assertEquals(6, result);

    }

    @Test
    @DisplayName("Returns 2 when both players have moved twice")
    void testGetMovesRemaining_WhenBothPlayersHaveMovedTwice() {

        firstGame.setMovesRemaining(2);
        listOfGames.add(firstGame);

        when(mockGamesRepository.getListOfGames()).thenReturn(listOfGames);

        int result = gameManagerServiceImpl.getMovesRemaining(firstGame.getId());

        verify(mockGamesRepository, times(1)).getListOfGames();

        assertEquals(2, result);

    }

    @Test
    @DisplayName("Reduces the number of moves remaining by 1 when a game has at least 1 move remaining")
    void testUpdateMovesRemaining_WhenGameHasTwoMovesRemaining() {

        firstGame.setMovesRemaining(2);
        updatedFirstGame.setMovesRemaining(1);
        listOfGames.add(firstGame);

        when(mockGamesRepository.getListOfGames()).thenReturn(listOfGames);

        gameManagerServiceImpl.updateMovesRemaining(firstGame.getId());

        verify(mockGamesRepository, times(1)).getListOfGames();
        verify(mockGamesRepository, times(1)).updateGame(Mockito.any());

        assertEquals(updatedFirstGame.getMovesRemaining(), gameManagerServiceImpl.getMovesRemaining(1));

    }

    @Test
    @DisplayName("Leave number of moves remaining unchanged when a game has no moves remaining")
    void testUpdateMovesRemaining_WhenGameHasNoMovesRemaining() {

        firstGame.setMovesRemaining(0);
        listOfGames.add(firstGame);

        when(mockGamesRepository.getListOfGames()).thenReturn(listOfGames);

        gameManagerServiceImpl.updateMovesRemaining(firstGame.getId());

        verify(mockGamesRepository, times(1)).getListOfGames();
        verify(mockGamesRepository, times(0)).updateGame(Mockito.any());

        assertEquals(0, firstGame.getMovesRemaining());

    }

    @Test
    @DisplayName("Sets winning player to game attribute when game has ended")
    void testSetWinner_WhenGameHasNoMovesRemaining() {

        firstGame.setMovesRemaining(0);
        listOfGames.add(firstGame);
        String nameOfPlayerOne = "Player One";

        when(mockGamesRepository.getListOfGames()).thenReturn(listOfGames);

        gameManagerServiceImpl.setWinner(firstGame.getId(), playerOne);

        verify(mockGamesRepository, times(1)).getListOfGames();
        verify(mockGamesRepository, times(1)).updateGame(Mockito.any());

        assertEquals(nameOfPlayerOne, firstGame.getWinner().getPlayerName());

    }

    @Test
    @DisplayName("Leaves winning player of game unset when game has not ended")
    void testSetWinner_WhenGameHasMovesRemaining() {

        firstGame.setMovesRemaining(1);
        listOfGames.add(firstGame);
        String playerName = null;

        when(mockGamesRepository.getListOfGames()).thenReturn(listOfGames);

        gameManagerServiceImpl.setWinner(firstGame.getId(), playerOne);

        verify(mockGamesRepository, times(1)).getListOfGames();
        verify(mockGamesRepository, times(0)).updateGame(Mockito.any());

        try {
            assertNull(firstGame.getWinner().getPlayerName());
        } catch (NullPointerException e) {
            playerName = null;
        }

        assertNull(playerName);

    }


}