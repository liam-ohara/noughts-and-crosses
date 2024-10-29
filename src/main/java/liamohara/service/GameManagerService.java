package liamohara.service;

import liamohara.model.Player;

public interface GameManagerService {

    int startNewGame();

    int getMovesRemaining(int gameId);

    void updateMovesRemaining(int gameId);

    void setWinner(Player winner);

}
