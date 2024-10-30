package liamohara.service;

public interface GridManagerService {

    void addNewGrid(String[][] grid, int gameId);

    String getGrid();

    void updateGrid(int row, int column, boolean isNought);
}
