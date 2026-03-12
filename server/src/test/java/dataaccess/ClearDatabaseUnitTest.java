package dataaccess;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearDatabaseUnitTest {
    private SQLUserDAO userDao;
    private SQLAuthDAO authDao;
    private SQLGameDAO gameDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        userDao = new SQLUserDAO();
        authDao = new SQLAuthDAO();
        gameDao = new SQLGameDAO();

        userDao.clearAll();
        authDao.clearAll();
        gameDao.clearAll();

        userDao.createUser(new UserData("clearUser", "password123", "clear@example.com"));
        authDao.createAuth(new AuthData("clearUser", "clearToken"));
        gameDao.createGame("ClearGame", "clearUser");
    }

    @Test
    public void clearSuccess() throws DataAccessException {
        userDao.clearAll();
        authDao.clearAll();
        gameDao.clearAll();

        assertNull(userDao.getUser("clearUser"));
        assertNull(authDao.getAuth("clearToken"));
        assertTrue(gameDao.listGames().isEmpty());
    }
}