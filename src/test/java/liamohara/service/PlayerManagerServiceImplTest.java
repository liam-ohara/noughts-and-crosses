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

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);
        doThrow(PlayerRoleTakenException.class).when(mockPlayersRepository).addNewPlayer(duplicateRole);

        assertThrowsExactly(PlayerRoleTakenException.class, () ->  playerManagerServiceImpl.addNewPlayer(duplicateRole.getPlayerName(), duplicateRole.isNought(), duplicateRole.isCross()));

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        verify(mockPlayersRepository, times(0)).addNewPlayer(Mockito.any());

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
        int expectedMovesRemaining = 3;

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
    @DisplayName("Returns 0 when player has not won a game")
    void testGetPlayerScore_WhenPlayerHasNotWonAGame() {

        listOfPlayers.add(playerOne);
        int expectedPlayerScore = 0;

        when(mockPlayersRepository.getListOfPlayers()).thenReturn(listOfPlayers);

        int result = playerManagerServiceImpl.getPlayerScore(playerOne.getPlayerName());

        verify(mockPlayersRepository, times(1)).getListOfPlayers();
        assertEquals(expectedPlayerScore, result);

    }

}