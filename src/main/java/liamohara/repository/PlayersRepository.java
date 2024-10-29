package liamohara.repository;

import liamohara.model.Player;

import java.util.ArrayList;
import java.util.List;

public final class PlayersRepository {

    private static PlayersRepository INSTANCE;
    private List<Player> listOfPlayers = new ArrayList<>();

    private PlayersRepository() {}

    public static PlayersRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayersRepository();
        }
        return INSTANCE;
    }

    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public void addNewPlayer (Player newPlayer) {
        listOfPlayers.add(newPlayer);
    }
}
