package liamohara.service;

public interface GridManagerService {

    void addNewGrid(int gameId);

    String getGrid();

    void updateGrid(int row, int column, boolean isNought);
}
