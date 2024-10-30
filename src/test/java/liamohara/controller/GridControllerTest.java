package liamohara.controller;

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
}