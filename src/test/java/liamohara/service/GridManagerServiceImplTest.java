package liamohara.service;

import liamohara.exception.GameIdAlreadyAssignedException;
import liamohara.exception.GridNotFoundException;
import liamohara.exception.IllegalMoveException;
import liamohara.exception.NoGridsException;
import liamohara.model.Game;
import liamohara.model.Grid;
import liamohara.model.Player;
import liamohara.repository.GridsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GridManagerServiceImplTest {

    @Mock
    GridsRepository mockGridsRepository;

    @InjectMocks
    GridManagerServiceImpl gridManagerServiceImpl;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

    }

    Game gameOne = new Game(1);
    Grid gridOne = new Grid(gameOne.getId());
    List<Grid> listOfGrids = new ArrayList<>();
    Player playerOne = new Player("Player One", false, true);

    @Test
    @DisplayName("Calls getListOfGrids and addNewGrid methods in GridsRepository once each when repository is empty.")
    void testAddNewGrid_WhenGridsRepositoryIsEmpty() {

        String[][] gridData = new String[3][3];
        gridOne.setGrid(gridData);

        when(mockGridsRepository.getListOfGrids()).thenReturn(listOfGrids);

        gridManagerServiceImpl.addNewGrid(gameOne.getId());

        verify(mockGridsRepository, times(1)).getListOfGrids();
        verify(mockGridsRepository, times(1)).addNewGrid(Mockito.any());

    }

    @Test
    @DisplayName("Throws GameIdAlreadyAssignedException when game ID has already been assigned to another grid")
    void testAddNewGrid_WhenGameIDHasAlreadyBeenAssigned() {

        String[][] gridData = new String[3][3];
        gridOne.setGrid(gridData);
        listOfGrids.add(gridOne);

        when(mockGridsRepository.getListOfGrids()).thenReturn(listOfGrids);

        assertThrowsExactly(GameIdAlreadyAssignedException.class, () -> gridManagerServiceImpl.addNewGrid(gameOne.getId()));

        verify(mockGridsRepository, times(1)).getListOfGrids();
        verify(mockGridsRepository, times(0)).addNewGrid(Mockito.any());

    }

    @Test
    @DisplayName("Returns null two-dimensional array of size 3x3 when no players have moved")
    void testGetGrid_WhenNoPlayersHaveMoved() {

        String[][] gridData = new String[3][3];
        gridOne.setGrid(gridData);
        listOfGrids.add(gridOne);
        int expectedGridRowSize = 3;

        when(mockGridsRepository.getListOfGrids()).thenReturn(listOfGrids);

        String[][] result = gridManagerServiceImpl.getGrid(gameOne.getId());

        verify(mockGridsRepository, times(1)).getListOfGrids();
        assertEquals(expectedGridRowSize, result.length);

    }

    @Test
    @DisplayName("Throw NoGridsException when method GridsRespository is empty")
    void testGetGrid_WhenGridsRespositoryIsEmpty() {

        when(mockGridsRepository.getListOfGrids()).thenReturn(listOfGrids);

        assertThrowsExactly(NoGridsException.class, () -> gridManagerServiceImpl.getGrid(1));
        verify(mockGridsRepository, times(1)).getListOfGrids();

    }

    @Test
    @DisplayName("Throw GridNotFoundException when grid with matching game ID not found.")
    void testGetGrid_WhenGridNotFound() {

        String[][] gridData = new String[3][3];
        gridOne.setGrid(gridData);
        listOfGrids.add(gridOne);

        when(mockGridsRepository.getListOfGrids()).thenReturn(listOfGrids);

        assertThrowsExactly(GridNotFoundException.class, () -> gridManagerServiceImpl.getGrid(2));
        verify(mockGridsRepository, times(1)).getListOfGrids();

    }

    @Test
    @DisplayName("Returns two dimensional array of size 3x3 null except for row 0 column 0 which contains X when one player has moved")
    void testGetGrid_WhenOnePlayerHasMoved() {

        String[][] gridData = new String[3][3];
        gridData[0][0] = "X";
        gridOne.setGrid(gridData);
        listOfGrids.add(gridOne);

        when(mockGridsRepository.getListOfGrids()).thenReturn(listOfGrids);

        String[][] result = gridManagerServiceImpl.getGrid(gameOne.getId());

        verify(mockGridsRepository, times(1)).getListOfGrids();
        assertEquals("X", result[0][0]);

    }

    @Test
    @DisplayName("Calls updateGrid method in GridsRepository once when player makes valid move")
    void testUpdateGrid_WhenPlayerMakesValidMove() {

        String[][] gridData = new String[3][3];
        gridOne.setGrid(gridData);
        listOfGrids.add(gridOne);

        when(mockGridsRepository.getListOfGrids()).thenReturn(listOfGrids);

        gridManagerServiceImpl.updateGrid(1, 0, 0, playerOne);

        verify(mockGridsRepository, times(1)).getListOfGrids();
        verify(mockGridsRepository, times(1)).updateGrid(Mockito.any());

        assertEquals("X", gridOne.getGrid()[0][0]);

    }

    @Test
    @DisplayName("Throw NoGridsException when method GridsRespository is empty")
    void testUpdateGrid_WhenGridsRespositoryIsEmpty() {

        when(mockGridsRepository.getListOfGrids()).thenReturn(listOfGrids);

        assertThrowsExactly(NoGridsException.class, () -> gridManagerServiceImpl.updateGrid(1, 0, 0, playerOne));
        verify(mockGridsRepository, times(1)).getListOfGrids();

    }

    @Test
    @DisplayName("Throw GridNotFoundException when grid with matching game ID not found.")
    void testUpdateGrid_WhenGridNotFound() {

        String[][] gridData = new String[3][3];
        gridOne.setGrid(gridData);
        listOfGrids.add(gridOne);

        when(mockGridsRepository.getListOfGrids()).thenReturn(listOfGrids);

        assertThrowsExactly(GridNotFoundException.class, () -> gridManagerServiceImpl.updateGrid(2, 0, 0, playerOne));
        verify(mockGridsRepository, times(1)).getListOfGrids();

    }

    @Test
    @DisplayName("Throw IllegalMoveException when player makes an illegal move")
    void testUpdateGrid_WhenPlayerMakesIllegalMove() {

        String[][] gridData = new String[3][3];
        gridData[0][0] = "X";
        gridOne.setGrid(gridData);
        listOfGrids.add(gridOne);

        when(mockGridsRepository.getListOfGrids()).thenReturn(listOfGrids);

        assertThrowsExactly(IllegalMoveException.class, () -> gridManagerServiceImpl.updateGrid(1, 0, 0, playerOne));
        verify(mockGridsRepository, times(1)).getListOfGrids();
        verify(mockGridsRepository, times(0)).updateGrid(Mockito.any());

    }



}