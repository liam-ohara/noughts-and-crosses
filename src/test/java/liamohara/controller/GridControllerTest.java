package liamohara.controller;

import liamohara.exception.IllegalMoveException;
import liamohara.model.Player;
import liamohara.service.GameManagerServiceImpl;
import liamohara.service.GridManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GridControllerTest {

    @Mock
    GridManagerServiceImpl mockGridManagerServiceImpl;

    @InjectMocks
    GridController gridController;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    Player playerOne = new Player("Player One", true, false);
    Player playerTwo = new Player("Player Two", false, true);

    @Test
    @DisplayName("Call addNewGrid method in Service layer once")
    void testAddNewGrid() {

        int gameId = 1;

        gridController.addNewGrid(gameId);

        verify(mockGridManagerServiceImpl, times(1)).addNewGrid(gameId);

    }

    @Test
    @DisplayName("Returns null two-dimensional array of size 3x3 when no players have moved")
    void testGetGrid_WhenNoPlayersHaveMoved() {

        int gameId = 1;
        String[][] grid = new String[3][3];

        when(mockGridManagerServiceImpl.getGrid(gameId)).thenReturn(grid);

        String[][] result = gridController.getGrid(gameId);

        verify(mockGridManagerServiceImpl, times(1)).getGrid(gameId);

        assertNull(result[0][0]);

    }

    @Test
    @DisplayName("Returns two dimensional array of size 3x3 null except for row 0 column 0 which contains X when one player has moved")
    void testGetGrid_WhenOnePlayerHasMoved() {

        int gameId = 1;
        String[][] grid = new String[3][3];
        grid[0][0] = "X";

        when(mockGridManagerServiceImpl.getGrid(gameId)).thenReturn(grid);

        String[][] result = gridController.getGrid(gameId);

        verify(mockGridManagerServiceImpl, times(1)).getGrid(gameId);

        assertEquals("X", result[0][0]);

    }

    @Test
    @DisplayName("Calls addGrid method in Service layer once when player makes valid move")
    void testUpdateGrid_WhenPlayerMakesValidMove() {

        int gameId = 1;
        String[][] grid = new String[3][3];
        grid[0][0] = "O";
        int rowMove = 0;
        int columnMove = 1;

        gridController.updateGrid(gameId, rowMove, columnMove, playerTwo);

        verify(mockGridManagerServiceImpl, times(1)).updateGrid(gameId, rowMove, columnMove, playerTwo);

    }

    @Test
    @DisplayName("Throw IllegalMoveException when player makes an illegal move")
    void testUpdateGrid_WhenPlayerMakesIllegalMove() {

        int gameId = 1;
        String[][] grid = new String[3][3];
        grid[0][0] = "O";
        int rowMove = 0;
        int columnMove = 0;

        doThrow(IllegalMoveException.class).when(mockGridManagerServiceImpl).updateGrid(gameId, rowMove, columnMove, playerOne);

        assertThrowsExactly(IllegalMoveException.class, () -> gridController.updateGrid(gameId, rowMove, columnMove, playerOne));

    }


}