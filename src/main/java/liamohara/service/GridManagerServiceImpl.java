package liamohara.service;

import liamohara.model.Grid;
import liamohara.model.Player;
import liamohara.repository.GridsRepository;

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

        gridsRepository.addNewGrid(newGrid);

    }

    @Override
    public String[][] getGrid(int gameId) {
        return new String[3][3];
    }

    @Override
    public void updateGrid(int gameId, int row, int column, Player player) {

    }
}
