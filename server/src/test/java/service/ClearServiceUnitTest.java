package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceUnitTest {

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
    public void clearSuccess() throws Exception {
        var user = service.register(new RegisterRequest("player", "pass", "p1@gmail.com"));
        var result = gameService.createGame(user.authToken(), new CreateGameRequest("New Game"));

        assertNotNull(userDAO.getUser("player"));
        assertNotNull(gameDAO.getGame(result.gameID()));
        assertNotNull(authDAO.getAuth(user.authToken()));

        userDAO.clearAll();
        gameDAO.clearAll();
        authDAO.clearAll();

        assertNull(userDAO.getUser("player"));
        assertNull(gameDAO.getGame(result.gameID()));
        assertNull(authDAO.getAuth(user.authToken()));
    }
}