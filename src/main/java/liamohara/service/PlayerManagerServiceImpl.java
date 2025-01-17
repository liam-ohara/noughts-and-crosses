package liamohara.service;

import liamohara.exception.PlayerNameTakenException;
import liamohara.exception.PlayerRoleTakenException;
import liamohara.model.Player;
import liamohara.repository.PlayersRepository;

import java.util.ArrayList;
import java.util.List;

public class PlayerManagerServiceImpl implements PlayerManagerService {

    PlayersRepository playersRepository = PlayersRepository.getInstance();

    @Override
    public void addNewPlayer(String playerName, boolean isNought, boolean isCross) {

        List<Player> listOfPlayers = playersRepository.getListOfPlayers();
        Player newPlayer = new Player(playerName, isNought, isCross);
        String newPlayerRole;
        String newPlayerRoleReassigned;

        if (listOfPlayers.isEmpty()) {
            playersRepository.addNewPlayer(newPlayer);

        } else {
            for (int i = 0; i < listOfPlayers.size(); i++) {
                if (listOfPlayers.get(i).getPlayerName().equalsIgnoreCase(newPlayer.getPlayerName())) {
                    throw new PlayerNameTakenException(newPlayer.getPlayerName());

                } else {
                    if (newPlayer.isCross() == listOfPlayers.get(i).isCross() || newPlayer.isNought() == listOfPlayers.get(i).isNought()) {

                        if (newPlayer.isNought()) {
                            newPlayerRole = "Nought";
                            newPlayerRoleReassigned = "cross";

                        } else {
                            newPlayerRole = "Cross";
                            newPlayerRoleReassigned = "nought";

                        }

                        newPlayer.setNought(!newPlayer.isNought());
                        newPlayer.setCross(!newPlayer.isCross());
                        playersRepository.addNewPlayer(newPlayer);
                        throw new PlayerRoleTakenException(newPlayerRole + " role is taken by " + listOfPlayers.get(i).getPlayerName() + ". " + newPlayer.getPlayerName() + " has thus been assigned the role of " + newPlayerRoleReassigned + ".");

                    }
                }
            }
            playersRepository.addNewPlayer(newPlayer);
        }
    }

    @Override
    public void updatePlayerMovesRemaining(String playerName) {

        List<Player> listOfPlayers = playersRepository.getListOfPlayers();
        Player updatedPlayer;
        int movesRemaining;

        if (!(listOfPlayers.isEmpty())) {

            for (int i = 0; i < listOfPlayers.size(); i++) {
                if (listOfPlayers.get(i).getPlayerName().equalsIgnoreCase(playerName)) {
                    updatedPlayer = listOfPlayers.get(i);
                    movesRemaining = updatedPlayer.getMovesRemaining();

                    if (movesRemaining > 0) {
                        updatedPlayer.setMovesRemaining(movesRemaining - 1);
                        playersRepository.updatePlayer(updatedPlayer);

                    }
                }
            }
        }
    }

    @Override
    public int getPlayerMovesRemaining(String playerName) {

        List<Player> listOfPlayers = playersRepository.getListOfPlayers();

        if (!(listOfPlayers.isEmpty())) {

            for (int i = 0; i < listOfPlayers.size(); i++) {
                if (listOfPlayers.get(i).getPlayerName().equalsIgnoreCase(playerName)) {
                    return (listOfPlayers.get(i).getMovesRemaining());

                }
            }
        }
        return 0;
    }

    @Override
    public int getPlayerScore(String playerName) {

        List<Player> listOfPlayers = playersRepository.getListOfPlayers();

        if (!(listOfPlayers.isEmpty())) {

            for (int i = 0; i < listOfPlayers.size(); i++) {
                if (listOfPlayers.get(i).getPlayerName().equalsIgnoreCase(playerName)) {
                    return (listOfPlayers.get(i).getPlayerScore());

                }
            }
        }
        return 0;

    }

    @Override
    public void updatePlayerScore(String playerName) {

        List<Player> listOfPlayers = playersRepository.getListOfPlayers();
        Player updatedPlayer;
        int playerScore;

        if (!(listOfPlayers.isEmpty())) {

            for (int i = 0; i < listOfPlayers.size(); i++) {
                if (listOfPlayers.get(i).getPlayerName().equalsIgnoreCase(playerName)) {
                    updatedPlayer = listOfPlayers.get(i);

                    if (updatedPlayer.getMovesRemaining() < 3) {
                        playerScore = updatedPlayer.getPlayerScore();
                        updatedPlayer.setPlayerScore(playerScore + 1);
                        playersRepository.updatePlayer(updatedPlayer);

                    }
                }
            }
        }
    }

    @Override
    public ArrayList<String> getPlayerNames() {

        ArrayList<String> listOfPlayerNames = new ArrayList<>();

        try {
            List<Player> listOfPlayers = playersRepository.getListOfPlayers();

            for (Player p : listOfPlayers) {
                listOfPlayerNames.add(p.getPlayerName());

            }
            return listOfPlayerNames;

        } catch (NullPointerException e) {
            return listOfPlayerNames;

        }
    }

    @Override
    public String getPlayerRole(String playerName) {

        String playerRole = "";

        if (!(playerName.isEmpty())) {
            boolean isPlayerNought = false;
            boolean isPlayerCross = false;

            try {
            List<Player> listOfPlayers = playersRepository.getListOfPlayers();

            for (Player p : listOfPlayers) {
                if (p.getPlayerName().equalsIgnoreCase(playerName)) {
                    isPlayerNought = p.isNought();
                    isPlayerCross = p.isCross();

                }
            }
            if (isPlayerNought) {
                playerRole = "O";

            } else if (isPlayerCross) {
                playerRole = "X";

            } else {
                return "";

            }

        } catch (NullPointerException e) {
                return "";

            }
        }
        return playerRole;

    }

    @Override
    public void resetPlayerMovesRemaining(String playerName) {

        try {
            List<Player> listOfPlayers = playersRepository.getListOfPlayers();

            for (Player p : listOfPlayers) {
                if (p.getPlayerName().equalsIgnoreCase(playerName)) {
                    if (p.getMovesRemaining() < 3) {
                        p.setMovesRemaining(5);
                        playersRepository.updatePlayer(p);

                    }
                }
            }
        } catch (NullPointerException ignored) {}
    }

}
