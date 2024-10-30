package liamohara.model;

public class Grid {

    private String[][] grid = new String[3][3];
    private int gameId;

    public Grid(int gameId) {
        this.gameId = gameId;
    }

    public String[][] getGrid() {
        return grid;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGrid(String[][] grid, int gameId) {
        this.grid = grid;
    }
}
