package liamohara.controller;

import liamohara.model.Player;

import java.util.List;

public class PlayerController {

    private List<Player> listOfPlayers;

    public PlayerController(List<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public void updatePlayerMovesRemaining(String playerName) {}

    public int getPlayerMovesRemaining(String playerName) {
        return 0;
    }

    public int getPlayerScore(String playerName) {
        return 0;
    }

    public void updatePlayerScore(String playerName) {}

}
