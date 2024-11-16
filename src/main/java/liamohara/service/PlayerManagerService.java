package liamohara.service;

import java.util.ArrayList;

public interface PlayerManagerService {

    void addNewPlayer(String playerName, boolean isNought, boolean isCross);

    void updatePlayerMovesRemaining(String playerName);

    int getPlayerMovesRemaining(String playerName);

    int getPlayerScore(String playerName);

    void updatePlayerScore(String playerName);

    ArrayList<String> getPlayerNames();

    String getPlayerRole(String playerName);

    void resetPlayerMovesRemaining(String playerName);

}
