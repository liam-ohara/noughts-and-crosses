package liamohara.service;

import liamohara.exception.PlayerNameTakenException;
import liamohara.exception.PlayerRoleTakenException;
import liamohara.model.Player;
import liamohara.repository.PlayersRepository;

import java.util.List;

public class PlayerManagerServiceImpl implements PlayerManagerService {

    PlayersRepository playersRepository = PlayersRepository.getInstance();

    @Override
    public void addNewPlayer(String playerName, boolean isNought, boolean isCross) {

        List<Player> listOfPlayers = playersRepository.getListOfPlayers();
        Player newPlayer = new Player(playerName, isNought, isCross);

        if (listOfPlayers.isEmpty()) {
            playersRepository.addNewPlayer(newPlayer);

        } else {
            for (int i = 0; i < listOfPlayers.size(); i++) {
                if (listOfPlayers.get(i).getPlayerName().equalsIgnoreCase(newPlayer.getPlayerName())) {
                    throw new PlayerNameTakenException(newPlayer.getPlayerName());

                } else {
                    if (newPlayer.isCross() == listOfPlayers.get(i).isCross() || newPlayer.isNought() == listOfPlayers.get(i).isNought()) {
                        throw new PlayerRoleTakenException(listOfPlayers.get(i).getPlayerName());

                    } else {
                        playersRepository.addNewPlayer(newPlayer);

                    }
                }
            }
        }
    }

    @Override
    public void updatePlayerMovesRemaining(String playerName) {

        List<Player> listOfPlayers = playersRepository.getListOfPlayers();
        Player updatedPlayer;
        int movesRemaining;
        
        if (!(listOfPlayers.isEmpty())) {
            
            for (int i = 0; i < listOfPlayers.size(); i++) {
                if (listOfPlayers.get(i).getPlayerName().equalsIgnoreCase(playerName)) {
                    updatedPlayer = listOfPlayers.get(i);
                    movesRemaining = updatedPlayer.getMovesRemaining();

                    if (movesRemaining > 0) {
                        updatedPlayer.setMovesRemaining(movesRemaining - 1);
                        playersRepository.updatePlayer(updatedPlayer);

                    }
                }
            }
        }
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
