# Noughts and Crosses
This is a Java implementation of the two-player game noughts and crosses (also known as tic-tac-toe), in which players place a nought or cross in a three-by-three grid. The first player to have three of their markers filling a row, column or diagonal wins.

## Programming approach
This application was written in Java 21 using:
* Object-oriented programming paradigm
* Model–view–controller (MVC) design pattern
* Test-driven development methodology

## Features
This game has the following features:
>* Players are given a welcome message explaining the game
>* Players can enter a player name
>* The first player can choose their marker (nought or cross)
>* The starting player for each game is selected at random
>* Players are shown a visual representation of the grid that is updated after each move
>* Players are advised of illegal moves and prompted to re-enter their move
>* Once the minimum number of moves required to win a game have been played, the game checks for a win and after every subsequent move until no moves remain
>* Players are shown the game result, score and moves remaining
>* Players can play multiple games

## Installation

### Prerequisites
* Java 21
* Access to command line interface / terminal

### Installation

1. Download `noughts-and-crosses.jar` from the `/out/artifacts/noughts_and_crosses_jar/` directory of this repository.
2. Move the downloaded file to another local directory on your device of your choosing.
3. Open your command line interface / terminal and navigate to the local location of `noughts-and-crosses.jar`.
4. Run the application with the following command:

> java -jar noughts-and-crosses.jar

## Usage
### Gameplay
Once you have run the application with the command above, you will be shown the following text:
> Welcome to Noughts and Crosses!
>
>This is a two player game in which players place a nought or cross in a 3x3 grid.
>The first player to have three of their markers filling a row, column or diagonal wins.
>
>Do you wish to start a new game? [Y / N] and hit ENTER.

If you are ready to play, type `Y` and press the `ENTER` key. If not, type `N` and press the `ENTER` key.

If you selected to continue, you will be prompted to enter the name of the first player:
> Please enter the name of Player One: 

Followed by the marker for Player One:
> Please enter the role of Player One. [O / X] and hit ENTER.

The same prompts will be issued for Player Two.

Next the players will be asked to confirm whether the details are correct and that they are ready to start:

![Player details](/screenshots/Screenshot-Player-Details.png "Player details")

At the beginning of the game an empty game grid will be displayed.

![Empty grid](/screenshots/Screenshot-Empty-Grid.png "Empty game grid")

The first player to move will be selected at random, and they will be prompted to move first:
>Jack moves first.<br />
>Jack, please enter column number [1 - 3] and hit ENTER.

After entering the column number, they will be prompted for the row number:
>Jack, please enter row number [1 - 3] and hit ENTER.

The game grid will be redrawn showing the first move.

![First move](/screenshots/Screenshot-First-Move.png "First move")

The second player will be prompted to enter their move, before the game returns to the first player for their second move.
The game will continue in this way until a player make a winning move or no moves remain.

### Example of a winning game

![Winning game](/screenshots/Screenshot-Winning-Game.png "Winning game")

### Example of a drawn game

![Drawn game](/screenshots/Screenshot-Drawn-Game.png "Drawn game")

### Subsequent games
Once the game has ended the players will be asked whether they wish to play another game, if not, the game will close. If they select to continue, they will be prompted to confirm that no changes to the players are required.

>Do you want to play another game? [Y] or any other key to quit and hit ENTER."

The game play will continue as before, but the scores for each player will be updated as a sum total of games won since the game was started.
