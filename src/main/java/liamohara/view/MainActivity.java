package liamohara.view;

import liamohara.controller.GameController;
import liamohara.controller.GridController;
import liamohara.controller.PlayerController;
import liamohara.exception.GlobalExceptionHandler;
import liamohara.exception.IllegalMoveException;
import liamohara.exception.PlayerRoleTakenException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity {



    private PlayerController playerController = new PlayerController();
    private GridController gridController = new GridController();
    private GameController gameController = new GameController();
    private GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    public void run() {}

    protected ArrayList<String> welcome() {

        ArrayList<String> messages = new ArrayList<>();
        messages.add("Welcome to Noughts and Crosses!\n");
        messages.add("This is a two player game in which players place a nought or cross in a 3x3 grid.");
        messages.add("The first player to have three of their markers filling a row, column or diagonal wins.\n");
        messages.add("Do you wish to start a new game? [Y / N] and hit ENTER.\n");

        return messages;

    }

    protected void printMessages(ArrayList<String> messages) {

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

            while (isPlayerOneRoleInputInvalid) {
                System.out.println("Please enter the role of Player One. [O / X] and hit ENTER.");
                String playerOneRole = reader.readLine();

                if (playerOneRole.equals("O") || playerOneRole.equals("X")) {
                    isPlayerOneRoleInputInvalid = false;

                    if (playerOneRole.equals("O")) {
                        isPlayerOneNought = true;

                    } else {
                        isPlayerOneCross = true;

                    }
                } else {
                    System.out.println("Invalid input. Please enter O or X.");

                }
            }

            System.out.println("Please enter the name of Player Two: ");
            String playerTwoName = reader.readLine();

            while (isPlayerTwoRoleInputInvalid) {
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
        try {
            playerController.addNewPlayer(listOfPlayerNames.getFirst(), isPlayerOneNought, isPlayerOneCross);
            playerController.addNewPlayer(listOfPlayerNames.getLast(), isPlayerTwoNought, isPlayerTwoCross);

        } catch (PlayerRoleTakenException pre) {
            System.out.println(globalExceptionHandler.handlePlayerRoleTakenException(pre));

        }
    }

    protected void play() {}

    protected String[][] getPlayersData() {

        String[][] playersData = new String[2][4];
        String playerOneName;
        String playerTwoName;

        ArrayList<String> playerNames = playerController.getPlayerNames();

        if (!(playerNames.isEmpty())) {
            playerOneName = playerNames.getFirst();
            playersData[0][0] = playerOneName;
            playerTwoName = playerNames.getLast();
            playersData[1][0] = playerTwoName;

            playersData[0][1] = playerController.getPlayerRole(playerOneName);
            playersData[1][1] = playerController.getPlayerRole(playerTwoName);
            playersData[0][2] = Integer.toString(playerController.getPlayerMovesRemaining(playerOneName));
            playersData[1][2] = Integer.toString(playerController.getPlayerMovesRemaining(playerTwoName));
            playersData[0][3] = Integer.toString(playerController.getPlayerScore(playerOneName));
            playersData[1][3] = Integer.toString(playerController.getPlayerScore(playerTwoName));

            return playersData;

        }
        return playersData;

    }

    protected ArrayList<String> drawPlayerTable(String[][] tableData) {
        ArrayList<String> playerTable = new ArrayList<>();
        String[] tableHeaders = {"Name", "Role", "Moves Remaining", "Score"};
        int colOneWidth = tableHeaders[0].length();
        int colTwoWidth = tableHeaders[1].length();
        int colThreeWidth = tableHeaders[2].length();
        int colFourWidth = tableHeaders[3].length();
        String headerRow;
        String line;
        int lineLength;
        String playerOneRow;
        String playerTwoRow;


        for (int i = 0; i < tableData.length; i++) {
            if (tableData[i][0].length() > colOneWidth) {
                colOneWidth = tableData[i][0].length();

            }
            if (tableData[i][1].length() > colTwoWidth) {
                colTwoWidth = tableData[i][1].length();

            }
            if (tableData[i][2].length() > colThreeWidth) {
                colThreeWidth = tableData[i][2].length();

            }
            if (tableData[i][3].length() > colFourWidth) {
                colFourWidth = tableData[i][3].length();

            }
        }

        colOneWidth += 3;
        colTwoWidth += 3;
        colThreeWidth += 3;
        colFourWidth += 3;


        StringBuilder headerRowBuilder = new StringBuilder();
        String columnOne = "| " + tableHeaders[0];
        String columnTwo = "| " + tableHeaders[1];
        String columnThree = "| " + tableHeaders[2];
        String columnFour = "| " + tableHeaders[3];

        headerRowBuilder.append(columnOne);
        int colOneLoopCount = colOneWidth;
        colOneLoopCount -= columnOne.length();

        while (colOneLoopCount > 0) {
            headerRowBuilder.append(" ");
            colOneLoopCount--;

        }

        headerRowBuilder.append(columnTwo);
        int colTwoLoopCount = colTwoWidth;
        colTwoLoopCount -= columnTwo.length();

        while (colTwoLoopCount > 0) {
            headerRowBuilder.append(" ");
            colTwoLoopCount--;

        }

        headerRowBuilder.append(columnThree);
        int colThreeLoopCount = colThreeWidth;
        colThreeLoopCount -= columnThree.length();

        while (colThreeLoopCount > 0) {
            headerRowBuilder.append(" ");
            colThreeLoopCount--;

        }

        headerRowBuilder.append(columnFour);
        int colFourLoopCount = colFourWidth;
        colFourLoopCount -= columnFour.length();

        while (colFourLoopCount > 0) {
            headerRowBuilder.append(" ");
            colFourLoopCount--;

        }

        headerRowBuilder.append("|");
        headerRow = headerRowBuilder.toString();

        lineLength = headerRow.length();

        StringBuilder lineBuilder = new StringBuilder();

        while (lineLength > 0) {
            lineBuilder.append("-");
            lineLength--;

        }

        line = lineBuilder.toString();

        playerTable.add(headerRow);
        playerTable.add(line);

        if (tableData.length != 0) {

            StringBuilder playerOneRowBuilder = new StringBuilder();
            columnOne = "| " + tableData[0][0];
            columnTwo = "| " + tableData[0][1];
            columnThree = "| " + tableData[0][2];
            columnFour = "| " + tableData[0][3];

            playerOneRowBuilder.append(columnOne);
            colOneLoopCount = colOneWidth;
            colOneLoopCount -= columnOne.length();

            while (colOneLoopCount > 0) {
                playerOneRowBuilder.append(" ");
                colOneLoopCount--;

            }

            playerOneRowBuilder.append(columnTwo);
            colTwoLoopCount = colTwoWidth;
            colTwoLoopCount -= columnTwo.length();

            while (colTwoLoopCount > 0) {
                playerOneRowBuilder.append(" ");
                colTwoLoopCount--;

            }

            playerOneRowBuilder.append(columnThree);
            colThreeLoopCount = colThreeWidth;
            colThreeLoopCount -= columnThree.length();

            while (colThreeLoopCount > 0) {
                playerOneRowBuilder.append(" ");
                colThreeLoopCount--;

            }

            playerOneRowBuilder.append(columnFour);
            colFourLoopCount = colFourWidth;
            colFourLoopCount -= columnFour.length();

            while (colFourLoopCount > 0) {
                playerOneRowBuilder.append(" ");
                colFourLoopCount--;

            }

            playerOneRowBuilder.append("|");
            playerOneRow = playerOneRowBuilder.toString();

            playerTable.add(playerOneRow);

            StringBuilder playerTwoRowBuilder = new StringBuilder();
            columnOne = "| " + tableData[1][0];
            columnTwo = "| " + tableData[1][1];
            columnThree = "| " + tableData[1][2];
            columnFour = "| " + tableData[1][3];

            playerTwoRowBuilder.append(columnOne);
            colOneLoopCount = colOneWidth;
            colOneLoopCount -= columnOne.length();

            while (colOneLoopCount > 0) {
                playerTwoRowBuilder.append(" ");
                colOneLoopCount--;

            }

            playerTwoRowBuilder.append(columnTwo);
            colTwoLoopCount = colTwoWidth;
            colTwoLoopCount -= columnTwo.length();

            while (colTwoLoopCount > 0) {
                playerTwoRowBuilder.append(" ");
                colTwoLoopCount--;

            }

            playerTwoRowBuilder.append(columnThree);
            colThreeLoopCount = colThreeWidth;
            colThreeLoopCount -= columnThree.length();

            while (colThreeLoopCount > 0) {
                playerTwoRowBuilder.append(" ");
                colThreeLoopCount--;

            }

            playerTwoRowBuilder.append(columnFour);
            colFourLoopCount = colFourWidth;
            colFourLoopCount -= columnFour.length();

            while (colFourLoopCount > 0) {
                playerTwoRowBuilder.append(" ");
                colFourLoopCount--;

            }

            playerTwoRowBuilder.append("|");
            playerTwoRow = playerTwoRowBuilder.toString();

            playerTable.add(playerTwoRow);

        }
        return playerTable;

    }

    protected ArrayList<String> drawGrid(int gameId) {

        ArrayList<String> grid = new ArrayList<>();
        String[][] gridData = gridController.getGrid(gameId);
        int emptyCellCount = 0;
        boolean isGridDataEmpty = false;

        for (String[] s : gridData) {
            for (int i = 0; i < s.length; i++) {
                if (s[i] == null) {
                    emptyCellCount ++;

                } else {
                    break;

                }
            }
        }

        if (emptyCellCount == 9) {
            isGridDataEmpty = true;
        }

        String columnKey = "    1   2   3  ";
        String topBorder = "  ╔═══╤═══╤═══╗";
        String emptyDataRowOne = " 1║   │   │   ║";
        String dataRowOnePtOne = " 1║ ";
        String emptyDataRowTwo = " 2║   │   │   ║";
        String dataRowTwoPtOne = " 2║ ";
        String emptyDataRowThree = " 3║   │   │   ║";
        String dataRowThreePtOne = " 3║ ";
        String divider = "  ╟───┼───┼───╢";
        String bottomBorder = "  ╚═══╧═══╧═══╝";
        String dataRowDivider = " │ ";
        String dataRowRightBorder = " ║";

        if (!(isGridDataEmpty)) {
            StringBuilder dataRowOneBuilder = new StringBuilder();
            dataRowOneBuilder.append(dataRowOnePtOne).append((gridData[0][0] == null)? " ":gridData[0][0])
                    .append(dataRowDivider).append((gridData[0][1] == null)? " ":gridData[0][1])
                    .append(dataRowDivider).append((gridData[0][2] == null)? " ":gridData[0][2])
                    .append(dataRowRightBorder);

            String dataRowOne = dataRowOneBuilder.toString();

            StringBuilder dataRowTwoBuilder = new StringBuilder();
            dataRowTwoBuilder.append(dataRowTwoPtOne).append((gridData[1][0] == null)? " ":gridData[1][0])
                    .append(dataRowDivider).append((gridData[1][1] == null)? " ":gridData[1][1])
                    .append(dataRowDivider).append((gridData[1][2] == null)? " ":gridData[1][2])
                    .append(dataRowRightBorder);

            String dataRowTwo = dataRowTwoBuilder.toString();

            StringBuilder dataRowThreeBuilder = new StringBuilder();
            dataRowThreeBuilder.append(dataRowThreePtOne).append((gridData[2][0] == null)? " ":gridData[2][0])
                    .append(dataRowDivider).append((gridData[2][1] == null)? " ":gridData[2][1])
                    .append(dataRowDivider).append((gridData[2][2] == null)? " ":gridData[2][2])
                    .append(dataRowRightBorder);

            String dataRowThree = dataRowThreeBuilder.toString();

            grid.add(columnKey);
            grid.add(topBorder);
            grid.add(dataRowOne);
            grid.add(divider);
            grid.add(dataRowTwo);
            grid.add(divider);
            grid.add(dataRowThree);
            grid.add(bottomBorder);
            return grid;

        } else {
            grid.add(columnKey);
            grid.add(topBorder);
            grid.add(emptyDataRowOne);
            grid.add(divider);
            grid.add(emptyDataRowTwo);
            grid.add(divider);
            grid.add(emptyDataRowThree);
            grid.add(bottomBorder);

        }
        return grid;

    }

    protected int flipCoin() {
        Random rand = new Random();

        return rand.nextInt(2);

    }

    protected void playerMove(int gameId, String playerName) throws IOException {

        boolean isInputInvalid = true;
        boolean illegalMoveExceptionThrown = false;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (isInputInvalid) {
                int columnSelection;
                int rowSelection;

                System.out.println(playerName + " please enter column number [1 - 3] and hit ENTER.");
                columnSelection = (Integer.parseInt(reader.readLine())) - 1;

                if (!(columnSelection > 2 || columnSelection < 0)) {
                    System.out.println(playerName + " please enter row number [1 - 3] and hit ENTER.");
                    rowSelection = (Integer.parseInt(reader.readLine())) - 1;

                    if (!(rowSelection > 2 || rowSelection < 0)) {
                            try {
                                gridController.updateGrid(gameId, rowSelection, columnSelection, playerName);

                            } catch (IllegalMoveException ie) {
                                System.out.println(exceptionHandler.handleIllegalMoveException(ie));
                                illegalMoveExceptionThrown = true;

                            }
                            isInputInvalid = false;

                    } else {
                        isInputInvalid = true;

                    }
                } else {
                    isInputInvalid = true;

                }
                if (illegalMoveExceptionThrown) {
                    illegalMoveExceptionThrown = false;
                    isInputInvalid = true;

                }
            }
            playerController.updatePlayerMovesRemaining(playerName);
            gameController.updateMovesRemaining(gameId);

    }

    protected String[] analyseGame(int gameId, String playerOne, String playerTwo) {

        String[][] gridData = gridController.getGrid(gameId);
        String playerOneRole = playerController.getPlayerRole(playerOne);
        String playerTwoRole = playerController.getPlayerRole(playerTwo);
        int noughtCount = 0;
        int noughtRowCount = 0;
        int noughtColumnCount = 0;
        int crossCount = 0;
        int crossRowCount = 0;
        int crossColumnCount = 0;
        String gameResult;
        String winnerRole;
        String winnerName = "";
        String[] gameAnalysis = new String[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gridData[i][j] == null) {
                    gridData[i][j] = "";
                }
                else {
                    if (gridData[i][j].equalsIgnoreCase("O")) {
                        noughtRowCount++;

                    }
                }
            }
            if (noughtRowCount == 3) {
                noughtCount ++;

            }
            noughtRowCount = 0;
        }





        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gridData[i][j] == null) {
                    gridData[i][j] = "";
                }
                else {
                    if (gridData[i][j].equalsIgnoreCase("X")) {
                        crossRowCount++;

                    }
                }
            }
            if (crossRowCount == 3) {
                crossCount ++;

            }
            crossRowCount = 0;

        }


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gridData[j][i] == null) {
                    gridData[j][i] = "";
                }
                else {
                    if (gridData[j][i].equalsIgnoreCase("O")) {
                        noughtColumnCount++;

                    }
                }
            }
            if (noughtColumnCount == 3) {
                noughtCount ++;

            }
            noughtColumnCount = 0;

        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gridData[j][i] == null) {
                    gridData[j][i] = "";
                }
                else {
                    if (gridData[j][i].equalsIgnoreCase("X")) {
                        crossColumnCount++;

                    }
                }
            }
            if (crossColumnCount == 3) {
                crossCount ++;

            }
            crossColumnCount = 0;

        }

        if (gridData[0][0].equalsIgnoreCase("O") && gridData[1][1].equalsIgnoreCase("O") && gridData[2][2].equalsIgnoreCase("O")) {
            noughtCount ++;

        }

        if (gridData[0][0].equalsIgnoreCase("X") && gridData[1][1].equalsIgnoreCase("X") && gridData[2][2].equalsIgnoreCase("X")) {
            noughtCount ++;

        }

        if (gridData[0][2].equalsIgnoreCase("O") && gridData[1][1].equalsIgnoreCase("O") && gridData[2][0].equalsIgnoreCase("O")) {
            noughtCount ++;

        }

        if (gridData[0][2].equalsIgnoreCase("X") && gridData[1][1].equalsIgnoreCase("X") && gridData[2][0].equalsIgnoreCase("X")) {
            noughtCount ++;

        }

        if (noughtCount == crossCount) {
            gameResult = "draw";

        } else {
            gameResult = "won";

            if (noughtCount > crossCount) {
                winnerRole = "O";

            } else {
                winnerRole = "X";

            }
            if (winnerRole.equalsIgnoreCase(playerOneRole)) {
                winnerName = playerOne;

            } else {
                winnerName = playerTwo;

            }
            gameController.setWinner(gameId, winnerName);
            playerController.updatePlayerScore(winnerName);

        }
        gameAnalysis[0] = gameResult;

        if (winnerName != null) {
            gameAnalysis[1] = winnerName;

        }
        return gameAnalysis;

    }

    protected void postResult (int gameId, String winnerName) {

        gameController.setWinner(gameId, winnerName);
        playerController.updatePlayerScore(winnerName);

    }

}
