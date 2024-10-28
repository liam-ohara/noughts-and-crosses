package liamohara.service;

public interface GameService {

    String getGrid();

    void updateGrid(int row, int column, boolean isNought);
}
