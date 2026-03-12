package dataaccess;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GetGameDatabaseUnitTest {
    private SQLGameDAO gameDAO;
    private int gameId;

    @BeforeEach
    public void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.clearAll();
        gameId = gameDAO.createGame("GetGame", "owner");
    }

    @Test
    public void getGameSuccess() throws DataAccessException {
        GameData game = gameDAO.getGame(gameId);
        assertNotNull(game);
        assertEquals(gameId, game.gameID());
        assertEquals("GetGame", game.gameName());
    }

    @Test
    public void getGameFailure() throws DataAccessException {
        GameData game = gameDAO.getGame(999999);
        assertNull(game);
    }
}