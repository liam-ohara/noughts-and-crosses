package liamohara.model;

public class Game {

    private int id;

    private int movesRemaining = 9;

    private Player winner;

    public Game(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getMovesRemaining() {
        return movesRemaining;
    }

    public Player getWinner() {
        return winner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMovesRemaining(int movesRemaining) {
        this.movesRemaining = movesRemaining;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
