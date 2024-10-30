package liamohara.service;

public interface GridService {

    String getGrid();

    void updateGrid(int row, int column, boolean isNought);
}
