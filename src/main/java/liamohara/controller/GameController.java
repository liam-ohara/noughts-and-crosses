package liamohara.controller;

import liamohara.model.Game;
import liamohara.model.Player;

public class GameController {

    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public int getMovesRemaining(int gameId) {
        return 0;
    }

    public void updateMovesRemaining(int gameId) {}

    public void setWinner (int gameId, Player winner) {}
}
