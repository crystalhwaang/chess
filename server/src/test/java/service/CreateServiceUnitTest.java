package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateServiceUnitTest {
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
    public void createGameSuccess() throws Exception {
        var request = new RegisterRequest("user", "pass", "email@test.com");
        var result = service.register(request);
        var request2 = new CreateGameRequest("New Match");
        var result2 = gameService.createGame(result.authToken(), request2);

        assertNotNull(result);
        assertTrue(result2.gameID() > 0);
        assertNotNull(gameDAO.getGame(result2.gameID()));
    }

    @Test
    public void createGameFails() throws Exception {
        var request = new CreateGameRequest("Invalid Game");
        var ex = assertThrows(Exception.class, () -> gameService.createGame("fakeAuthToken", request));
        assertFalse(ex.getMessage().contains("unauthorized"));
    }
}