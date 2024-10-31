package liamohara.service;

import liamohara.exception.GameIdAlreadyAssignedException;
import liamohara.exception.GridNotFoundException;
import liamohara.exception.NoGridsException;
import liamohara.model.Game;
import liamohara.model.Grid;
import liamohara.repository.GridsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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




}