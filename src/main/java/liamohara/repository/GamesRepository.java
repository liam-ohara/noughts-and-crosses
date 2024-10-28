package liamohara.repository;

import liamohara.model.Game;

import java.util.List;

public class GamesRepository {

    private List<Game> listOfGames;

    public GamesRepository(List<Game> listOfGames) {
        this.listOfGames = listOfGames;
    }

    public List<Game> getListOfGames() {
        return listOfGames;
    }

    public void addNewGame(Game newGame) {
        listOfGames.add(newGame);
    }
}
