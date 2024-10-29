package liamohara.service;

import liamohara.model.Game;
import liamohara.model.Player;
import liamohara.repository.GamesRepository;

import java.util.ArrayList;
import java.util.List;

public class GameManagerServiceImpl implements GameManagerService {

    GamesRepository gamesRepository = GamesRepository.getInstance();

    @Override
    public int startNewGame() {

        List<Game> listOfGames;

        listOfGames = gamesRepository.getListOfGames();

        int newGameId = 0;

        if (listOfGames.isEmpty()) {
            Game firstGame = new Game(1);
            gamesRepository.addNewGame(firstGame);
            return 1;

        } else {
            int lastGameId = listOfGames.getLast().getId();
            Game nextGame = new Game(lastGameId + 1);
            newGameId = nextGame.getId();
            gamesRepository.addNewGame(nextGame);

        }
        return newGameId;
    }

    @Override
    public int getMovesRemaining(int gameId) {
        return 0;
    }

    @Override
    public void updateMovesRemaining(int gameId) {
    }

    @Override
    public void setWinner(int gameId, Player winner) {

    }
}
