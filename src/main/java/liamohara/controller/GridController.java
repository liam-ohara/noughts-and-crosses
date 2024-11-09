package liamohara.controller;

import liamohara.model.Player;
import liamohara.service.GridManagerService;
import liamohara.service.GridManagerServiceImpl;

public class GridController {

    GridManagerService gridManagerService = new GridManagerServiceImpl();

    public void addNewGrid(int gameId) {
        gridManagerService.addNewGrid(gameId);

    }

    public String[][] getGrid(int gameId) {
        return gridManagerService.getGrid(gameId);

    }

    //Parameters need to be modified to accept player name
    public void updateGrid(int gameId, int row, int column, String playerName) {
        gridManagerService.updateGrid(gameId, row, column, playerName);
    }

}
