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
    @DisplayName("Calls addNewPlayer method in PlayersRepository once.")
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


}