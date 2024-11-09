package liamohara.service;

import liamohara.model.Player;

public interface GridManagerService {

    void addNewGrid(int gameId);

    String[][] getGrid(int gameId);

    void updateGrid(int gameId, int row, int column, String playerName);
}
