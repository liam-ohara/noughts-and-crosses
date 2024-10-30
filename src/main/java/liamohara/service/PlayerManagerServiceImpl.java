package liamohara.service;

import liamohara.model.Player;
import liamohara.repository.PlayersRepository;

import java.util.List;

public class PlayerManagerServiceImpl implements PlayerManagerService {

    PlayersRepository playersRepository = PlayersRepository.getInstance();

    @Override
    public void addNewPlayer(String playerName, boolean isNought, boolean isCross) {

        List<Player> listOfPlayers = playersRepository.getListOfPlayers();

        Player newPlayer = new Player(playerName, isNought, isCross);

        playersRepository.addNewPlayer(newPlayer);

    }

    @Override
    public void updatePlayerMovesRemaining(String playerName) {

    }

    @Override
    public int getPlayerMovesRemaining(String playerName) {
        return 0;
    }

    @Override
    public int getPlayerScore(String playerName) {
        return 0;
    }

    @Override
    public void updatePlayerScore(String playerName) {

    }
}
