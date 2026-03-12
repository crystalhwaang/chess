package dataaccess;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GetAuthDatabaseUnitTest {
    private SQLAuthDAO authDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        authDao = new SQLAuthDAO();
        authDao.clearAll();
        authDao.createAuth(new AuthData("getAuthUser", "getAuthToken"));
    }

    @Test
    public void getAuthSuccess() throws DataAccessException {
        AuthData retrieved = authDao.getAuth("getAuthToken");
        assertNotNull(retrieved);
        assertEquals("getAuthUser", retrieved.username());
        assertEquals("getAuthToken", retrieved.authToken());
    }

    @Test
    public void getAuthFailure() throws DataAccessException {
        AuthData retrieved = authDao.getAuth("doesNotExist");
        assertNull(retrieved);
    }
}