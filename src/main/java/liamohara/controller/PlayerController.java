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
        return playerManagerService.getPlayerMovesRemaining(playerName);
    }

    public int getPlayerScore(String playerName) {
        return playerManagerService.getPlayerScore(playerName);
    }

    public void updatePlayerScore(String playerName) {
        playerManagerService.updatePlayerScore(playerName);
    }

    public void addNewPlayer(String playerName, boolean isNought, boolean isCross) {
        playerManagerService.addNewPlayer(playerName, isNought, isCross);
    }

}
