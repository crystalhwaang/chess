package dataaccess;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateAuthDatabaseUnitTest {
    private SQLAuthDAO authDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        authDao = new SQLAuthDAO();
        authDao.clearAll();
    }

    @Test
    public void createAuthSuccess() throws DataAccessException {
        authDao.createAuth(new AuthData("alice", "tokenA"));
        AuthData retrieved = authDao.getAuth("tokenA");
        assertNotNull(retrieved);
        assertEquals("alice", retrieved.username());
        assertEquals("tokenA", retrieved.authToken());
    }

    @Test
    public void createAuthFailure() throws DataAccessException {
        authDao.createAuth(new AuthData("alice", "tokenDup"));
        DataAccessException ex = assertThrows(DataAccessException.class, () ->
                authDao.createAuth(new AuthData("bob", "tokenDup")));
        assertTrue(ex.getMessage().contains("Auth token already exists"));
    }
}