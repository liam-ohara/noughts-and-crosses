package liamohara.service;

import liamohara.model.Player;

public interface GameManagerService {

    int getMovesRemaining();

    void updateMovesRemaining();

    void setWinner(Player winner);

}
