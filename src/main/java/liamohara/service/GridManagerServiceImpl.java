package liamohara.service;

import liamohara.exception.GameIdAlreadyAssignedException;
import liamohara.exception.GridNotFoundException;
import liamohara.exception.IllegalMoveException;
import liamohara.exception.NoGridsException;
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

        List<Grid> listOfGrids = new ArrayList<>();

        listOfGrids = gridsRepository.getListOfGrids();

        if (listOfGrids.isEmpty()) {
            throw new NoGridsException("No grids created yet.");

        }

        Grid grid = null;

        for (int i = 0; i < listOfGrids.size(); i++) {
            if (listOfGrids.get(i).getGameId() == gameId) {
                grid = listOfGrids.get(i);

            } else {
                throw new GridNotFoundException(Integer.toString(gameId));

            }
        }
        return grid.getGrid();

    }

    @Override
    public void updateGrid(int gameId, int row, int column, Player player) {

        List<Grid> listOfGrids = gridsRepository.getListOfGrids();

        Grid updatedGrid = null;
        String playerSymbol;
        String[][] updatedGridData;

        if (listOfGrids.isEmpty()) {
            throw new NoGridsException("No grids created yet.");

        }

        for (int i = 0; i < listOfGrids.size(); i++) {
            if (listOfGrids.get(i).getGameId() == gameId) {
                updatedGrid = listOfGrids.get(i);

            } else {
                throw new GridNotFoundException(Integer.toString(gameId));

            }
        }

        if (player.isNought()) {
            playerSymbol = "O";

        } else {
            playerSymbol = "X";

        }

        updatedGridData = updatedGrid.getGrid();

        if (updatedGridData[row][column].isEmpty()) {
            updatedGridData[row][column] = playerSymbol;
            updatedGrid.setGrid(updatedGridData);

            gridsRepository.updateGrid(updatedGrid);

        } else {
            throw new IllegalMoveException("Position: row " + Integer.toString(row) + "by column " + Integer.toString(column));

        }
    }


}
