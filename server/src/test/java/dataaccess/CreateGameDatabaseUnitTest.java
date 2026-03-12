package dataaccess;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateGameDatabaseUnitTest {
    private SQLGameDAO gameDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.clearAll();
    }

    @Test
    public void createGameSuccess() throws DataAccessException {
        int gameId = gameDAO.createGame("TestGame", "owner");
        assertTrue(gameId > 0);

        GameData game = gameDAO.getGame(gameId);
        assertNotNull(game);
        assertEquals("TestGame", game.gameName());
        assertNull(game.whiteUsername());
        assertNull(game.blackUsername());
        assertNotNull(game.game());
    }

    @Test
    public void createGameFailure() {
        DataAccessException ex = assertThrows(DataAccessException.class, () -> gameDAO.createGame("   ", "owner"));
        assertTrue(ex.getMessage().contains("Game name required"));
    }
}