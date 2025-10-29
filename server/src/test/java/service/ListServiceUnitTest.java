package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;

public class ListServiceUnitTest {
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
    public void listGameSuccess() throws Exception {
        var user = service.register(new RegisterRequest("player", "pass", "p1@gmail.com"));
        var result = gameService.createGame(user.authToken(), new CreateGameRequest("New Game"));
        var games = gameService.listGames(user.authToken());
        var game = games.get(0);

        assertNotNull(games);
        assertFalse(games.isEmpty());
        assertEquals(result.gameID(), games.get(0).gameID());
        assertEquals("New Game", games.get(0).gameName());
        assertNull(game.whiteUsername());
    }

    @Test
    public void listGameFails() throws Exception {
        var ex = assertThrows(Exception.class, () -> gameService.listGames("fakeAuthToken"));
        assertFalse(ex.getMessage().contains("unauthorized"));
    }
}
