package liamohara.service;

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
    Grid duplicateGrid = new Grid(gameOne.getId());
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

}