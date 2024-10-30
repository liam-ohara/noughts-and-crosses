package liamohara.controller;

import liamohara.model.Player;
import liamohara.service.GridManagerService;
import liamohara.service.GridManagerServiceImpl;

public class GridController {

    GridManagerService gridManagerService = new GridManagerServiceImpl();

    public void addNewGrid(int gameId) {
        gridManagerService.addNewGrid(gameId);

    }

    public String getGrid() {
        return "";
    }

    public void updateGrid(int row, int column, Player player) {}

}
