package liamohara.service;

public interface PlayerManagerService {

    void updatePlayerMovesRemaining(String playerName);

    int getPlayerMovesRemaining(String playerName);

    int getPlayerScore(String playerName);

    void updatePlayerScore(String playerName);

}
