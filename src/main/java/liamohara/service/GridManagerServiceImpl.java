package liamohara.service;

import liamohara.model.Player;

public class GridManagerServiceImpl implements GridManagerService {

    @Override
    public void addNewGrid(int gameId) {

    }

    @Override
    public String[][] getGrid(int gameId) {
        return new String[3][3];
    }

    @Override
    public void updateGrid(int gameId, int row, int column, Player player) {

    }
}
