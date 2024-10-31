package liamohara.view;

import java.util.ArrayList;

public class MainActivity {

    public void run(){}

    protected ArrayList<String> welcome(){

        ArrayList<String> messages = new ArrayList<>();
        messages.add("Welcome to Noughts and Crosses!\n");
        messages.add("This is a two player game in which player place a nought or cross in a 3x3 grid.");
        messages.add("The first player to have three of their markers filling a row, column or diagonal wins.\n");
        messages.add("Do you wish to start a new game? [Y / N] and hit ENTER.\n");

        return messages;

    }


}
