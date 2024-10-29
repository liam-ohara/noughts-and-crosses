package liamohara.controller;

import liamohara.service.PlayerManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerControllerTest {

    @Mock
    PlayerManagerServiceImpl mockPlayerManagerServiceImpl;

    @InjectMocks
    PlayerController playerController;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Calls updatePlayerMovesRemaining method in Service layer once")
    void updatePlayerMovesRemaining() {

        String playerName = "Player One";

        playerController.updatePlayerMovesRemaining(playerName);

        verify(mockPlayerManagerServiceImpl, times(1)).updatePlayerMovesRemaining(playerName);

    }
}