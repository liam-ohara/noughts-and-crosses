package liamohara.controller;

import liamohara.model.Player;
import liamohara.service.PlayerManagerService;
import liamohara.service.PlayerManagerServiceImpl;

import java.util.List;

public class PlayerController {

    PlayerManagerService playerManagerService = new PlayerManagerServiceImpl();


    public void updatePlayerMovesRemaining(String playerName) {
        playerManagerService.updatePlayerMovesRemaining(playerName);
    }

    public int getPlayerMovesRemaining(String playerName) {
        return 0;
    }

    public int getPlayerScore(String playerName) {
        return 0;
    }

    public void updatePlayerScore(String playerName) {}

}
