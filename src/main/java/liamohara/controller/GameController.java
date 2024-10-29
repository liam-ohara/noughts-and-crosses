package liamohara.controller;

import liamohara.model.Game;
import liamohara.model.Player;
import liamohara.service.GameManagerService;
import liamohara.service.GameManagerServiceImpl;

public class GameController {

    GameManagerService gameManagerService = new GameManagerServiceImpl();

    public int startNewGame() {
        return gameManagerService.startNewGame();
    }

    public int getMovesRemaining(int gameId) {
        return 0;
    }

    public void updateMovesRemaining(int gameId) {}

    public void setWinner (int gameId, Player winner) {}
}
