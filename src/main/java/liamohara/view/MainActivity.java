package liamohara.view;

import liamohara.controller.PlayerController;
import liamohara.exception.GlobalExceptionHandler;
import liamohara.exception.PlayerRoleTakenException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity {

    private PlayerController playerController = new PlayerController();

    public void run() throws IOException {
        setup();
    }

    protected ArrayList<String> welcome(){

        ArrayList<String> messages = new ArrayList<>();
        messages.add("Welcome to Noughts and Crosses!\n");
        messages.add("This is a two player game in which player place a nought or cross in a 3x3 grid.");
        messages.add("The first player to have three of their markers filling a row, column or diagonal wins.\n");
        messages.add("Do you wish to start a new game? [Y / N] and hit ENTER.\n");

        return messages;

    }

    protected void printMessages (ArrayList<String> messages) {

        if (messages.isEmpty()) {
            System.out.print("");

        } else {

            StringBuilder string = new StringBuilder();

            for (int i = 0; i < messages.size(); i++) {
                if (i < messages.size() - 1) {
                    string.append(messages.get(i)).append("\n");

                } else {
                    string.append(messages.get(i));

                }
            }
            System.out.println(string);
        }

    }

    protected void setup() throws IOException {

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        boolean isPlayerOneRoleInputInvalid = true;
        boolean isPlayerTwoRoleInputInvalid = true;
        boolean isPlayerOneNought = false;
        boolean isPlayerOneCross = false;
        boolean isPlayerTwoNought = false;
        boolean isPlayerTwoCross = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> listOfPlayerNames = playerController.getPlayerNames();


        while (listOfPlayerNames.size() < 2) {
            System.out.println("Please enter the name of Player One: ");
            String playerOneName = reader.readLine();

            while(isPlayerOneRoleInputInvalid) {
                System.out.println("Please enter the role of Player One. [O / N] and hit ENTER.");
                String playerOneRole = reader.readLine();

                if (playerOneRole.equals("O") || playerOneRole.equals("N")) {
                    isPlayerOneRoleInputInvalid = false;

                    if (playerOneRole.equals("O")) {
                        isPlayerOneNought = true;

                    } else {
                        isPlayerOneCross = true;

                    }
                } else {
                    System.out.println("Invalid input. Please enter O or N.");

                }
            }

            System.out.println("Please enter the name of Player Two: ");
            String playerTwoName = reader.readLine();

            while(isPlayerTwoRoleInputInvalid) {
                System.out.println("Please enter the role of Player Two. [O / X] and hit ENTER.");
                String playerTwoRole = reader.readLine();

                if (playerTwoRole.equals("O") || playerTwoRole.equals("X")) {
                    isPlayerTwoRoleInputInvalid = false;

                    if (playerTwoRole.equals("O")) {
                        isPlayerTwoNought = true;

                    } else {
                        isPlayerTwoCross = true;

                    }
                } else {
                    System.out.println("Invalid input. Please enter O or X.");

                }
            }
            if (playerOneName.equalsIgnoreCase(playerTwoName)) {
                System.out.println("Player One name is \"" + playerOneName + "\" and Player Two name is \"" + playerTwoName + ".");
                System.out.println("Player names must be unique. Please try again.");

            } else {
                listOfPlayerNames.add(playerOneName);
                listOfPlayerNames.add(playerTwoName);

            }

        }
        //Try catch for player exceptions
        try {
            playerController.addNewPlayer(listOfPlayerNames.getFirst(), isPlayerOneNought, isPlayerOneCross);
            playerController.addNewPlayer(listOfPlayerNames.getLast(), isPlayerTwoNought, isPlayerTwoCross);
        } catch (PlayerRoleTakenException pre) {
            System.out.println(globalExceptionHandler.handlePlayerRoleTakenException(pre));
        }

    }




}
