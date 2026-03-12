package dataaccess;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutDatabaseUnitTest {
    private SQLAuthDAO authDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        authDao = new SQLAuthDAO();
        authDao.clearAll();
        authDao.createAuth(new AuthData("loginUser", "token123"));
    }

    @Test
    public void logoutSuccess() throws DataAccessException {
        authDao.logoutUser("token123"); // should succeed
        DataAccessException ex = assertThrows(DataAccessException.class, () -> {
            authDao.logoutUser("token123"); // token gone, should throw
        });
        assertTrue(ex.getMessage().contains("Auth token not found"));
    }

    @Test
    public void logoutFailure() {
        DataAccessException ex = assertThrows(DataAccessException.class, () -> {
            authDao.logoutUser("nonexistentToken");
        });
        assertTrue(ex.getMessage().contains("Auth token not found"));
    }
}
