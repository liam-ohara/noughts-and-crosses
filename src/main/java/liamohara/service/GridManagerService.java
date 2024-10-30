package liamohara.service;

public interface GridManagerService {

    void addNewGrid(int gameId);

    String[][] getGrid();

    void updateGrid(int gameId, int row, int column, boolean isNought);
}
