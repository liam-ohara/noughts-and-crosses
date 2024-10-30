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

    public void updateGame(Game game) {
        for (int i = 0; i < listOfGames.size(); i++) {

            if (listOfGames.get(i).getId() == game.getId()) {
                listOfGames.set(i, game);

            }
        }
    }
}
