package liamohara.service;

import liamohara.model.Player;

public interface GameManagerService {

    int startNewGame();

    int getMovesRemaining(int gameId);

    void updateMovesRemaining();

    void setWinner(Player winner);

}
