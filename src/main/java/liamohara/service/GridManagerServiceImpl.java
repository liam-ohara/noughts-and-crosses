package liamohara.service;

import liamohara.exception.GameIdAlreadyAssignedException;
import liamohara.model.Grid;
import liamohara.model.Player;
import liamohara.repository.GridsRepository;

import java.util.ArrayList;
import java.util.List;

public class GridManagerServiceImpl implements GridManagerService {

    GridsRepository gridsRepository = GridsRepository.getInstance();

    @Override
    public void addNewGrid(int gameId) {

        List<Grid> listOfGrids;
        String[][] newGridData = new String[3][3];
        Grid newGrid = new Grid(gameId);
        newGrid.setGrid(newGridData);

        listOfGrids = gridsRepository.getListOfGrids();

        if (listOfGrids.isEmpty()) {
            gridsRepository.addNewGrid(newGrid);

        } else {

            for (int i = 0; i < listOfGrids.size(); i++) {
                if (listOfGrids.get(i).getGameId() == gameId) {
                    throw new GameIdAlreadyAssignedException(Integer.toString(gameId));

                } else {
                    gridsRepository.addNewGrid(newGrid);

                }
            }
        }
    }

    @Override
    public String[][] getGrid(int gameId) {

        List<Grid> listOfGrids = gridsRepository.getListOfGrids();
        Grid grid = null;

        for (int i = 0; i < listOfGrids.size(); i++) {
            if (listOfGrids.get(i).getGameId() == gameId) {
                grid = listOfGrids.get(i);

            }
        }
        return grid.getGrid();

    }

    @Override
    public void updateGrid(int gameId, int row, int column, Player player) {

    }
}
