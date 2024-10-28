package liamohara.model;

public class Player {

    String playerName;

    int movesRemaining = 3;

    int playerScore = 0;

    boolean isNought = false;

    boolean isCross = false;

    public Player(String playerName, boolean isNought, boolean isCross) {
        this.playerName = playerName;
        this.isNought = isNought;
        this.isCross = isCross;
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

    public boolean isNought() {
        return isNought;
    }

    public boolean isCross() {
        return isCross;
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

    public void setNought(boolean nought) {
        isNought = nought;
    }

    public void setCross(boolean cross) {
        isCross = cross;
    }
}
