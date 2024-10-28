package liamohara.model;

public class Player {

    String playerName;

    int movesRemaining = 3;

    int playerScore = 0;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getMovesRemaining() {
        return movesRemaining;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setMovesRemaining(int movesRemaining) {
        this.movesRemaining = movesRemaining;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
}
