package liamohara.service;

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


    }


}