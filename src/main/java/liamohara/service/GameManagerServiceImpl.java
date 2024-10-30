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

        int newGameId;

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

        List<Game> listOfGames;

        listOfGames = gamesRepository.getListOfGames();

        int movesRemaining = 0;

        if (!(listOfGames.isEmpty())) {
            for (int i = 0; i < listOfGames.size(); i++) {

                if (listOfGames.get(i).getId() == gameId) {
                    movesRemaining = listOfGames.get(i).getMovesRemaining();

                }
            }
            return movesRemaining;

        }
        return movesRemaining;

    }

    @Override
    public void updateMovesRemaining(int gameId) {

        List<Game> listOfGames;
        listOfGames = gamesRepository.getListOfGames();
        Game updatedGame;
        int movesRemaining;

        if (!(listOfGames.isEmpty())) {
            for (int i = 0; i < listOfGames.size(); i++) {

                if (listOfGames.get(i).getId() == gameId) {
                    movesRemaining = listOfGames.get(i).getMovesRemaining();
                    updatedGame = listOfGames.get(i);


                    if (movesRemaining > 0) {
                        updatedGame.setMovesRemaining(movesRemaining -1);
                        gamesRepository.updateGame(updatedGame);

                    }
                }
            }
        }
    }

    @Override
    public void setWinner(int gameId, Player winner) {

        List<Game> listOfGames;
        listOfGames = gamesRepository.getListOfGames();
        Game updatedGame;
        int movesRemaining;

        if (!(listOfGames.isEmpty())) {
            for (int i = 0; i < listOfGames.size(); i++) {

                if (listOfGames.get(i).getId() == gameId) {
                    movesRemaining = listOfGames.get(i).getMovesRemaining();
                    updatedGame = listOfGames.get(i);

                    if (movesRemaining == 0) {
                        updatedGame.setWinner(winner);
                        gamesRepository.updateGame(updatedGame);
                    }
                }
            }
        }
    }
}
