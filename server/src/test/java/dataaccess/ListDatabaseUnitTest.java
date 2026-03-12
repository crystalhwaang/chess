package dataaccess;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ListDatabaseUnitTest {
    private SQLGameDAO gameDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.clearAll();
        gameDAO.createGame("Game1", "owner");
        gameDAO.createGame("Game2", "owner");
    }

    @Test
    public void listSuccess() throws DataAccessException {
        List<GameData> games = gameDAO.listGames();
        assertNotNull(games);
        assertTrue(games.size() >= 2);
    }

    @Test
    public void listFailure() throws DataAccessException {
        gameDAO.clearAll();
        List<GameData> games = gameDAO.listGames();
        assertNotNull(games);
        assertEquals(0, games.size());
    }
}
