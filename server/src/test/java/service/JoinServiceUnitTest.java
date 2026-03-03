package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JoinServiceUnitTest {
    private UserService service;
    private MemoryGameDAO gameDAO;
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;
    private GameService gameService;

    @BeforeEach
    public void setup() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        service = new UserService(userDAO, authDAO);
        gameService = new GameService(authDAO, gameDAO);
    }

    @Test
    public void joinGameSuccess() throws Exception {
        var user1 = service.register(new RegisterRequest("player1", "pass", "p1@gmail.com"));
        var user2 = service.register(new RegisterRequest("player2", "pass", "p2@gmail.com"));
        var request = new CreateGameRequest("New Game");
        var result = gameService.createGame(user1.authToken(), request);

        var join = new JoinGameRequest("WHITE", result.gameID());
        gameService.joinGame(user2.authToken(), join);
        var game = gameDAO.getGame(result.gameID());
        assertEquals("player2", game.whiteUsername());
    }

    @Test
    public void joinGameFails() throws Exception {
        var request = new JoinGameRequest("WHITE", 12);
        var ex = assertThrows(Exception.class, () -> gameService.joinGame("fakeAuthToken", request));
        assertFalse(ex.getMessage().contains("unauthorized"));
    }
}