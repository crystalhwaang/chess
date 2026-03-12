package dataaccess;
import exception.AlreadyTakenException;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JoinDatabaseUnitTest {
    private SQLGameDAO gameDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.clearAll();
    }

    @Test
    public void joinSuccess() throws DataAccessException, AlreadyTakenException {
        int gameId = gameDAO.createGame("TestGame2", "owner");
        gameDAO.joinGame(gameId, "BLACK", "alice");
        GameData game = gameDAO.getGame(gameId);
        assertEquals("alice", game.blackUsername(), "BLACK player should be set to alice");
        assertNull(game.whiteUsername(), "WHITE player should still be null");
    }

    @Test
    public void joinFailure() throws DataAccessException {
        int gameId = gameDAO.createGame("TestGame4", "owner");
        DataAccessException ex = assertThrows(DataAccessException.class, () -> {
            gameDAO.joinGame(gameId, "GREEN", "dave");
        });
        assertTrue(ex.getMessage().contains("Invalid player color"));
    }

    @Test
    public void joinAlreadyTakenFailure() throws DataAccessException, AlreadyTakenException {
        int gameId = gameDAO.createGame("TestGame5", "owner");
        gameDAO.joinGame(gameId, "WHITE", "alice");

        AlreadyTakenException ex = assertThrows(AlreadyTakenException.class, () -> {
            gameDAO.joinGame(gameId, "WHITE", "bob");
        });
        assertTrue(ex.getMessage().contains("already taken"));
    }
}
