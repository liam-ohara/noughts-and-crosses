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
    void addNewGrid() {

        int gameId = 1;

        gridController.addNewGrid(gameId);

        verify(mockGridManagerServiceImpl, times(1)).addNewGrid(gameId);

    }
}