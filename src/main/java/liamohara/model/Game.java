package liamohara.model;

public class Game {

    private int id;

    private Player winner;

    public Game(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Player getWinner() {
        return winner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
