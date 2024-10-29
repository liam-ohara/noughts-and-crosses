package liamohara.repository;

import liamohara.model.Game;

import java.util.ArrayList;
import java.util.List;

public final class GamesRepository {

    private static GamesRepository INSTANCE;
    private List<Game> listOfGames = new ArrayList<>();

    private GamesRepository() {}

    public static GamesRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GamesRepository();
        }
        return INSTANCE;
    }

    public List<Game> getListOfGames() {
        return listOfGames;
    }

    public void addNewGame(Game newGame) {
        listOfGames.add(newGame);
    }
}
