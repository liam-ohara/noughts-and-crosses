package liamohara.repository;

import liamohara.model.Grid;

import java.util.ArrayList;
import java.util.List;

public class GridsRepository {

    private static GridsRepository INSTANCE;
    private List<Grid> listOfGrids = new ArrayList<>();

    private GridsRepository() {}

    public static GridsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GridsRepository();
        }
        return INSTANCE;
    }

    public List<Grid> getListOfGrids() {
        return listOfGrids;
    }

    public void addNewGrid (Grid newGrid) {
        listOfGrids.add(newGrid);
    }

    public void updateGrid(Grid grid) {
        for (int i = 0; i < listOfGrids.size(); i++) {

            if(listOfGrids.get(i).getGameId() == grid.getGameId()) {
                listOfGrids.set(i, grid);

            }
        }
    }

}
